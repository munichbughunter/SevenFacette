@file:JvmName("SupportJVMKt")
package de.p7s1.qa.sevenfacette.veritas.verification.utils

internal actual fun displayPlatformSpecific(value: Any?): String {
    return when (value) {
        is Byte -> "0x%02X".format(value)
        is Float -> "${value}f"
        is Regex -> "/$value/"
        is Class<*> -> value.name
        else -> value.toString()
    }
}
