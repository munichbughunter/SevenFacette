package de.p7s1.qa.sevenfacette.config.types

import de.p7s1.qa.sevenfacette.config.types.*
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
        var http: HttpConfig? = null,
        var kafka: KafkaConfig? = null,
        var database: List<DatabaseConfig>? = null,
        var application: ApplicationConfig? = null,
        var custom: Map<String, String> = mutableMapOf()
)
