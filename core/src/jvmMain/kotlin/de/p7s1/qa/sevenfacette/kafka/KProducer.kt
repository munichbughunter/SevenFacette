package de.p7s1.qa.sevenfacette.kafka

import de.p7s1.qa.sevenfacette.kafka.SaslSecurityProtocol.SSL
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.Producer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import java.util.Properties

class KProducer (private val topic: String) {
    private val producer = createProducer()

    private fun createProducer() : Producer<String, String> {
        var config = Properties()
        // ToDo: That should come from the config
        config[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = System.getenv("BOOT_STRAP_SERVER")
        config[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        config[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        // ToDo: THE SSL should also come from the config
        config = SaslConfiguration.addSaslProperties(config, SSL)
        return KafkaProducer(config)
    }

    fun send(msg: String) {
        producer.send(ProducerRecord(topic, msg))
    }

    fun flush() = producer.flush()

    fun getTopic() : String {
        return topic
    }
}
