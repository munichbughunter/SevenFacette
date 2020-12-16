package de.p7s1.qa.sevenfacette.config

import com.charleskorn.kaml.Yaml
import de.p7s1.qa.sevenfacette.config.types.*

/**
 * Class to read the config yaml file(s).
 * If an environment variable or system property FACETTE_CONFIG is provided this file will be used.
 * The configuration yaml in the resource root folder will be used.
 *
 * @author Florian Pilz
 */
//private val logger = KotlinLogging.logger {}
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
        actual fun getHttpConfig(clientName: String): HttpClientConfig? =
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
        actual fun getSeleniumConfig(seleniumConfig: String): WebConfig? =
                FacetteConfig.web
    }
}
