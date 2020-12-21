package de.p7s1.qa.sevenfacette.kafka

import de.p7s1.qa.sevenfacette.config.ConfigReader
import de.p7s1.qa.sevenfacette.config.types.FacetteConfig
import de.p7s1.qa.sevenfacette.config.types.KafkaTopicConfig

/**
 * JS specific implementation of the KFactory to create consumer and producer objects
 *
 * @author Patrick Döring
 */

/**
 * Creates a KConsumer object based on the autoStart flag
 * true -> start consuming and returns the created object
 * false -> returns the created object
 *
 * @param [consumerName] and [autoStart]
 * @return [KConsumer]
 */
@JsName("createKConsumer")
fun createKConsumer(consumerName: String, autoStart: Boolean = true) : KConsumer {
    val config: KafkaTopicConfig = ConfigReader.getKafkaConsumerConfig(consumerName) ?:
    throw Exception("Kafka config for consumer $consumerName not found")
    if(config.bootstrapServer.isEmpty()) config.bootstrapServer = FacetteConfig.kafka?.bootstrapServer ?: ""

    return createKConsumer(config, autoStart)
}

fun createKConsumer(config: KafkaTopicConfig, autoStart: Boolean) : KConsumer {
    return when (autoStart) {
        true -> KConsumer(config).apply {
            createKConsumer()
        }
        false -> KConsumer(config).apply {
            createKConsumer()
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
@JsName("createKProducer")
fun createKProducer(producerName: String, autoSend: Boolean) : KProducer {
    val config = ConfigReader.getKafkaProducerConfig(producerName) ?:
    throw Exception("Kafka config for consumer $producerName not found")
    if(config.bootstrapServer.isEmpty()) config.bootstrapServer = FacetteConfig.kafka?.bootstrapServer ?: ""

    return createKProducer(config, autoSend)
}

fun createKProducer(config: KafkaTopicConfig, autoSend: Boolean) : KProducer {
    return KProducer(config).apply {
        createKProducer()
    }
}
