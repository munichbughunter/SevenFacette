package de.p7s1.qa.sevenfacette.veritas.verification

import de.p7s1.qa.sevenfacette.veritas.Verify
import de.p7s1.qa.sevenfacette.veritas.all

/**
 * Returns an assert on the throwable's message.
 */
fun Verify<Throwable>.message() = prop("message", Throwable::message)

/**
 * Returns an assert on the throwable's cause.
 */
fun Verify<Throwable>.cause() = prop("cause", Throwable::cause)

/**
 * Returns an assert on the throwable's root cause.
 */
fun Verify<Throwable>.rootCause() = prop("rootCause", Throwable::rootCause)

/**
 * Asserts the throwable has the expected message.
 */
fun Verify<Throwable>.hasMessage(message: String?) {
    message().isEqualTo(message)
}

/**
 * Asserts the throwable contains the expected text
 */
fun Verify<Throwable>.messageContains(text: String) {
    message().isNotNull().contains(text)
}

/**
 * Asserts the throwable is similar to the expected cause, checking the type and message.
 * @see [hasNoCause]
 */
fun Verify<Throwable>.hasCause(cause: Throwable) {
    cause().isNotNull().all {
        kClass().isEqualTo(cause::class)
        hasMessage(cause.message)
    }
}

/**
 * Asserts the throwable has no cause.
 * @see [hasCause]
 */
fun Verify<Throwable>.hasNoCause() {
    cause().isNull()
}

/**
 * Asserts the throwable is similar to the expected root cause, checking the type and message.
 */
fun Verify<Throwable>.hasRootCause(cause: Throwable) {
    rootCause().all {
        kClass().isEqualTo(cause::class)
        hasMessage(cause.message)
    }
}

private fun Throwable.rootCause(): Throwable = this.cause?.rootCause() ?: this
