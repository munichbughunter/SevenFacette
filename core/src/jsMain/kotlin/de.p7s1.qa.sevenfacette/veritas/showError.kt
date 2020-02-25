package de.p7s1.qa.sevenfacette.veritas

import de.p7s1.qa.sevenfacette.veritas.verification.utils.show

/**
 * Calls platform specific function so that it is possible to show stacktrace if able
 *
 * TODO: use @OptionalExpectation (https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-optional-expectation/index.html) here once available and call default implementation of [show] for JS
 */
internal actual fun showError(e: Throwable) = show(e)
