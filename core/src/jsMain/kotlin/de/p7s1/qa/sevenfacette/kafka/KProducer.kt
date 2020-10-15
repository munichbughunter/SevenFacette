package de.p7s1.qa.sevenfacette.kafka

import de.p7s1.qa.sevenfacette.config.types.KafkaTopicConfig
import de.p7s1.qa.sevenfacette.kafka.externals.Kafka
import de.p7s1.qa.sevenfacette.kafka.externals.KafkaConfig
import de.p7s1.qa.sevenfacette.kafka.externals.ProducerRecord

/**
 * JS specific implementation of the Kafka producer
 *
 * @constructor the constructor receives the [topicConfig] parameter
 *
 * @author Patrick Döring
 */
class KProducer (
    private val topicConfig: KafkaTopicConfig
) {

    private var producer: dynamic = ""

    /**
     * Create a KafkaProducer
     * @return [producer]
     */
    fun createProducer(): dynamic {
        val kafkaOptions: KafkaConfig = js("({})")
        kafkaOptions.brokers = arrayOf(topicConfig.bootstrapServer)
        kafkaOptions.clientId = "7Facette_Producer_" + (0..36).shuffled().first().toString()
        producer = Kafka(kafkaOptions).producer()
        return producer
    }

    @JsName("sendMessage")
    fun send(msg: ProducerRecord) {
        producer.connect()
        producer.send(msg)
    }

    // ToDo: Validate to send key and message via kotlin...

    @JsName("getTopic")
    fun getTopic(): dynamic {
        return topicConfig.kafkaTopic
    }
}
