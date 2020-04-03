package de.p7s1.qa.sevenfacette.http

import mu.KotlinLogging

/**
 * Headers to add to http requests
 *
 * @property header list of headers
 *
 * @author Florian Pilz
 */

private val logger = KotlinLogging.logger {}
class HttpHeader {
    val header = mutableListOf<Pair<String, String>>()

    /**
     * Add additional header
     *
     * @param key Used key for header
     * @param value Used value for header
     */
    fun add(key: String, value: String): HttpHeader {
        logger.debug { "Adding key == ${key}, value == $value to header" }
        header.add(Pair(key, value))
        return this
    }
}
