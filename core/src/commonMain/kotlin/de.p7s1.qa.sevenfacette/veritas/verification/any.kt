package de.p7s1.qa.sevenfacette.veritas.verification

import de.p7s1.qa.sevenfacette.veritas.Verify
import de.p7s1.qa.sevenfacette.veritas.all
import de.p7s1.qa.sevenfacette.veritas.verification.utils.expected
import de.p7s1.qa.sevenfacette.veritas.verification.utils.fail
import de.p7s1.qa.sevenfacette.veritas.verification.utils.show
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1

/**
 * Returns a verification on the kotlin class of the value.
 */
fun Verify<Any>.kClass() = prop("class") { it::class }

/**
 * Returns a verification on the toString method of the value.
 */
fun Verify<Any?>.toStringFun() = prop("toString", Any?::toString)

/**
 * Returns a verification on the hasCode method of the value.
 */
fun Verify<Any>.hashCodeFun() = prop("hashCode", Any::hashCode)

/**
 * Verifies the value is equal to the expected one, using `==`.
 * @see [isNotEqualTo]
 * @see [isSameAs]
 */
fun <T> Verify<T>.isEqualTo(expected: T) = given { actual ->
    if (actual == expected) return
    fail(expected, actual)
}

/**
 * Verifies the value is not equal to the expected one, using `!=`.
 * @see [isEqualTo]
 * @see [isNotSameAs]
 */
fun Verify<Any?>.isNotEqualTo(expected: Any?) = given { actual ->
    if (actual != expected) return
    val showExpected = show(expected)
    val showActual = show(actual)
    // if they display the same, only show one.
    if (showExpected == showActual) {
        expected("to not be equal to:$showActual")
    } else {
        expected(":$showExpected not to be equal to:$showActual")
    }
}

/**
 * Verifies the value is the same as the expected one, using `===`.
 * @see [isNotSameAs]
 * @see [isEqualTo]
 */
fun <T> Verify<T>.isSameAs(expected: T) = given { actual ->
    if (actual === expected) return
    expected(":${show(expected)} and:${show(actual)} to refer to the same object")
}

/**
 * Verifies the value is not the same as the expected one, using `!==`.
 * @see [isSameAs]
 * @see [isNotEqualTo]
 */
fun Verify<Any?>.isNotSameAs(expected: Any?) = given { actual ->
    if (actual !== expected) return
    expected(":${show(expected)} to not refer to the same object")
}

/**
 * Verifies the value is in the expected values, using `in`.
 * @see [isNotIn]
 */
fun <T> Verify<T>.isIn(vararg values: T) = given { actual ->
    if (actual in values) return
    expected(":${show(values)} to contain:${show(actual)}")
}

/**
 * Verifies the value is not in the expected values, using `!in`.
 * @see [isIn]
 */
fun <T> Verify<T>.isNotIn(vararg values: T) = given { actual ->
    if (actual !in values) return
    expected(":${show(values)} to not contain:${show(actual)}")
}

/**
 * Verifies the value has the expected string from it's [toString].
 */
fun Verify<Any?>.hasToString(string: String) {
    toStringFun().isEqualTo(string)
}

/**
 * Verifies the value has the expected hash code from it's [hashCode].
 */
fun Verify<Any>.hasHashCode(hashCode: Int) {
    hashCodeFun().isEqualTo(hashCode)
}

/**
 * Verifies the value is null.
 */
fun Verify<Any?>.isNull() = given { actual ->
    if (actual == null) return
    expected("to be null but was:${show(actual)}")
}

/**
 * Verifies the value is not null. You can pass in an optional lambda to run additional assertions on the non-null value.
 *
 * ```
 * val name: String? = ...
 * assertThat(name).isNotNull().hasLength(4)
 * ```
 */
fun <T : Any> Verify<T?>.isNotNull(): Verify<T> = transform { actual ->
    actual ?: expected("to not be null")
}

/**
 * Returns a verification that verifies on the given property of the value.
 * @param name The name of the property to show in failure messages.
 * @param extract The function to extract the property value out of the value of the current verification.
 *
 * ```
 * assertThat(person).prop("name", { it.name }).isEqualTo("Sue")
 * ```
 */
fun <T, P> Verify<T>.prop(name: String, extract: (T) -> P): Verify<P> =
        transform("${if (this.name != null) this.name + "." else ""}$name", extract)

/**
 * Verifies the value has the expected kotlin class. This is an exact match, so `verifyThat("test").hasClass(String::class)`
 * is successful but `verifyThat("test").hasClass(Any::class)` fails.
 * @see [doesNotHaveClass]
 * @see [isInstanceOf]
 */
fun <T : Any> Verify<T>.hasClass(kclass: KClass<out T>) = given { actual ->
    if (kclass == actual::class) return
    expected("to have class:${show(kclass)} but was:${show(actual::class)}")
}

/**
 * Verifies the value does not have the expected kotlin class. This is an exact match, so
 * `assertThat("test").doesNotHaveClass(String::class)` is fails but `assertThat("test").doesNotHaveClass(Any::class)` is
 * successful.
 * @see [hasClass]
 * @see [isNotInstanceOf]
 */
fun <T : Any> Verify<T>.doesNotHaveClass(kclass: KClass<out T>) = given { actual ->
    if (kclass != actual::class) return
    expected("to not have class:${show(kclass)}")
}

/**
 * Asserts the value is not an instance of the expected kotlin class. Both
 * `assertThat("test").isNotInstanceOf(String::class)` and `assertThat("test").isNotInstanceOf(Any::class)` fails.
 * @see [isInstanceOf]
 * @see [doesNotHaveClass]
 */
fun <T : Any> Verify<T>.isNotInstanceOf(kclass: KClass<out T>) = given { actual ->
    if (!kclass.isInstance(actual)) return
    expected("to not be instance of:${show(kclass)}")
}

/**
 * Asserts the value is an instance of the expected kotlin class. Both `assertThat("test").isInstanceOf(String::class)` and
 * `assertThat("test").isInstanceOf(Any::class)` is successful.
 * @see [isNotInstanceOf]
 * @see [hasClass]
 */
fun <T : Any, S : T> Verify<T>.isInstanceOf(kclass: KClass<S>): Verify<S> = transform(name) { actual ->
    if (kclass.isInstance(actual)) {
        @Suppress("UNCHECKED_CAST")
        actual as S
    } else {
        expected("to be instance of:${show(kclass)} but had class:${show(actual::class)}")
    }
}

/**
 * Asserts the value corresponds to the expected one using the given correspondence function to compare them. This is
 * useful when the objects don't have an [equals] implementation.
 *
 * @see [isEqualTo]
 * @see [doesNotCorrespond]
 */
fun <T, E> Verify<T>.corresponds(expected: E, correspondence: (T, E) -> Boolean) = given { actual ->
    if (correspondence(actual, expected)) return
    fail(expected, actual)
}

/**
 * Asserts the value does not correspond to the expected one using the given correspondence function to compare them.
 * This is useful when the objects don't have an [equals] implementation.
 *
 * @see [corresponds]
 * @see [isNotEqualTo]
 */
fun <T, E> Verify<T>.doesNotCorrespond(expected: E, correspondence: (T, E) -> Boolean) = given { actual ->
    if (!correspondence(actual, expected)) return
    val showExpected = show(expected)
    val showActual = show(actual)
    // if they display the same, only show one.
    if (showExpected == showActual) {
        expected("to not be equal to:$showActual")
    } else {
        expected(":$showExpected not to be equal to:$showActual")
    }
}

/**
 * Returns an assert that compares only the given properties on the calling class
 * @param other Other value to compare to
 * @param properties properties of the type with which to compare
 *
 * ```
 * assertThat(person).isEqualToWithGivenProperties(other, Person::name, Person::age)
 * ```
 */
fun <T> Verify<T>.isEqualToWithGivenProperties(other: T, vararg properties: KProperty1<T, Any?>) {
    all {
        for (prop in properties) {
            transform("${if (this.name != null) this.name + "." else ""}${prop.name}", prop::get)
                    .isEqualTo(prop.get(other))
        }
    }
}
