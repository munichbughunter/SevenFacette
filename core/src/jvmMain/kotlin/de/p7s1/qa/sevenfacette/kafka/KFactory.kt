package de.p7s1.qa.sevenfacette.kafka

import de.p7s1.qa.sevenfacette.config.ConfigReader
import de.p7s1.qa.sevenfacette.config.types.FacetteConfig
import de.p7s1.qa.sevenfacette.config.types.KafkaTopicConfig

/**
 * JVM specific implementation of the KFactory to create consumer and producer objects
 *
 * @author Patrick Döring
 */
class KFactory {
    companion object {
        /**
         * Creates a KConsumer object based on the autoStart flag
         * true -> start consuming and returns the created object
         * false -> returns the created object
         *
         * @param [consumerName] and [autoStart]
         * @return [KConsumer]
         */
        @JvmStatic
        fun createConsumer(consumerName: String, autoStart: Boolean) : KConsumer {
            val config: KafkaTopicConfig = ConfigReader.getKafkaConsumerConfig(consumerName) ?:
                throw Exception("Kafka config for consumer $consumerName not found")
            if(config.bootstrapServer.isEmpty()) config.bootstrapServer = FacetteConfig.kafka?.bootstrapServer ?: ""

            return createConsumer(consumerName, config, autoStart)
        }

        @JvmStatic
        fun createConsumer(consumerName: String, config: KafkaTopicConfig, autoStart: Boolean) : KConsumer {
            return when (autoStart) {
                true -> KConsumer(consumerName, config).apply {
                    createConsumer()
                    consume()
                }
                false -> KConsumer(consumerName, config).apply {
                    createConsumer()
                }
            }
        }

        /**
         * Creates a KProducer object based on the autoSend flag
         * true -> set autoSend to true and returns the created object
         * false -> returns the created object with autoSend false
         *
         * @param [tableTopicConfig] and [autoSend]
         * @return [KProducer]
         */
        @JvmStatic
        fun createKProducer(producerName: String, autoSend: Boolean) : KProducer {
            val config = ConfigReader.getKafkaProducerConfig(producerName) ?:
            throw Exception("Kafka config for consumer $producerName not found")
            if(config.bootstrapServer.isEmpty()) config.bootstrapServer = FacetteConfig.kafka?.bootstrapServer ?: ""

            return createKProducer(producerName, config, autoSend)
        }

        @JvmStatic
        fun createKProducer(producerName: String, config: KafkaTopicConfig, autoSend: Boolean) : KProducer {
            return KProducer(producerName, config, autoSend).apply {
                createProducer()
            }
        }
    }
}
