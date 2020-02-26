package de.p7s1.qa.sevenfacette.kafka

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import java.util.Properties


class KProducer (private val topic: String) {
    private val kProducer: KafkaProducer<String, String>

    var username = "f79z35t6"
    var password = "TVJN359WjKNeL32KDGGc0c9MT77ng0FF"
    var jaasTemplate = "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"%s\" password=\"%s\";"
    var jaasCfg = String.format(jaasTemplate, username, password)
    init {
        val config = Properties()

        config[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        config[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java

        config[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = "moped-01.srvs.cloudkafka.com:9094"
        config[ConsumerConfig.GROUP_ID_CONFIG] = "$username-consumer"
        config["security.protocol"] = "SASL_SSL"
        config["sasl.mechanism"] = "SCRAM-SHA-256"
        config["sasl.jaas.config"] = jaasCfg
        kProducer = KafkaProducer(config)
    }

    fun send(msg: String) {
        kProducer.send(ProducerRecord(topic, msg))
    }

    fun flush() = kProducer.flush()
}
