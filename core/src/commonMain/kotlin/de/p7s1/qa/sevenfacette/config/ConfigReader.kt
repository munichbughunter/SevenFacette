package de.p7s1.qa.sevenfacette.config

import de.p7s1.qa.sevenfacette.config.types.*
import kotlin.jvm.JvmStatic

/**
 * TODO: Add Description
 *
 * @author Patrick Döring
 */
expect class ConfigReader {

    companion object {
        @JvmStatic
        fun readConfig(): SevenFacetteConfig

        @JvmStatic
        fun getHttpConfig(clientName: String): HttpClientConfig?

        @JvmStatic
        fun getKafkaConsumer(consumerName: String): KafkaTopicConfig?

        @JvmStatic
        fun getKafkaProducer(producerName: String): KafkaTopicConfig?

        @JvmStatic
        fun getDatabase(databaseName: String) : DatabaseConfig?
    }
}
