package de.p7s1.qa.sevenfacette.veritas.verification

import de.p7s1.qa.sevenfacette.veritas.Verify
import de.p7s1.qa.sevenfacette.veritas.verification.utils.expected
import de.p7s1.qa.sevenfacette.veritas.verification.utils.fail
import de.p7s1.qa.sevenfacette.veritas.verification.utils.show

/**
 * Asserts the value is greater than the expected value, using `>`.
 * @see [isGreaterThanOrEqualTo]
 * @see [isLessThan]
 */
fun <A, B : Comparable<A>> Verify<B>.isGreaterThan(other: A) = given { actual ->
    if (actual > other) return
    expected("to be greater than:${show(other)} but was:${show(actual)}")
}

/**
 * Asserts the value is less than the expected value, using `<`.
 * @see [isLessThanOrEqualTo]
 * @see [isGreaterThan]
 */
fun <A, B : Comparable<A>> Verify<B>.isLessThan(other: A) = given { actual ->
    if (actual < other) return
    expected("to be less than:${show(other)} but was:${show(actual)}")
}

/**
 * Asserts the value is greater or equal to the expected value, using `>=`.
 * @see [isGreaterThan]
 * @see [isLessThanOrEqualTo]
 */
fun <A, B : Comparable<A>> Verify<B>.isGreaterThanOrEqualTo(other: A) = given { actual ->
    if (actual >= other) return
    expected("to be greater than or equal to:${show(other)} but was:${show(actual)}")
}

/**
 * Asserts the value is less than or equal to the expected value, using `<=`.
 * @see [isLessThan]
 * @see [isGreaterThanOrEqualTo]
 */
fun <A, B : Comparable<A>> Verify<B>.isLessThanOrEqualTo(other: A) = given { actual ->
    if (actual <= other) return
    expected("to be less than or equal to:${show(other)} but was:${show(actual)}")
}

/**
 * Asserts the value is between the expected start and end values, inclusive.
 * @see [isStrictlyBetween]
 */
fun <A, B : Comparable<A>> Verify<B>.isBetween(start: A, end: A) = given { actual ->
    if (actual >= start && actual <= end) return
    expected("to be between:${show(start)} and ${show(end)} but was:${show(actual)}")
}

/**
 * Asserts the value is between the expected start and end values, non-inclusive.
 * @see [isBetween]
 */
fun <A, B : Comparable<A>> Verify<B>.isStrictlyBetween(start: A, end: A) = given { actual ->
    if (actual > start && actual < end) return
    expected("to be strictly between:${show(start)} and ${show(end)} but was:${show(actual)}")
}

/**
 * Asserts the value if it is close to the expected value with given delta.
 */
fun Verify<Float>.isCloseTo(value: Float, delta: Float) = given { actual ->
    if (actual >= value.minus(delta) && actual <= value.plus(delta)) return
    expected("${show(actual)} to be close to ${show(value)} with delta of ${show(delta)}, but was not")
}

/**
 * Asserts the value if it is close to the expected value with given delta.
 */
fun Verify<Double>.isCloseTo(value: Double, delta: Double) = given { actual ->
    if (actual >= value.minus(delta) && actual <= value.plus(delta)) return
    expected("${show(actual)} to be close to ${show(value)} with delta of ${show(delta)}, but was not")
}

/**
 * Asserts that value is equal when comparing using <code>{@link Comparable#compareTo(Object)}</code>.
 */
fun <T : Comparable<T>> Verify<T>.isEqualByComparingTo(expected: T) = given { actual ->
    if (actual.compareTo(expected) == 0) return
    fail(expected, actual)
}
