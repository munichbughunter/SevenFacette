package de.p7s1.qa.sevenfacette.http

import de.p7s1.qa.sevenfacette.http.config.Authentication

/**
 * Config used for creation of generic http clients
 *
 * @author Florian Pilz
 */
class HttpConfig {
    lateinit var url: Url
    var socketTimeout = 10_000
    var connectionTimeout = 10_000
    var connectionRequestTimeout = 20_000
    var authentication: MutableMap<String, String>? = null
    var proxy: HttpProxy? = null
}
