package de.p7s1.qa.sevenfacette.veritas.verification

import de.p7s1.qa.sevenfacette.veritas.Verify
import de.p7s1.qa.sevenfacette.veritas.verification.utils.expected

/**
 * Asserts the boolean is true.
 * @see [isFalse]
 */
fun Verify<Boolean>.isTrue() = given { actual ->
    if (actual) return
    expected("to be true")
}

/**
 * Asserts the boolean is false.
 * @see [isTrue]
 */
fun Verify<Boolean>.isFalse() = given { actual ->
    if (!actual) return
    expected("to be false")
}
