package de.p7s1.qa.sevenfacette.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

actual class DateTime {
    actual companion object {
        private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS")

        @JvmStatic
        actual fun now(): Long {
            return System.currentTimeMillis()
        }

        @JvmStatic
        actual fun getCurrentTimeClock(): String {
            val current = LocalDateTime.now()
            return current.format(formatter)
        }
    }
}
