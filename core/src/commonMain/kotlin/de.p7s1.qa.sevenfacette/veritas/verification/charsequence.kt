package de.p7s1.qa.sevenfacette.veritas.verification

import de.p7s1.qa.sevenfacette.veritas.Verify
import de.p7s1.qa.sevenfacette.veritas.verification.utils.expected
import de.p7s1.qa.sevenfacette.veritas.verification.utils.show

/**
 * Returns an assert on the CharSequence's length.
 */
fun Verify<CharSequence>.length() = prop("length", CharSequence::length)

/**
 * Asserts the char sequence is empty.
 * @see [isNotEmpty]
 * @see [isNullOrEmpty]
 */
fun Verify<CharSequence>.isEmpty() = given { actual ->
    if (actual.isEmpty()) return
    expected("to be empty but was:${show(actual)}")
}

/**
 * Asserts the char sequence is not empty.
 * @see [isEmpty]
 */
fun Verify<CharSequence>.isNotEmpty() = given { actual ->
    if (actual.isNotEmpty()) return
    expected("to not be empty")
}

/**
 * Asserts the char sequence is null or empty.
 * @see [isEmpty]
 */
fun Verify<CharSequence?>.isNullOrEmpty() = given { actual ->
    if (actual.isNullOrEmpty()) return
    expected("to be null or empty but was:${show(actual)}")
}

/**
 * Asserts the char sequence has the expected length.
 */
fun Verify<CharSequence>.hasLength(length: Int) {
    length().isEqualTo(length)
}

/**
 * Asserts the char sequence has the same length as the expected one.
 */
fun Verify<CharSequence>.hasSameLengthAs(other: CharSequence) = given { actual ->
    val actualLength = actual.length
    val otherLength = other.length
    if (actualLength == otherLength) return
    expected("to have same length as:${show(other)} ($otherLength) but was:${show(actual)} ($actualLength)")
}
