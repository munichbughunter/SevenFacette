package de.p7s1.qa.sevenfacette.config

import de.p7s1.qa.sevenfacette.config.types.*

/**
 * TODO: Add Description
 *
 * @author Patrick Döring
 */
expect class ConfigReader {
    companion object {
        fun readConfig(): DSevenFacetteConfig

        fun getHttpConfig() : DHttpConfig?

        fun getHttpClientConfig(clientName: String) : DHttpClientConfig?

        fun getKafkaConsumerConfig(consumerName: String) : KafkaTopicConfig?

        fun getKafkaProducerConfig(producerName: String) : KafkaTopicConfig?

        fun getDatabaseConfig(databaseName: String) : DDatabaseConfig?

        fun getSeleniumConfig(seleniumConfig: String) : DWebConfig?

        fun getCustomConfig(key: String) : String?
    }
}
