package de.p7s1.qa.sevenfacette.kafka

import de.p7s1.qa.sevenfacette.config.types.KafkaTopicConfig
import de.p7s1.qa.sevenfacette.kafka.externals.Kafka
import de.p7s1.qa.sevenfacette.kafka.externals.KafkaConfig
import de.p7s1.qa.sevenfacette.kafka.externals.Message
import de.p7s1.qa.sevenfacette.kafka.externals.ProducerEvents
import de.p7s1.qa.sevenfacette.kafka.externals.ProducerRecord
import de.p7s1.qa.sevenfacette.kafka.externals.Sender
import kotlin.coroutines.Continuation
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.startCoroutine
import kotlin.coroutines.suspendCoroutine

/**
 * JS specific implementation of the Kafka producer
 *
 * @constructor the constructor receives the [topicConfig] parameter
 *
 * @author Patrick Döring
 */
external fun setTimeout(function: () -> Unit, delay: Long)
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
    fun sendKafkaMessage(key: String, msg: String) {
        println(" HERE IS THE PRODUCER CLASS")
        val message: Message = js("({})")
        message.key = key
        message.value = msg

        val record: ProducerRecord = js("({})")
        record.topic = getTopic()
        record.messages = arrayOf(message)

        producer.connect()

        producer.send(record)
        println("RECORD WAS SENT TO TOPIC!!!")
    }
    // ToDo: Validate to send key and message via kotlin...

    @JsName("getTopic")
    fun getTopic(): String {
        return topicConfig.topicName
    }
}
