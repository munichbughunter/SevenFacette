package de.p7s1.qa.sevenfacette.config

import de.p7s1.qa.sevenfacette.config.FacetteConfig.custom
import de.p7s1.qa.sevenfacette.config.FacetteConfig.httpClients
import de.p7s1.qa.sevenfacette.http.config.HttpClientConfig
import kotlinx.serialization.ContextualSerialization
import kotlinx.serialization.Serializable

/**
 * Overall type for configurations. The config yaml files will be mapped to this type.
 * Add more properties if config yaml/ json will be extended and map them to FacetteConfig-object.
 *
 * @property httpClients List of httpClientConfiguration
 * @property custom map to give the users the possibility to add custom configurations
 *
 * @author Florian Pilz
 */
@Serializable
class FacetteConfigDataClass (
    val httpClients: List<HttpClientConfig> = mutableListOf(),
    val custom: Map<String, @ContextualSerialization Any> = mutableMapOf()
)
