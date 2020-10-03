package de.p7s1.qa.sevenfacette.config.types

import kotlinx.serialization.Serializable

@Serializable
data class KafkaConfig (
        var bootstrapServer: String,
        var consumer: Map<String, KafkaTopicConfig>,
        var producer: Map<String, KafkaTopicConfig>
) {
    fun getKafkaConsumer(consumerName: String): KafkaTopicConfig? = consumer[consumerName]
    fun getKafkaProducer(producerName: String): KafkaTopicConfig? = producer[producerName]
}
