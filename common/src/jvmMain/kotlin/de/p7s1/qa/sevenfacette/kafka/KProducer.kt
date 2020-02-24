package de.p7s1.qa.sevenfacette.kafka

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import java.util.Properties


class KProducer (private val topic: String) {
    private val kProducer: KafkaProducer<String, String>

    init {
        val config = Properties()
        config[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = "kafka.bootstrapServers"
        config[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        config[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        kProducer = KafkaProducer(config)
    }

    fun send(msg: String) {
        kProducer.send(ProducerRecord(topic, msg))
    }

    fun flush() = kProducer.flush()
}
