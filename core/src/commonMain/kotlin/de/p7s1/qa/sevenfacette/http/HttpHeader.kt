package de.p7s1.qa.sevenfacette.http

import de.p7s1.qa.sevenfacette.utils.Logger
import kotlin.js.JsName

/**
 * Headers to add to http requests
 *
 * @property header list of headers
 *
 * @author Florian Pilz
 */
class HttpHeader {
    private var logger: Logger = Logger()
    val header = mutableListOf<Pair<String, String>>()

    /**
     * Add additional header
     *
     * @param key Used key for header
     * @param value Used value for header
     */
    @JsName("add")
    fun add(key: String, value: String): HttpHeader {
        logger.debug("Adding key == ${key}, value == $value to header")
        header.add(Pair(key, value))
        return this
    }
}
