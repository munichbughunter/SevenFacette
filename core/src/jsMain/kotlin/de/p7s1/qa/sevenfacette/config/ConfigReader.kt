package de.p7s1.qa.sevenfacette.config

import de.p7s1.qa.sevenfacette.config.types.*
import de.p7s1.qa.sevenfacette.utils.KSystem
import kotlinx.serialization.json.Json
import de.p7s1.qa.sevenfacette.config.types.DDatabaseConfig
import de.p7s1.qa.sevenfacette.config.types.DHttpClientConfig
import de.p7s1.qa.sevenfacette.config.types.KafkaTopicConfig
import de.p7s1.qa.sevenfacette.config.types.DSevenFacetteConfig
import de.p7s1.qa.sevenfacette.config.types.DWebConfig
import de.p7s1.qa.sevenfacette.utils.Logger

/**
 * TODO: Add Description
 *
 * @author Patrick Döring
 */
actual class ConfigReader {

    actual companion object {

        /**
         * Reads the configuration.
         *
         * @return FacetteConfigDataClass
         */
        @JsName("readConfig")
        actual fun readConfig(): DSevenFacetteConfig {
            val config = replaceEnvironmentVariables(replaceImports(getConfigFileName().toString()))
            var result = DSevenFacetteConfig()
            if (config != "") {
                result = Json.decodeFromString(DSevenFacetteConfig.serializer(), config)
            }
            return result
        }

        @JsName("getLoggingConfig")
        actual fun getLoggingConfig(): LoggingConfig? = FacetteConfig.log

        @JsName("getHttpConfig")
        actual fun getHttpConfig(): DHttpConfig? =
                FacetteConfig.http

        @JsName("getHttpClientConfig")
        actual fun getHttpClientConfig(clientName: String): DHttpClientConfig? =
                FacetteConfig.http?.clients?.get(clientName)

        @JsName("getKafkaConsumerConfig")
        actual fun getKafkaConsumerConfig(consumerName: String): KafkaTopicConfig? =
                FacetteConfig.kafka?.consumer?.get(consumerName)

        @JsName("getKafkaProducerConfig")
        actual fun getKafkaProducerConfig(producerName: String): KafkaTopicConfig? =
                FacetteConfig.kafka?.producer?.get(producerName)

        @JsName("getDatabaseConfig")
        actual fun getDatabaseConfig(databaseName: String) : DDatabaseConfig? =
                FacetteConfig.database?.get(databaseName)

        @JsName("getCustomConfig")
        actual fun getCustomConfig(key: String) : String? =
                FacetteConfig.custom?.get(key)

        actual fun getSeleniumConfig(seleniumConfig: String) : DWebConfig? {
            TODO("Not yet implemented")
        }

        private fun getConfigFileName(): String? {
            val logger = Logger()
            return if(!KSystem.getEnv("FACETTE_CONFIG").isNullOrEmpty()) {
                logger.info("Use environment variable ${KSystem.getEnv("FACETTE_CONFIG")} for configuration")
                KSystem.getEnv("FACETTE_CONFIG")
            } else if(!KSystem.getProperty("FACETTE_CONFIG").isNullOrEmpty()) {
                logger.info("Use system property ${KSystem.getProperty("FACETTE_CONFIG")} for configuration")
                KSystem.getProperty("FACETTE_CONFIG")
            } else {
                logger.info("Use facetteConfig.json from the root folder for configuration")
                "facetteConfig.json"
            }
        }
    }
}
