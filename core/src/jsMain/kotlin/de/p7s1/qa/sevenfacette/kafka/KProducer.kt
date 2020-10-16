package de.p7s1.qa.sevenfacette.kafka

import de.p7s1.qa.sevenfacette.config.types.KafkaTopicConfig
import de.p7s1.qa.sevenfacette.kafka.externals.Kafka
import de.p7s1.qa.sevenfacette.kafka.externals.KafkaConfig
import de.p7s1.qa.sevenfacette.kafka.externals.ProducerEvents
import de.p7s1.qa.sevenfacette.kafka.externals.ProducerRecord
import de.p7s1.qa.sevenfacette.kafka.externals.Sender

/**
 * JS specific implementation of the Kafka producer
 *
 * @constructor the constructor receives the [topicConfig] parameter
 *
 * @author Patrick Döring
 */
class KProducer (
    // ToDo: Do we need that?
    private val producerName: String,
    private val topicConfig: KafkaTopicConfig
) {
    private var producer: dynamic = ""

    @JsName("createKProducer")
    fun createKProducer() : KProducer {
        createProducer()
        return this
    }

    /**
     * Create a KafkaProducer
     * @return [producer]
     */
    private fun createProducer() {
        val kafkaOptions: KafkaConfig = js("({})")
        kafkaOptions.brokers = arrayOf(topicConfig.bootstrapServer)
        kafkaOptions.clientId = "7Facette_Producer_" + (0..36).shuffled().first().toString()
        producer = Kafka(kafkaOptions).producer()
    }

    @JsName("sendKafkaMessage")
    fun sendKafkaMessage() {
        producer.connect()
        println(" HERE IS THE PRODUCER CLASS")
    }

    // ToDo: Validate to send key and message via kotlin...

    @JsName("getTopic")
    fun getTopic(): String {
        return topicConfig.kafkaTopic
    }
}
