package de.p7s1.qa.sevenfacette.config

import de.p7s1.qa.sevenfacette.http.config.ClientConfig
import kotlinx.serialization.Serializable

/**
 * Overall type for configurations. The config yaml files will be mapped to this type.
 * Add more properties if config yaml/ json will be extended.
 */
@Serializable
data class FacetteConfig (
    var httpClients: List<ClientConfig> = mutableListOf()
)

