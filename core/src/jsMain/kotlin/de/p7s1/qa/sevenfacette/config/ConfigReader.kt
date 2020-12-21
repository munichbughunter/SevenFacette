package de.p7s1.qa.sevenfacette.config

import de.p7s1.qa.sevenfacette.config.types.*
import de.p7s1.qa.sevenfacette.utils.KSystem
import kotlinx.serialization.json.Json
import de.p7s1.qa.sevenfacette.config.types.DatabaseConfig
import de.p7s1.qa.sevenfacette.config.types.HttpClientConfig
import de.p7s1.qa.sevenfacette.config.types.KafkaTopicConfig
import de.p7s1.qa.sevenfacette.config.types.SevenFacetteConfig
import de.p7s1.qa.sevenfacette.config.types.WebConfig

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
        actual fun readConfig(): SevenFacetteConfig {
            val config = replaceEnvironmentVariables(replaceImports(getConfigFileName().toString()))
            var result = SevenFacetteConfig()
            if (config != "") {
                result = Json.decodeFromString(SevenFacetteConfig.serializer(), config)
            }
            return result
        }

        @JsName("getHttpConfig")
        actual fun getHttpConfig(): HttpConfig? =
                FacetteConfig.http

        @JsName("getHttpClientConfig")
        actual fun getHttpClientConfig(clientName: String): HttpClientConfig? =
                FacetteConfig.http?.clients?.get(clientName)

        @JsName("getKafkaConsumerConfig")
        actual fun getKafkaConsumerConfig(consumerName: String): KafkaTopicConfig? =
                FacetteConfig.kafka?.consumer?.get(consumerName)

        @JsName("getKafkaProducerConfig")
        actual fun getKafkaProducerConfig(producerName: String): KafkaTopicConfig? =
                FacetteConfig.kafka?.producer?.get(producerName)

        @JsName("getDatabaseConfig")
        actual fun getDatabaseConfig(databaseName: String) : DatabaseConfig? =
                FacetteConfig.database?.get(databaseName)

        @JsName("getCustomConfig")
        actual fun getCustomConfig(key: String) : String? =
                FacetteConfig.custom?.get(key)


        actual fun getSeleniumConfig(seleniumConfig: String) : WebConfig? {
            TODO("Not yet implemented")
        }

        private fun getConfigFileName(): String? {
            return if(!KSystem.getEnv("FACETTE_CONFIG").isNullOrEmpty()) {
                println("Use environment variable ${KSystem.getEnv("FACETTE_CONFIG")} for configuration")
                KSystem.getEnv("FACETTE_CONFIG")
            } else if(!KSystem.getProperty("FACETTE_CONFIG").isNullOrEmpty()) {
                println("Use environment variable ${KSystem.getProperty("FACETTE_CONFIG")} for configuration")
                KSystem.getProperty("FACETTE_CONFIG")
            } else {
                println("Use facetteConfig.json in root folder for configuration")
                "facetteConfig.json"
            }
        }
    }
}
