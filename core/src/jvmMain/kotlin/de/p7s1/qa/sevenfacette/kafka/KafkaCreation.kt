package de.p7s1.qa.sevenfacette.kafka

import de.p7s1.qa.sevenfacette.config.ConfigReader
import de.p7s1.qa.sevenfacette.config.types.FacetteConfig
import de.p7s1.qa.sevenfacette.config.types.KafkaTopicConfig


fun createConsumer(consumerName: String, autoStart: Boolean) : KConsumer {
    val config: KafkaTopicConfig = ConfigReader.getKafkaConsumerConfig(consumerName) ?:
    throw Exception("Kafka config for consumer $consumerName not found")
    if(config.bootstrapServer.isEmpty()) config.bootstrapServer = FacetteConfig.kafka?.bootstrapServer ?: ""

    return KFactory.createConsumer(consumerName, config, autoStart)
}