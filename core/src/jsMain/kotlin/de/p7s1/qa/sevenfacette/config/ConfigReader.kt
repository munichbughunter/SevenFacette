package de.p7s1.qa.sevenfacette.config

import de.p7s1.qa.sevenfacette.config.types.DatabaseConfig
import de.p7s1.qa.sevenfacette.config.types.HttpClientConfig
import de.p7s1.qa.sevenfacette.config.types.KafkaTopicConfig
import de.p7s1.qa.sevenfacette.config.types.SevenFacetteConfig


/**
 * TODO: Add Description
 *
 * @author Patrick Döring
 */
actual class ConfigReader {

    actual companion object {


        actual fun readConfig(): SevenFacetteConfig {
            TODO("Not yet implemented")
        }


        actual fun getHttpConfig(clientName: String): HttpClientConfig? {
            TODO("Not yet implemented")
        }


        actual fun getKafkaConsumerConfig(consumerName: String): KafkaTopicConfig? {
            TODO("Not yet implemented")
        }


        actual fun getKafkaProducerConfig(producerName: String): KafkaTopicConfig? {
            TODO("Not yet implemented")
        }


        actual fun getDatabaseConfig(databaseName: String): DatabaseConfig? {
            TODO("Not yet implemented")
        }

    }
}
