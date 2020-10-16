package de.p7s1.qa.sevenfacette.kafka

import de.p7s1.qa.sevenfacette.kafka.config.KTableTopicConfig
import de.p7s1.qa.sevenfacette.kafka.externals.Kafka
import de.p7s1.qa.sevenfacette.kafka.externals.KafkaConfig

/**
 * JS specific implementation of the Kafka producer
 *
 * @constructor the constructor receives the [tableTopicConfig] parameter
 *
 * @author Patrick Döring
 */
class KProducer (private val tableTopicConfig: KTableTopicConfig) {

    private var producer: dynamic = ""

    /**
     * Create a KafkaProducer
     * @return [producer]
     */
    fun createProducer(): dynamic {
        val kafkaOptions: KafkaConfig = js("({})")
        kafkaOptions.brokers = arrayOf(tableTopicConfig.kafkaConfig.bootstrapServer)
        kafkaOptions.clientId = "7Facette_Producer_" + (0..36).shuffled().first().toString()
        producer = Kafka(kafkaOptions).producer()
        //logger.info("Create KProducer")
        return producer
    }
}
