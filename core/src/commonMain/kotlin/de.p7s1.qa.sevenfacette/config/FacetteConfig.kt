package de.p7s1.qa.sevenfacette.config

import de.p7s1.qa.sevenfacette.http.config.HttpClientConfig
import kotlinx.serialization.ContextualSerialization

/**
 * Singelton that holds the configuration data globally for the user.
 * In the actual class
 * * Create an init which loads the configuration of the ConfigReader,
 * * then map the results to the objects properties.
 *
 * @property httpClients List of httpClientConfiguration
 * @property custom map to give the users the possibility to add custom configurations
 *
 * @author Florian Pilz
 */
expect object FacetteConfig {
    var httpClients: List<HttpClientConfig>
        private set
    var custom: Map<String, @ContextualSerialization Any>
        private set



}
