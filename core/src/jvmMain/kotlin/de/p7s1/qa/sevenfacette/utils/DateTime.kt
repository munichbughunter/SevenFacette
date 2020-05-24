package de.p7s1.qa.sevenfacette.utils

actual class DateTime {
    actual companion object {
        @JvmStatic
        actual fun now(): Long {
            return System.currentTimeMillis()
        }
    }
}
