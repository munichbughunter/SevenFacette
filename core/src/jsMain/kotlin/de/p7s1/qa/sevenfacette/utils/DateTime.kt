package de.p7s1.qa.sevenfacette.utils

import kotlin.js.Date

actual class DateTime {
    actual companion object {
        actual fun now(): Long {
            TODO("Not yet implemented")
        }

        actual fun getTime(): String {
            return Date().toISOString().substring(11, 23)
        }
    }
}
