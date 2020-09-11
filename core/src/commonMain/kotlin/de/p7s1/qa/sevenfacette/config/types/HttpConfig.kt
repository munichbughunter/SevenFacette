package de.p7s1.qa.sevenfacette.config.types

import kotlinx.serialization.Serializable

/**
 * Contains map of httpClientConfigs. The coonfigurations can be accessed via name.
 */
@Serializable
data class HttpConfig(
    val clients: Map<String, HttpClientConfig>
) {
    fun getClient(clientName: String): HttpClientConfig? = clients[clientName]
}
