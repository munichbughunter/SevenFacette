package de.p7s1.qa.sevenfacette.kafka

import de.p7s1.qa.sevenfacette.kafka.config.KTableTopicConfig

/**
 * TODO: Add Description
 *
 * @author Patrick Döring
 */
class KProducer (private val tableTopicConfig: KTableTopicConfig) {

    private var producer: dynamic = ""

    fun createProducer(): dynamic {
        println("CREATING A NEW IN THE KPRODUCER")
        val options: KafkaConfig = js("({})")
        options.brokers = arrayOf(tableTopicConfig.kafkaConfig.bootstrapServer)
        options.clientId = "7Facette_Producer_" + (0..36).shuffled().first().toString()
        producer = Kafka(options).producer()
        return producer
    }
}
