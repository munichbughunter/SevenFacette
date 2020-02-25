package de.p7s1.qa.sevenfacette.veritas.verification

import de.p7s1.qa.sevenfacette.veritas.Verify
import de.p7s1.qa.sevenfacette.veritas.verification.utils.expected
import de.p7s1.qa.sevenfacette.veritas.verification.utils.show

/**
 * Asserts the number is 0.
 * @see [isNotZero]
 */
fun Verify<Number>.isZero() = given { actual ->
    if (actual.toDouble() == 0.0) return
    expected("to be 0 but was:${show(actual)}")
}

/**
 * Asserts the number is not 0.
 * @see [isZero]
 */
fun Verify<Number>.isNotZero() = given { actual ->
    if (actual.toDouble() != 0.0) return
    expected("to not be 0")
}

/**
 * Asserts the number is greater than 0.
 * @see [isNegative]
 */
fun Verify<Number>.isPositive() = given { actual ->
    if (actual.toDouble() > 0) return
    expected("to be positive but was:${show(actual)}")
}

/**
 * Asserts the number is less than 0.
 * @see [isPositive]
 */
fun Verify<Number>.isNegative() = given { actual ->
    if (actual.toDouble() < 0) return
    expected("to be negative but was:${show(actual)}")
}
