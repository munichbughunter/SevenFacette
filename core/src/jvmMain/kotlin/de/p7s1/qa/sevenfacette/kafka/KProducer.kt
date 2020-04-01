package de.p7s1.qa.sevenfacette.kafka

import de.p7s1.qa.sevenfacette.kafka.config.KTableTopicConfig
import de.p7s1.qa.sevenfacette.kafka.config.SaslConfiguration
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.Producer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer

class KProducer (private val tableTopicConfig: KTableTopicConfig,
                 private var autoSend: Boolean
) {
    private lateinit var producer : Producer<String, String>

    fun createProducer() : Producer<String, String> {
        var config : MutableMap<String, Any> = mutableMapOf()
        config[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = tableTopicConfig.kafkaConfig.bootstrapServer
        config[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        config[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        if (tableTopicConfig.kafkaConfig.saslConfig) {
            config = SaslConfiguration.addSaslProperties(config, tableTopicConfig)
        }
        producer = KafkaProducer<String, String>(config)
        return producer
    }

    fun send(msg: String) {
        producer.send(ProducerRecord(tableTopicConfig.kafkaTopic, msg))
        if (autoSend) {
            flush()
        }
    }

    fun flush() = producer.flush()

    fun getTopic() : String {
        return tableTopicConfig.kafkaTopic
    }
}
