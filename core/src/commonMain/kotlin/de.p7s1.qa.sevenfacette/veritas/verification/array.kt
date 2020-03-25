package de.p7s1.qa.sevenfacette.veritas.verification

import de.p7s1.qa.sevenfacette.veritas.Verify
import de.p7s1.qa.sevenfacette.veritas.all
import de.p7s1.qa.sevenfacette.veritas.verification.utils.expected
import de.p7s1.qa.sevenfacette.veritas.verification.utils.fail
import de.p7s1.qa.sevenfacette.veritas.verification.utils.show

/**
 * Returns an assert on the Arrays's size.
 */
fun Verify<Array<*>>.size() = prop("size") { it.size }

/**
 * Asserts the array contents are equal to the expected one, using [contentDeepEquals].
 * @see isNotEqualTo
 */
fun <T> Verify<Array<T>>.isEqualTo(expected: Array<T>) = given { actual ->
    if (actual.contentDeepEquals(expected)) return
    fail(expected, actual)
}

/**
 * Asserts the array contents are not equal to the expected one, using [contentDeepEquals].
 * @see isEqualTo
 */
fun <T> Verify<Array<T>>.isNotEqualTo(expected: Array<T>) = given { actual ->
    if (!(actual.contentDeepEquals(expected))) return
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
 * Asserts the array is empty.
 * @see [isNotEmpty]
 * @see [isNullOrEmpty]
 */
fun Verify<Array<*>>.isEmpty() = given { actual ->
    if (actual.isEmpty()) return
    expected("to be empty but was:${show(actual)}")
}

/**
 * Asserts the array is not empty.
 * @see [isEmpty]
 */
fun Verify<Array<*>>.isNotEmpty() = given { actual ->
    if (actual.isNotEmpty()) return
    expected("to not be empty")
}

/**
 * Asserts the array is null or empty.
 * @see [isEmpty]
 */
fun Verify<Array<*>?>.isNullOrEmpty() = given { actual ->
    if (actual == null || actual.isEmpty()) return
    expected("to be null or empty but was:${show(actual)}")
}

/**
 * Asserts the array has the expected size.
 */
fun Verify<Array<*>>.hasSize(size: Int) {
    size().isEqualTo(size)
}

/**
 * Asserts the array has the same size as the expected array.
 */
fun Verify<Array<*>>.hasSameSizeAs(other: Array<*>) = given { actual ->
    val actualSize = actual.size
    val otherSize = other.size
    if (actualSize == otherSize) return
    expected("to have same size as:${show(other)} ($otherSize) but was size:($actualSize)")
}

/**
 * Asserts the array contains the expected element, using `in`.
 * @see [doesNotContain]
 */
fun Verify<Array<*>>.contains(element: Any?) = given { actual ->
    if (element in actual) return
    expected("to contain:${show(element)} but was:${show(actual)}")
}

/**
 * Asserts the array does not contain the expected element, using `!in`.
 * @see [contains]
 */
fun Verify<Array<*>>.doesNotContain(element: Any?) = given { actual ->
    if (element !in actual) return
    expected("to not contain:${show(element)} but was:${show(actual)}")
}

/**
 * Asserts the collection does not contain any of the expected elements.
 * @see [containsAll]
 */
fun Verify<Array<*>>.containsNone(vararg elements: Any?) = given { actual ->
    if (elements.none { it in actual }) {
        return
    }

    val notExpected = elements.filter { it in actual }
    expected("to contain none of:${show(elements)} " +
            "but was:${show(actual)}\n " +
            "elements not expected:${show(notExpected)}")
}

/**
 * Asserts the array contains all the expected elements, in any order. The array may also contain
 * additional elements.
 * @see [containsExactly]
 */
fun Verify<Array<*>>.containsAll(vararg elements: Any?) = given { actual ->
    if (elements.all { actual.contains(it) }) return
    val notFound = elements.filterNot { it in actual }
    expected("to contain all:${show(elements)} " +
            "but was:${show(actual)}\n " +
            "elements not found:${show(notFound)}")
}

/**
 * Asserts the array contains only the expected elements, in any order.
 * @see [containsNone]
 * @see [containsExactly]
 * @see [containsAll]
 */
fun Verify<Array<*>>.containsOnly(vararg elements: Any?) = given { actual ->
    val notInActual = elements.filterNot { it in actual }
    val notInExpected = actual.filterNot { it in elements }
    if (notInExpected.isEmpty() && notInActual.isEmpty()) {
        return
    }
    expected(StringBuilder("to contain only:${show(elements)} " +
            "but was:${show(actual)}").apply {
        if (notInActual.isNotEmpty()) {
            append("\n elements not found:${show(notInActual)}")
        }
        if (notInExpected.isNotEmpty()) {
            append("\n extra elements found:${show(notInExpected)}")
        }
    }.toString())
}

/**
 * Returns an assert that assertion on the value at the given index in the array.
 *
 * ```
 * assertThat(arrayOf(0, 1, 2)).index(1).isPositive()
 * ```
 */
fun <T> Verify<Array<T>>.index(index: Int): Verify<T> =
        transform("${name ?: ""}${show(index, "[]")}") { actual ->
            if (index in 0 until actual.size) {
                actual[index]
            } else {
                expected("index to be in range:[0-${actual.size}) but was:${show(index)}")
            }
        }

/**
 * Asserts the array contains exactly the expected elements. They must be in the same order and
 * there must not be any extra elements.
 * @see [containsAll]
 */
fun Verify<Array<*>>.containsExactly(vararg elements: Any?) = given { actual ->
    if (actual.contentEquals(elements)) return

    expected(listDifferExpected(elements.asList(), actual.asList()))
}

/**
 * Asserts on each item in the array. The given lambda will be run for each item.
 *
 * ```
 * assertThat(arrayOf("one", "two")).each {
 *   it.hasLength(3)
 * }
 * ```
 */
fun <T> Verify<Array<T>>.each(f: (Verify<T>) -> Unit) = given { actual ->
    all {
        actual.forEachIndexed { index, item ->
            f(verifyThat(item, name = "${name ?: ""}${show(index, "[]")}"))
        }
    }
}

/**
 * Extracts a value of from each item in the array, allowing you to assert on a list of those values
 *
 * ```
 * assertThat(people)
 *   .extracting(Person::name)
 *   .contains("Sue", "Bob")
 * ```
 */
fun <E, R> Verify<Array<E>>.extracting(f1: (E) -> R): Verify<List<R>> = transform { actual ->
    actual.map(f1)
}

/**
 * Extracts two values of from each item in the array, allowing you to assert on a list of
 * paris of those values.
 *
 * ```
 * assertThat(people)
 *   .extracting(Person::name, Person::age)
 *   .contains("Sue" to 20, "Bob" to 22)
 * ```
 */
fun <E, R1, R2> Verify<Array<E>>.extracting(f1: (E) -> R1, f2: (E) -> R2): Verify<List<Pair<R1, R2>>> =
        transform { actual ->
            actual.map { f1(it) to f2(it) }
        }

/**
 * Extracts three values from each item in the array, allowing you to assert on a list of
 * triples of those values.
 *
 * ```
 * assertThat(people)
 *   .extracting(Person::name, Person::age, Person::address)
 *   .contains(Triple("Sue", 20, "123 Street"), Triple("Bob", 22, "456 Street")
 * ```
 */
fun <E, R1, R2, R3> Verify<Array<E>>.extracting(
        f1: (E) -> R1,
        f2: (E) -> R2,
        f3: (E) -> R3
): Verify<List<Triple<R1, R2, R3>>> = transform { actual ->
    actual.map { Triple(f1(it), f2(it), f3(it)) }
}
