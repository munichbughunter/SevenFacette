package de.p7s1.qa.sevenfacette.kafka

import de.p7s1.qa.sevenfacette.config.types.KafkaTopicConfig
import de.p7s1.qa.sevenfacette.kafka.externals.*
import de.p7s1.qa.sevenfacette.kafka.externals.CompressionTypes.None
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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
        val message: Message = js("({})")
        message.key = key
        message.value = msg

        val record: ProducerRecord = js("({})")
        record.acks = 0
        record.timeout = 30000
        record.compression = None
        record.topic = getTopic()
        record.messages = arrayOf(message)

        producer.connect()

        GlobalScope.launch(context = Dispatchers.Default) {
            delay(1000)
            producer.send(record)
        }
    }

    @JsName("disconnect")
    fun disconnect() {
        producer.disconnect()
    }

    @JsName("getTopic")
    fun getTopic(): String {
        return topicConfig.topicName
    }
}
