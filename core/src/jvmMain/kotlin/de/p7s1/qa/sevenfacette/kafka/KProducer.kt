package de.p7s1.qa.sevenfacette.kafka

import de.p7s1.qa.sevenfacette.kafka.config.KTopicConfiguration
import de.p7s1.qa.sevenfacette.kafka.config.SaslConfiguration
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.Producer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer

class KProducer (private val topicConfig: KTopicConfiguration) {
    private lateinit var producer : Producer<String, String>

    fun createProducer() : Producer<String, String> {
        var config : MutableMap<String, Any> = mutableMapOf()
        config[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = topicConfig.kafkaConfig.bootstrapServer
        config[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        config[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        if (topicConfig.kafkaConfig.saslConfig) {
            config = SaslConfiguration.addSaslProperties(config, topicConfig)
        }
        producer = KafkaProducer<String, String>(config)
        return producer
    }

    fun send(msg: String) {
        producer.send(ProducerRecord(topicConfig.kafkaTopic, msg))
        flush()
    }

    fun flush() = producer.flush()

    fun getTopic() : String {
        return topicConfig.kafkaTopic
    }
}
