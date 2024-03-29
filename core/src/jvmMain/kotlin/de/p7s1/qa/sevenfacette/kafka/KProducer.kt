package de.p7s1.qa.sevenfacette.kafka

import de.p7s1.qa.sevenfacette.config.types.KafkaTopicConfig
import de.p7s1.qa.sevenfacette.kafka.config.SaslConfig
import de.p7s1.qa.sevenfacette.utils.Logger
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.header.internals.RecordHeader
import org.apache.kafka.common.serialization.StringSerializer


/**
 * JVM specific implementation of the Kafka producer
 *
 * @constructor the constructor receives the [tableTopicConfig] and [autoSend] parameter
 *
 * @author Patrick Döring
 */
class KProducer (
    private val topicConfig: KafkaTopicConfig,
    private var autoSend: Boolean
) {
    private var logger: Logger = Logger()
    private lateinit var producer : KafkaProducer<String, String>

    /**
     * Create a KafkaProducer
     * @return [producer]
     */
    fun createProducer() : KafkaProducer<String, String> {
        var config : MutableMap<String, Any> = mutableMapOf()
        config[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = topicConfig.bootstrapServer
        config[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        config[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java

        if (topicConfig.useSASLAuthentication) {
            config = SaslConfig.addSaslProperties(config, topicConfig)
        }
        producer = KafkaProducer<String, String>(config)
        logger.info("Create KProducer")
        return producer
    }

    /**
     * Sends a Kafka message to a configured table topic
     * @param [msg]
     */
    fun send(msg: String) {
        producer.send(ProducerRecord(topicConfig.topicName, msg))
        if (autoSend) {
            flush()
        }
        logger.info("Message send to topic: $msg")
    }

    /**
     * Sends a Kafka message with Header(s) to a configured table topic
     * @param [msg]
     */
    fun sendMessageWithHeader(key: String, msg: String, headers: List<RecordHeader>) {
        val record = ProducerRecord(topicConfig.topicName, key, msg)

        headers.forEach { header ->
            record.headers().add(header)
        }

        producer.send(record)

        if (autoSend) {
            flush()
        }

        logger.info("Message send to topic: $msg")
    }

    /**
     * Sends a Kafka message with key to a configured table topic
     * @param [key]
     * @param [msg]
     */
    fun sendKeyMessage(key: String, msg: String) {
        producer.send(ProducerRecord(topicConfig.topicName, key, msg))
        if (autoSend) {
            flush()
        }
        logger.info("Message send to topic: $msg")
    }

    /**
     * Flush records currently buffered in the producer
     */
    fun flush() = producer.flush()
}
