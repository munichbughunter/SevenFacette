package de.p7s1.qa.sevenfacette.veritas.verification

import de.p7s1.qa.sevenfacette.veritas.Verify
import java.math.BigDecimal

/**
 * Asserts that <code>actual.compareTo(BigDecimal(expected) == 0</code>.
 */
fun Verify<BigDecimal>.isEqualByComparingTo(expected: String) = isEqualByComparingTo(BigDecimal(expected))
