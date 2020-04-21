package de.p7s1.qa.sevenfacette.config

import de.p7s1.qa.sevenfacette.http.config.HttpClientConfig

/**
 * Singelton that holds the configuration data globally for the user.
 * n initialization the configuration files will be read and the configuration will be created.
 *
 * @property httpClients List of httpClientConfiguration
 * @property custom map to give the users the possibility to add custom configurations
 *
 * @author Florian Pilz
 */
actual object FacetteConfig {
    actual var httpClients: List<HttpClientConfig>
        private set
    actual var custom: Map<String, Any>
        private set

    init {
        val config = ConfigReader().readConfig()
        this.httpClients = config.httpClients
        this.custom = config.custom
    }

    fun getHttpClient(clientName: String): HttpClientConfig = httpClients.first { it.name == clientName }
}
