package de.p7s1.qa.sevenfacette.kafka

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import java.util.Properties

// ToDo: Think about again....? Make this sense?
class KProducer constructor(private var topic: String, private var config: Properties) {
    private val kProducer: KafkaProducer<String, String> = KafkaProducer(config)

    fun send(msg: String) {
        // ToDo: Add logging
        kProducer.send(ProducerRecord(this.topic, msg))
    }

    fun flush() = kProducer.flush()

    fun getTopic(): String = this.topic
}
