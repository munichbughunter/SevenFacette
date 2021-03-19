package de.p7s1.qa.sevenfacette.config.types

import de.p7s1.qa.sevenfacette.config.ConfigReader

actual object FacetteConfig {
    @JsName("http")
    actual var http: DHttpConfig? = null
        private set
    @JsName("custom")
    actual var custom: Map<String, String>? = null
        private set

    @JsName("kafka")
    actual var kafka: DKafkaConfig? = null
        private set

    @JsName("database")
    actual var database: Map<String, DDatabaseConfig>? = null
        private set

    @JsName("application")
    actual var application: DApplicationConfig? = null
        private set

    @JsName("web")
    actual var web: DWebConfig? = null
        private set

    @JsName("log")
    actual var log: LoggingConfig? = null
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
