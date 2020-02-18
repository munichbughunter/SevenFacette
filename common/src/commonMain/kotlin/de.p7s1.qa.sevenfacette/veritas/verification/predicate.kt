package de.p7s1.qa.sevenfacette.veritas.verification

import de.p7s1.qa.sevenfacette.veritas.Verify
import de.p7s1.qa.sevenfacette.veritas.verification.utils.expected

/**
 * Asserts if the values satisfies the predicate provided.
 *
 * ```
 * val divisibleBy5 : (Int) -> Boolean = { it % 5 == 0 }
 * assert(10).matchesPredicate(divisibleBy5)
 * ```
 */
fun <T> Verify<T>.matchesPredicate(f: (T) -> Boolean) = given { actual ->
    if (f(actual)) return
    expected("$actual to satisfy the predicate")
}
