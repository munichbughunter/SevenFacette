package de.p7s1.qa.sevenfacette.config.types

import de.p7s1.qa.sevenfacette.config.ConfigReader
import kotlin.js.Promise

@ExperimentalJsExport
@JsExport
actual object FacetteConfig {
    @JsName("http")
    actual var http: HttpConfig? = null
        private set
    @JsName("custom")
    actual var custom: Map<String, String>? = null
        private set

    @JsName("kafka")
    actual var kafka: KafkaConfig? = null
        private set

    @JsName("database")
    actual var database: Map<String, DatabaseConfig>? = null
        private set

    @JsName("application")
    actual var application: ApplicationConfig? = null
        private set

    @JsName("web")
    actual var web: WebConfig? = null
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

    actual fun set(config: FacetteConfigDataClass) {
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
