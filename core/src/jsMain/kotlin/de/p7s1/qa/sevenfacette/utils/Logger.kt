package de.p7s1.qa.sevenfacette.utils

import kotlin.js.Date

/**
 * JS implementation for logging
 *
 * @author Patrick Döring
 */
class Logger : BaseLogger() {

    override fun getCurrentTime() : String {
        return Date().toISOString().substring(11, 23)
    }
}
