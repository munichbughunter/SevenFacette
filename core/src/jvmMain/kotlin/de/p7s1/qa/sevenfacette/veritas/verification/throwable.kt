@file:JvmName("ThrowableJVMKt")
package de.p7s1.qa.sevenfacette.veritas.verification

import de.p7s1.qa.sevenfacette.veritas.Verify

/**
 * Returns an assert on the throwable's stack trace.
 */
fun Verify<Throwable>.stackTrace() = prop("stackTrace") { it.stackTrace.map(StackTraceElement::toString) }
