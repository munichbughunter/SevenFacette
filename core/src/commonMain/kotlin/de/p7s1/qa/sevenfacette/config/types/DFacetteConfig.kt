package de.p7s1.qa.sevenfacette.config.types

import kotlinx.serialization.Serializable

/**
 * Add more properties if config yaml/ json will be extended and map them to FacetteConfig-object.
 *
 * @property http List of httpConfigs
 * @property kafka List of kafkaConfigs
 * @property database List of databaseConfigs
 * @property application List of applicationConfigs. Is used to combine with string boot projects
 * @property custom map to give the users the possibility to add custom configurations
 * @property web configurations for web project
 *
 * @author Florian Pilz
 */
@Serializable
data class DFacetteConfig (
        var http: DHttpConfig? = null,
        var kafka: DKafkaConfig? = null,
        var database: Map<String, DDatabaseConfig>? = null,
        var application: DApplicationConfig? = null,
        var custom: Map<String, String> = mutableMapOf(),
        var web: DWebConfig? = null
)
