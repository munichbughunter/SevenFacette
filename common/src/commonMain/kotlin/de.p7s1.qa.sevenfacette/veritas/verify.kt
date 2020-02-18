package de.p7s1.qa.sevenfacette.veritas


import de.p7s1.qa.sevenfacette.veritas.verification.isFailure
import de.p7s1.qa.sevenfacette.veritas.verification.isSuccess
import kotlin.contracts.contract
import kotlin.reflect.KProperty0
/**
 * Marks the assertion DSL.
 */
@DslMarker
annotation class VeritasDsl

/**
 * An assertion. Holds an actual value to assertion on and an optional name.
 * @see [verifyThat]
 */
@VeritasDsl
sealed class Verify<out T>(val name: String?, internal val context: Any?) {
    /**
     * Transforms an assertion from one type to another. If the assertion is failing the resulting assertion will still
     * be failing, otherwise the mapping function is called. An optional name can be provided, otherwise this
     * assertion's name will be used.
     */
    @Suppress("TooGenericExceptionCaught")
    fun <R> transform(name: String? = this.name, transform: (T) -> R): Verify<R> {
        return when (this) {
            is ValueAssert -> {
                try {
                    verifyThat(transform(value), name)
                } catch (e: Throwable) {
                    notifyFailure(e)
                    FailingAssert<R>(e, name, context)
                }
            }
            is FailingAssert -> FailingAssert(error, name, context)
        }
    }

    /**
     * Allows checking the actual value of an assert. This can be used to build your own custom assertions.
     * ```
     * fun Assert<Int>.isTen() = given { actual ->
     *     if (actual == 10) return
     *     expected("to be 10 but was:${show(actual)}")
     * }
     * ```
     */
    @Suppress("TooGenericExceptionCaught")
    inline fun given(assertion: (T) -> Unit) {
        if (this is ValueAssert) {
            try {
                assertion(value)
            } catch (e: Throwable) {
                notifyFailure(e)
            }
        }
    }

    /**
     * Asserts on the given value with an optional name.
     *
     * ```
     * assertThat(true, name = "true").isTrue()
     * ```
     */
    abstract fun <R> verifyThat(actual: R, name: String? = this.name): Verify<R>
}

@PublishedApi
internal class ValueAssert<out T>(val value: T, name: String?, context: Any?) :
        Verify<T>(name, context) {

    override fun <R> verifyThat(actual: R, name: String?): Verify<R> =
            ValueAssert(actual, name, if (context != null || this.value === actual) context else this.value)
}

internal class FailingAssert<out T>(val error: Throwable, name: String?, context: Any?) :
        Verify<T>(name, context) {
    override fun <R> verifyThat(actual: R, name: String?): Verify<R> = FailingAssert(error, name, context)
}

/**
 * Runs the given lambda if the block throws an error, otherwise fails.
 */
@Suppress("DEPRECATION")
@Deprecated(
        message = "Use isFailure().all(f) instead",
        replaceWith = ReplaceWith("isFailure().all(f)", imports = ["assertk.assertions.isFailure", "assertk.all"]),
        level = DeprecationLevel.ERROR
)
fun <T> Verify<Result<T>>.thrownError(f: Verify<Throwable>.() -> Unit) {
    isFailure().all(f)
}

/**
 * Runs the given lambda if the block returns a value, otherwise fails.
 */
@Suppress("DEPRECATION")
@Deprecated(
        message = "Use isSuccess().all(f) instead",
        replaceWith = ReplaceWith("isSuccess().all(f)", imports = ["assertk.assertions.isSuccess", "assertk.all"]),
        level = DeprecationLevel.ERROR
)
fun <T> Verify<Result<T>>.returnedValue(f: Verify<T>.() -> Unit) {
    isSuccess().all(f)
}

@Suppress("DEPRECATION")
@Deprecated(
        message = "Use isSuccess() instead",
        replaceWith = ReplaceWith("isSuccess()", imports = ["assertk.assertions.isSuccess", "assertk.assertions"]),
        level = DeprecationLevel.ERROR
)
fun <T> Verify<Result<T>>.doesNotThrowAnyException() {
    isSuccess()
}

@Suppress("DEPRECATION")
@Deprecated(message = "Use Assert<Result<T>> instead", level = DeprecationLevel.ERROR)
typealias AssertBlock<T> = Verify<Result<T>>

@Suppress("DEPRECATION")
@Deprecated(message = "Temporary replacement for kotlin.Result until https://youtrack.jetbrains.com/issue/KT-32450 is fixed.")
sealed class Result<out T> {
    companion object {
        fun <T> success(value: T): Result<T> = Success(value)
        fun <T> failure(error: Throwable): Result<T> = Failure(error)

        @Suppress("TooGenericExceptionCaught")
        inline fun <R> runCatching(block: () -> R): Result<R> {
            return try {
                success(block())
            } catch (e: Throwable) {
                failure(e)
            }
        }
    }

    val isSuccess: Boolean get() = when (this) {
        is Success -> true
        is Failure -> false
    }

    fun getOrNull(): T? = when (this) {
        is Success -> value
        is Failure -> null
    }

    fun exceptionOrNull(): Throwable? = when (this) {
        is Success -> null
        is Failure -> error
    }

    private data class Success<T>(val value: T) : Result<T>() {
        override fun toString(): String = "Success($value)"
    }

    private data class Failure<T>(val error: Throwable) : Result<T>() {
        override fun toString(): String = "Failure($error)"
    }
}

/**
 * Calls platform specific function so that it is possible to show stacktrace if able
 *
 * TODO: use @OptionalExpectation (https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-optional-expectation/index.html) here once available and call default implementation of [show] for JS
 */
internal expect fun showError(e: Throwable): String

/**
 * Asserts on the given value with an optional name.
 *
 * ```
 * verifyThat(true, name = "true").isTrue()
 * ```
 */
fun <T> verifyThat(actual: T, name: String? = null): Verify<T> = ValueAssert(actual, name, null)

/**
 * Asserts on the given property reference using its name, if no explicit name is specified. This method
 * should be preferred in cases, where property references can be used, as it uses the property's name
 * for the assertion automatically. The name may optionally be overridden, if needed.
 *
 * ```
 * data class Person(val name: String)
 *
 * val p = Person("Hugo")
 *
 * verifyThat(p::name).contains("u")
 * ```
 */
fun <T> verifyThat(getter: KProperty0<T>, name: String? = null): Verify<T> =
        verifyThat(getter.get(), name ?: getter.name)

/**
 * All assertions in the given lambda are run.
 *
 * ```
 * verifyThat("test", name = "test").all {
 *   startsWith("t")
 *   endsWith("t")
 * }
 * ```
 * @param message An optional message to show before all failures.
 * @param body The body to execute.
 */
fun <T> Verify<T>.all(message: String, body: Verify<T>.() -> Unit) {
    all(message, body, { it.isNotEmpty() })
}

/**
 * All assertions in the given lambda are run.
 *
 * ```
 * verifyThat("test", name = "test").all {
 *   startsWith("t")
 *   endsWith("t")
 * }
 * ```
 * @param body The body to execute.
 */
fun <T> Verify<T>.all(body: Verify<T>.() -> Unit) {
    all(SoftFailure.defaultMessage, body, { it.isNotEmpty() })
}

/**
 * All assertions in the given lambda are run, with their failures collected. If `failIf` returns true then a failure
 * happens, otherwise they are ignored.
 *
 * ```
 * assert("test", name = "test").all(
 *   message = "my message",
 *   body = {
 *     startsWith("t")
 *     endsWith("t")
 *   }, {
 *     it.size > 1
 *   }
 * )
 * ```
 *
 * @param message An optional message to show before all failures.
 * @param body The body to execute.
 * @param failIf Fails if this returns true, ignores failures otherwise.
 */
// Hide for now, not sure if happy with api.
internal fun <T> Verify<T>.all(
        message: String,
        body: Verify<T>.() -> Unit,
        failIf: (List<Throwable>) -> Boolean
) {
    SoftFailure(message, failIf).run {
        body()
    }
}

/**
 * Asserts on the given block returning an `Assert<Result<T>>`. You can test that it returns a value or throws an exception.
 *
 * ```
 * verifyThat { 1 + 1 }.isSuccess().isPositive()
 *
 * verifyThat {
 *   throw Exception("error")
 * }.isFailure().hasMessage("error")
 * ```
 */
@Suppress("DEPRECATION")
inline fun <T> verifyThat(f: () -> T): Verify<Result<T>> = verifyThat(Result.runCatching(f))

/**
 * Runs all assertions in the given lambda and reports any failures.
 */
inline fun assertAll(f: () -> Unit) {
    Failure.soft().run(f)
}

/**
 * Catches any exceptions thrown in the given lambda and returns it. This is an easy way to assert on expected thrown
 * exceptions.
 *
 * ```
 * val exception = catch { throw Exception("error") }
 * verifyThat(exception).isNotNull().hasMessage("error")
 * ```
 */
@Suppress("TooGenericExceptionCaught")
@Deprecated("Use verifyThat { }.isFailure() instead", level = DeprecationLevel.ERROR)
inline fun catch(f: () -> Unit): Throwable? {
    try {
        f()
        return null
    } catch (e: Throwable) {
        return e
    }
}
