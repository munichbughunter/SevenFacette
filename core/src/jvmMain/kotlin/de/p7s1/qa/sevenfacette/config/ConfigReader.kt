package de.p7s1.qa.sevenfacette.config

import com.charleskorn.kaml.Yaml
import de.p7s1.qa.sevenfacette.config.types.*
import de.p7s1.qa.sevenfacette.utils.Logger
import de.p7s1.qa.sevenfacette.utils.Files
import de.p7s1.qa.sevenfacette.utils.KSystem

/**
 * Class to read the config yaml file(s).
 * If an environment variable or system property FACETTE_CONFIG is provided this file will be used.
 * The configuration yaml in the resource root folder will be used.
 *
 * @author Florian Pilz
 */
actual class ConfigReader {

    actual companion object {
        /**
         * Reads the configuration.
         *
         * @return FacetteConfigDataClass
         */
        actual fun readConfig(): SevenFacetteConfig {
            val config = replaceEnvironmentVariables(replaceImports(getConfigFileName().toString()))
            var result = SevenFacetteConfig()
            if(config != "") {
                result = Yaml.default.decodeFromString(SevenFacetteConfig.serializer(), config)
            }
            return result
        }

        @JvmStatic
        actual fun getLoggingConfig(): LoggingConfig? = FacetteConfig.log

        @JvmStatic
        actual fun getHttpConfig(): HttpConfig? = FacetteConfig.http

        @JvmStatic
        actual fun getHttpClientConfig(clientName: String): HttpClientConfig? =
                FacetteConfig.http?.clients?.get(clientName)

        @JvmStatic
        actual fun getKafkaConsumerConfig(consumerName: String): KafkaTopicConfig? =
                FacetteConfig.kafka?.consumer?.get(consumerName)

        @JvmStatic
        actual fun getKafkaProducerConfig(producerName: String): KafkaTopicConfig? =
                FacetteConfig.kafka?.producer?.get(producerName)

        @JvmStatic
        actual fun getDatabaseConfig(databaseName: String) : DatabaseConfig? =
                FacetteConfig.database?.get(databaseName)

        @JvmStatic
        actual fun getCustomConfig(key: String) : String? =
                FacetteConfig.custom?.get(key)

        @JvmStatic
        actual fun getSeleniumConfig(seleniumConfig: String): WebConfig? =
                FacetteConfig.web

        /**
         * This function uses the env variable provided by the user for the config file or a default file
         */
        private fun getConfigFileName(): String? {
            val logger = Logger()
            return if(!KSystem.getEnv("FACETTE_CONFIG").isNullOrEmpty()) {
                logger.info("Use environment variable ${KSystem.getEnv("FACETTE_CONFIG")} for configuration")
                KSystem.getEnv("FACETTE_CONFIG")
            } else if(!KSystem.getProperty("FACETTE_CONFIG").isNullOrEmpty()) {
                logger.info("Use system property ${KSystem.getProperty("FACETTE_CONFIG")} for configuration")
                KSystem.getProperty("FACETTE_CONFIG")
            } else if(Files.getResource("facetteConfig.yml") != null) {
                logger.info("Use facetteConfig.yml from resource for configuration")
                "facetteConfig.yml"
            } else if(Files.getResource("facetteConfig.yaml") != null) {
                logger.info("Use facetteConfig.yaml from resource for configuration")
                "facetteConfig.yaml"
            } else {
                logger.error("No valid configuration file found!")
                throw Error("No configuration file found")
            }
        }
    }
}
