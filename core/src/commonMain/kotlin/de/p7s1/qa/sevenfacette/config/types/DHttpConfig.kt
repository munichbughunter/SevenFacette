package de.p7s1.qa.sevenfacette.config.types

import de.p7s1.qa.sevenfacette.http.HttpProxy
import kotlinx.serialization.Serializable
import kotlin.js.JsName

/**
 * Contains map of httpClientConfigs. The configurations can be accessed via name.
 */
@Serializable
data class DHttpConfig(
        var connectionTimeout: Int = 0,
        var connectionRequestTimeout: Int = 0,
        var socketTimeout: Int = 0,
        var proxy: HttpProxy? = null,
        val clients: Map<String, DHttpClientConfig>
) {
    @JsName("get")
    fun get(key: String) : DHttpClientConfig? = clients[key]
}
