package de.p7s1.qa.sevenfacette.veritas

@Suppress("NOTHING_TO_INLINE")
internal actual inline fun failWithNotInStacktrace(error: Throwable): Nothing {
    throw error
}
