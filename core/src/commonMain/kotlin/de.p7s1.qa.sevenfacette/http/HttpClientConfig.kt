package de.p7s1.qa.sevenfacette.http

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
    var authentication: Authentication? = null
    var proxy: HttpProxy? = null
}
