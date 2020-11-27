package de.p7s1.qa.sevenfacette.config

import de.p7s1.qa.sevenfacette.config.types.*
import de.p7s1.qa.sevenfacette.utils.FileReader
import kotlinx.serialization.json.Json

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
            val path = FileReader().getPath("/facetteConfig.json")
            // FACETTE_CONFIG = ""
            //val config = replaceEnvironmentVariables(replaceImports(getConfigFileName().toString()))
            val config = FileReader().readFileAsString(path)
            var result = SevenFacetteConfig()
            if(config != "") {
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
    }
}