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
    actual var http: HttpConfig? = null
        private set
    actual var custom: Map<String, String>? = null
        private set
    actual var kafka: KafkaConfig? = null
        private set
    actual var database: Map<String, DatabaseConfig>? = null
        private set
    actual var application: ApplicationConfig? = null
        private set
    actual var web: WebConfig? = null
        private set

    init {
        update()
    }

    actual fun update() {
        val config = ConfigReader.readConfig().sevenFacette
        set(config!!)
    }

    actual fun set(config: FacetteConfigDataClass) {
        http = config.http
        custom = config.custom
        kafka = config.kafka
        database = config.database
        application = config.application
        web = config.web
    }

    actual fun reset() {
        http = null
        custom = null
        kafka = null
        database = null
        application = null
        web = null
    }
}
