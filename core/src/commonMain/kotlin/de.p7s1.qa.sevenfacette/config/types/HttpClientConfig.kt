package de.p7s1.qa.sevenfacette.config.types

import de.p7s1.qa.sevenfacette.http.HttpProxy
import de.p7s1.qa.sevenfacette.http.Url
import kotlinx.serialization.Serializable

/**
 * Configuration for a single http client
 *
 * @property name String name of the client
 * @property connectionTimeout Int connection timeout in milliseconds
 * @property connectionRequestTimeout Int connection request timeout in milliseconds
 * @property socketTimeout Int socket timeout in milliseconds
 * @property url Url of the http client
 * @property proxy Proxy to be used with the http client
 * @property authentication Mutable map of String, String. Contains the values and the type of the authentication
 *
 * @author Florian Pilz
 */

@Serializable
data class HttpClientConfig (
    var connectionTimeout: Int = 0,
    var connectionRequestTimeout: Int = 0,
    var socketTimeout: Int = 0,
    var url: Url? = null,
    var proxy: HttpProxy? = null,
    var authentication: MutableMap<String, String>? = null
)
