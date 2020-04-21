package de.p7s1.qa.sevenfacette.config.types

import kotlinx.serialization.Serializable

@Serializable
data class HttpConfig(
        val clients: List<HttpClientConfig>
) {
    fun getClient(clientName: String): HttpClientConfig? = clients.first { it.name == clientName }
}
