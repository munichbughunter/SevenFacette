package de.p7s1.qa.sevenfacette.utils

import de.p7s1.qa.sevenfacette.config.ConfigReader
import de.p7s1.qa.sevenfacette.config.types.LogLevel.*
import de.p7s1.qa.sevenfacette.utils.DateTime.Companion.getCurrentTimeClock
import de.p7s1.qa.sevenfacette.utils.DateTime.Companion.now

/**
 * Base logger for logging events
 *
 * @author Patrick Döring
 */
open class Logger {

    private val loglevel = ConfigReader.getLoggingConfig()?.level
    private val TEXT_RED = "\u001B[31m"
    private val TEXT_YELLOW = "\u001B[33m"
    private val TEXT_RESET = "\u001B[0m"
    private val SEVEN_FACETTE = "[7Facette]"

    open fun getCurrentTime() : String {
        return getCurrentTimeClock()
    }

    fun info(message: Any?) {
        if (loglevel in arrayOf(INFO, DEBUG)) {
            println("${getCurrentTime()} $SEVEN_FACETTE ${INFO.logLevel.toUpperCase()} - $message")
        }
    }

    fun debug(message: Any?) {
        if (loglevel in arrayOf(DEBUG)) {
            println("${getCurrentTime()} $SEVEN_FACETTE ${DEBUG.logLevel.toUpperCase()} - $message")
        }
    }

    fun error(message: Any?) {
        if (loglevel in arrayOf(ERROR, DEBUG, WARN, INFO)) {
            println("${TEXT_RED}${getCurrentTime()} $SEVEN_FACETTE ${ERROR.logLevel.toUpperCase()} - $message${TEXT_RESET}")
        }
    }

    fun error(t: Throwable) {
        if (loglevel in arrayOf(ERROR, DEBUG, WARN, INFO)) {
            println("${TEXT_RED}${getCurrentTime()} $SEVEN_FACETTE ${ERROR.logLevel.toUpperCase()} - $t${TEXT_RESET}")
        }
    }

    fun warn(message: Any?) {
        if (loglevel in arrayOf(DEBUG, WARN, INFO)) {
            println("${TEXT_YELLOW}${getCurrentTime()} $SEVEN_FACETTE ${WARN.logLevel.toUpperCase()} - $message${TEXT_RESET}")
        }
    }
}
