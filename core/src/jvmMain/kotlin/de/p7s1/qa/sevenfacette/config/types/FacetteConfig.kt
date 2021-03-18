package de.p7s1.qa.sevenfacette.config.types

import de.p7s1.qa.sevenfacette.config.ConfigReader

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
    actual var http: DHttpConfig? = null
        private set
    actual var custom: Map<String, String>? = null
        private set
    actual var kafka: DKafkaConfig? = null
        private set
    actual var database: Map<String, DDatabaseConfig>? = null
        private set
    actual var application: DApplicationConfig? = null
        private set
    actual var web: DWebConfig? = null
        private set
    var log: LoggingConfig? = null
        private set

    init {
        update()
    }

    actual fun update() {
        val config = ConfigReader.readConfig().sevenFacette
        set(config!!)
    }

    actual fun set(config: DFacetteConfig) {
        http = config.http
        custom = config.custom
        kafka = config.kafka
        database = config.database
        application = config.application
        web = config.web
        log = config.log
    }

    actual fun reset() {
        http = null
        custom = null
        kafka = null
        database = null
        application = null
        web = null
        log = null
    }
}
