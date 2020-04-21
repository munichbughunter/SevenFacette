package de.p7s1.qa.sevenfacette.config.types

import de.p7s1.qa.sevenfacette.config.ConfigReader
import kotlinx.serialization.KSerializer

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
    actual var http: HttpConfig?
        private set
    actual var custom: Map<String, String>
        private set
    actual var kafka: KafkaConfig?
        private set
    actual var database: List<DatabaseConfig>?
        private set
    actual var application: ApplicationConfig?
        private set

    init {
        val config = ConfigReader().readConfig()
        http = config.http
        custom = config.custom
        kafka = config.kafka
        database = config.database
        application = config.application
    }
}
