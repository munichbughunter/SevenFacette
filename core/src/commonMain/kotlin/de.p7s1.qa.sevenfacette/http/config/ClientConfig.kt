package de.p7s1.qa.sevenfacette.http.config

import de.p7s1.qa.sevenfacette.http.HttpProxy
import de.p7s1.qa.sevenfacette.http.Url
import kotlinx.serialization.Serializable

@Serializable
class ClientConfig {
    var name: String? = null
    var connectionTimeout: Long = 0
    var connectionRequestTimeout: Long = 0
    var socketTimeout: Long = 0
    var url: Url? = null
    var proxy: HttpProxy? = null
    var authentication: MutableMap<String, String>? = null
}
