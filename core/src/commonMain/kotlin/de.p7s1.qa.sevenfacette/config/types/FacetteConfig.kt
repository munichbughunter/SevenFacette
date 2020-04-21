package de.p7s1.qa.sevenfacette.config.types

import de.p7s1.qa.sevenfacette.config.types.*
import kotlinx.serialization.ContextualSerialization
import kotlinx.serialization.KSerializer

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
    var http: HttpConfig?
        private set
    var custom: Map<String, String>
        private set
    var kafka: KafkaConfig?
        private set
    var database: List<DatabaseConfig>?
        private set
    var application: ApplicationConfig?
        private set
}
