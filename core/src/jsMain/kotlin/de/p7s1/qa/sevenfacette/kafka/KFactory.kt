package de.p7s1.qa.sevenfacette.kafka

import de.p7s1.qa.sevenfacette.kafka.config.KTableTopicConfig

/**
 * TODO: Add Description
 *
 * @author Patrick Döring
 */
class KFactory {

    fun createKConsumer(tableTopicConfig: KTableTopicConfig,
                        autoStart: Boolean): dynamic {
        val options: KafkaConfig = js("({})")
        val consumerOptions: ConsumerConfig = js("({})")
        consumerOptions.groupId = "test-group"
        return Kafka(options).consumer(consumerOptions)
    }

    fun createKProducer(tableTopicConfig: KTableTopicConfig): dynamic {
        return KProducer(tableTopicConfig).createProducer()
    }
}
