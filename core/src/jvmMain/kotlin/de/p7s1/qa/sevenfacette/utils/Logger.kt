package de.p7s1.qa.sevenfacette.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * JVM implementation for logging
 *
 * @author Patrick Döring
 */
class Logger : BaseLogger() {

    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS")

    override fun getCurrentTime() : String {
        val current = LocalDateTime.now()
        return current.format(formatter)
    }
}
