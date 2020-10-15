package de.p7s1.qa.sevenfacette.config.types

import de.p7s1.qa.sevenfacette.config.types.*
import kotlinx.serialization.KSerializer

actual object FacetteConfig {
    actual var http: HttpConfig?
        get() = TODO("Not yet implemented")
        set(value) {}
    actual var custom: Map<String, String>?
        get() = TODO("Not yet implemented")
        set(value) {}
    actual var kafka: KafkaConfig?
        get() = TODO("Not yet implemented")
        set(value) {}
    actual var database: Map<String, DatabaseConfig>?
        get() = TODO("Not yet implemented")
        set(value) {}
    actual var application: ApplicationConfig?
        get() = TODO("Not yet implemented")
        set(value) {}
    actual var web: WebConfig?
        get() = TODO("Not yet implemented")
        set(value) {}

    actual fun update() {
    }

    actual fun set(config: FacetteConfigDataClass) {
    }

    actual fun reset() {
    }
}
