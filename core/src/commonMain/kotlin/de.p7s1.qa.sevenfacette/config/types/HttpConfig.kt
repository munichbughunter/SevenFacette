package de.p7s1.qa.sevenfacette.config.types

import kotlinx.serialization.Serializable

@Serializable
data class HttpConfig(
        val clients: Map<String, HttpClientConfig>
) {
    fun getClient(clientName: String): HttpClientConfig? = clients.get(clientName)
}
