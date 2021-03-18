package de.p7s1.qa.sevenfacette.utils

import de.p7s1.qa.sevenfacette.config.ConfigReader
import de.p7s1.qa.sevenfacette.config.types.LogLevel.*
import kotlin.js.Date

/**
 * TODO: Add Description
 *
 * @author Patrick Döring
 */
class Logger {
    private val loglevel = ConfigReader.getLoggingConfig()?.level
    private val TEXT_RED = "\u001B[31m"
    private val TEXT_YELLOW = "\u001B[33m"
    private val SEVEN_FACETTE = "[7Facette]"

    private fun getCurrentTime() : String {
        return Date().toISOString().substring(11, 23)
    }

    fun info(message: String) {
        if (loglevel == INFO || loglevel == DEBUG) {
            println("${getCurrentTime()} $SEVEN_FACETTE ${INFO.logLevel.toUpperCase()} - $message")
        }
    }

    fun debug(message: String) {
        if (loglevel == DEBUG) {
            println("${getCurrentTime()} $SEVEN_FACETTE ${DEBUG.logLevel.toUpperCase()} - $message")
        }
    }

    fun error(message: String) {
        if (loglevel == ERROR || loglevel == DEBUG || loglevel == WARN || loglevel == INFO) {
            println("${TEXT_RED}${getCurrentTime()} $SEVEN_FACETTE ${ERROR.logLevel.toUpperCase()} - $message")
        }
    }

    fun warn(message: String) {
        if (loglevel == DEBUG || loglevel == WARN || loglevel == INFO) {
            println("${TEXT_YELLOW}${getCurrentTime()} $SEVEN_FACETTE ${WARN.logLevel.toUpperCase()} - $message")
        }
    }
}
