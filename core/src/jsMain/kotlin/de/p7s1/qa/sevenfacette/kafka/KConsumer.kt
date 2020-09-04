package de.p7s1.qa.sevenfacette.kafka

import de.p7s1.qa.sevenfacette.kafka.config.KTableTopicConfig

/**
 * JS specific implementation of the Kafka consumer
 *
 * @constructor the constructor receives the [tableTopicConfig]
 *
 * @author Patrick Döring
 */
class KConsumer (private val tableTopicConfig: KTableTopicConfig) {
    private var consumer: dynamic = ""

    /**
     * Create a KafkaConsumer
     * @return [consumer]
     */
    fun createConsumer(): dynamic {
        val kafkaOptions: KafkaConfig = js("({})")
        kafkaOptions.brokers = arrayOf(tableTopicConfig.kafkaConfig.bootstrapServer)
        kafkaOptions.clientId = "7Facette_" + (0..36).shuffled().first().toString()
        val consumerOptions: ConsumerConfig = js("({})")
        consumerOptions.groupId = "7Facette_Consumer_" + (0..36).shuffled().first().toString()

        consumer = Kafka(kafkaOptions).consumer(consumerOptions)

        // logger.info("Create Producer")
        return consumer
    }
}
