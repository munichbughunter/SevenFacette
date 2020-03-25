@file:Suppress("DEPRECATION")

package de.p7s1.qa.sevenfacette.veritas.verification

import de.p7s1.qa.sevenfacette.veritas.Verify
import de.p7s1.qa.sevenfacette.veritas.showError
import de.p7s1.qa.sevenfacette.veritas.verification.utils.expected
import de.p7s1.qa.sevenfacette.veritas.verification.utils.show

/**
 * Asserts the given [veritas.Result] successful returned a value,
 * returning it's result if it did or failing if it didn't.
 *
 * ```
 * assertThat { 1 + 1 }.isSuccess().isEqualTo(2)
 * ```
 */
fun <T> Verify<de.p7s1.qa.sevenfacette.veritas.Result<T>>.isSuccess(): Verify<T> = transform { actual ->
    if (actual.isSuccess) {
        @Suppress("UNCHECKED_CAST")
        actual.getOrNull() as T
    } else {
        expected("success but was failure:${showError(actual.exceptionOrNull()!!)}")
    }
}

/**
 * Asserts the given [veritas.Result] threw an exception, returning that exception
 * if it was or failing it if didn't.
 *
 * ```
 * assertThat { throw Exception("error") }.isFailure().hasMessage("error")
 * ```
 */
fun <T> Verify<de.p7s1.qa.sevenfacette.veritas.Result<T>>.isFailure(): Verify<Throwable> = transform { actual ->
    actual.exceptionOrNull() ?: expected("failure but was success:${show(actual.getOrNull())}")
}
