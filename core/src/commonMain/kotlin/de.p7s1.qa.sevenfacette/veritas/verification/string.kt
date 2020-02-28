package de.p7s1.qa.sevenfacette.veritas.verification

import de.p7s1.qa.sevenfacette.veritas.Verify
import de.p7s1.qa.sevenfacette.veritas.verification.utils.expected
import de.p7s1.qa.sevenfacette.veritas.verification.utils.fail
import de.p7s1.qa.sevenfacette.veritas.verification.utils.show

/**
 * Asserts the string has the expected number of lines.
 */
fun Verify<String>.hasLineCount(lineCount: Int) = given { actual ->
    val actualLineCount = actual.lines().size
    if (actualLineCount == lineCount) return
    expected("to have line count:${show(lineCount)} but was:${show(actualLineCount)}")
}

/**
 * Asserts the string is equal to the expected string.
 * @param ignoreCase true to compare ignoring case, the default if false.
 * @see [isNotEqualTo]
 */
fun Verify<String?>.isEqualTo(other: String?, ignoreCase: Boolean = false) = given { actual ->
    if (actual.equals(other, ignoreCase)) return
    fail(other, actual)
}

/**
 * Asserts the string is not equal to the expected string.
 * @param ignoreCase true to compare ignoring case, the default if false.
 * @see [isEqualTo]
 */
fun Verify<String?>.isNotEqualTo(other: String?, ignoreCase: Boolean = false) = given { actual ->
    if (!actual.equals(other, ignoreCase)) return
    if (ignoreCase) {
        expected(":${show(other)} not to be equal to (ignoring case):${show(actual)}")
    } else {
        expected("to not be equal to:${show(actual)}")
    }
}

/**
 * Asserts the string contains the expected substring.
 * @param ignoreCase true to compare ignoring case, the default if false.
 */
fun Verify<String>.contains(expected: CharSequence, ignoreCase: Boolean = false) = given { actual ->
    if (actual.contains(expected, ignoreCase)) return
    expected("to contain:${show(expected)} but was:${show(actual)}")
}

/**
 * Asserts the string contains the expected substring(s).
 * @param ignoreCase true to compare ignoring case, the default if false.
 */
fun Verify<String>.contains(vararg expected: CharSequence, ignoreCase: Boolean = false) = given { actual ->
    if (expected.all { actual.contains(it, ignoreCase) }) return
    expected("to contain:${show(expected)} but was:${show(actual)}")
}

/**
 * Asserts the string contains the expected strings.
 * @param ignoreCase true to compare ignoring case, the default if false.
 */
fun Verify<String>.contains(expected: Iterable<CharSequence>, ignoreCase: Boolean = false) = given { actual ->
    if (expected.all { actual.contains(it, ignoreCase) }) return
    expected("to contain:${show(expected)} but was:${show(actual)}")
}

/**
 * Asserts the string does not contain the specified string.
 * @param ignoreCase true to compare ignoring case, the default if false.
 */
fun Verify<String>.doesNotContain(expected: CharSequence, ignoreCase: Boolean = false) = given { actual ->
    if (!actual.contains(expected, ignoreCase)) return
    expected("to not contain:${show(expected)} but was:${show(actual)}")
}

/**
 * Asserts the string does not contain the specified string(s).
 * @param ignoreCase true to compare ignoring case, the default if false.
 */
fun Verify<String>.doesNotContain(vararg expected: CharSequence, ignoreCase: Boolean = false) = given { actual ->
    if (expected.none { actual.contains(it, ignoreCase) }) return
    expected("to not contain:${show(expected)} but was:${show(actual)}")
}

/**
 * Asserts the string does not contain the specified strings.
 * @param ignoreCase true to compare ignoring case, the default if false.
 */
fun Verify<String>.doesNotContain(expected: Iterable<CharSequence>, ignoreCase: Boolean = false) = given { actual ->
    if (expected.none { actual.contains(it, ignoreCase) }) return
    expected("to not contain:${show(expected)} but was:${show(actual)}")
}

/**
 * Asserts the string starts with the expected string.
 * @param ignoreCase true to compare ignoring case, the default if false.
 * @see [endsWith]
 */
fun Verify<String>.startsWith(other: String, ignoreCase: Boolean = false) = given { actual ->
    if (actual.startsWith(other, ignoreCase)) return
    expected("to start with:${show(other)} but was:${show(actual)}")
}

/**
 * Asserts the string ends with the expected string.
 * @param ignoreCase true to compare ignoring case, the default if false.
 * @see [startsWith]
 */
fun Verify<String>.endsWith(other: String, ignoreCase: Boolean = false) = given { actual ->
    if (actual.endsWith(other, ignoreCase)) return
    expected("to end with:${show(other)} but was:${show(actual)}")
}

/**
 * Asserts the string matches the expected regular expression.
 */
fun Verify<String>.matches(regex: Regex) = given { actual ->
    if (actual.matches(regex)) return
    expected("to match:${show(regex)} but was:${show(actual)}")
}
