package de.p7s1.qa.sevenfacette.config

import com.charleskorn.kaml.Yaml
import de.p7s1.qa.sevenfacette.config.types.*
import de.p7s1.qa.sevenfacette.utils.Files
import de.p7s1.qa.sevenfacette.utils.KSystem
import mu.KotlinLogging

/**
 * Class to read the config yaml file(s).
 * If an environment variable or system property FACETTE_CONFIG is provided this file will be used.
 * The configuration yaml in the resource root folder will be used.
 *
 * @author Florian Pilz
 */
private val logger = KotlinLogging.logger {}
actual class ConfigReader {

    actual companion object {
        /**
         * Reads the configuration.
         *
         * @return FacetteConfigDataClass
         */
        actual fun readConfig(): DSevenFacetteConfig {
            val config = replaceEnvironmentVariables(replaceImports(getConfigFileName().toString()))
            var result = DSevenFacetteConfig()
            if(config != "") {
                result = Yaml.default.decodeFromString(DSevenFacetteConfig.serializer(), config)
            }
            return result
        }

        @JvmStatic
        actual fun getHttpConfig(): DHttpConfig? = FacetteConfig.http

        @JvmStatic
        actual fun getHttpClientConfig(clientName: String): DHttpClientConfig? =
                FacetteConfig.http?.clients?.get(clientName)

        @JvmStatic
        actual fun getKafkaConsumerConfig(consumerName: String): KafkaTopicConfig? =
                FacetteConfig.kafka?.consumer?.get(consumerName)

        @JvmStatic
        actual fun getKafkaProducerConfig(producerName: String): KafkaTopicConfig? =
                FacetteConfig.kafka?.producer?.get(producerName)

        @JvmStatic
        actual fun getDatabaseConfig(databaseName: String) : DDatabaseConfig? =
                FacetteConfig.database?.get(databaseName)

        @JvmStatic
        actual fun getCustomConfig(key: String) : String? =
                FacetteConfig.custom?.get(key)

        @JvmStatic
        actual fun getSeleniumConfig(seleniumConfig: String): DWebConfig? =
                FacetteConfig.web

        /**
         * This function uses the env variable provided by the user for the config file or a default file
         */
        private fun getConfigFileName(): String? {
            return if(!KSystem.getEnv("FACETTE_CONFIG").isNullOrEmpty()) {
                //println("Use environment variable ${KSystem.getEnv("FACETTE_CONFIG")} for configuration")
                logger.info { "Use environment variable ${KSystem.getEnv("FACETTE_CONFIG")} for configuration" }
                KSystem.getEnv("FACETTE_CONFIG")
            } else if(!KSystem.getProperty("FACETTE_CONFIG").isNullOrEmpty()) {
                logger.info { "Use system property ${KSystem.getProperty("FACETTE_CONFIG")} for configuration" }
                //println("Use environment variable ${KSystem.getProperty("FACETTE_CONFIG")} for configuration")
                KSystem.getProperty("FACETTE_CONFIG")
            } else if(Files.getResource("facetteConfig.yml") != null) {
                //println("Use facetteConfig.yml for configuration")
                logger.info { "Use facetteConfig.yml from resource for configuration" }
                "facetteConfig.yml"
            } else if(Files.getResource("facetteConfig.yaml") != null) {
                //println("Use facetteConfig.yaml for configuration")
                logger.info { "Use facetteConfig.yaml from resource for configuration" }
                "facetteConfig.yaml"
            } else {
                throw Error("No configuration file found")
            }
        }
    }
}
