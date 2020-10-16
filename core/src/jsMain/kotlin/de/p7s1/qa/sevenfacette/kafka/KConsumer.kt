package de.p7s1.qa.sevenfacette.kafka

import de.p7s1.qa.sevenfacette.config.types.KafkaTopicConfig
import de.p7s1.qa.sevenfacette.kafka.externals.ConsumerConfig
import de.p7s1.qa.sevenfacette.kafka.externals.Kafka
import de.p7s1.qa.sevenfacette.kafka.externals.KafkaConfig

/**
 * JS specific implementation of the Kafka consumer
 *
 * @constructor the constructor receives the [topicConfig]
 *
 * @author Patrick Döring
 */
class KConsumer (
    private val topicConfig: KafkaTopicConfig
) {
    private var consumer: dynamic = ""

    /**
     * Create a KafkaConsumer
     * @return [consumer]
     */
    fun createConsumer(): dynamic {
        val kafkaOptions: KafkaConfig = js("({})")
        kafkaOptions.brokers = arrayOf(topicConfig.bootstrapServer)
        // AutoOffset is set at the call with true or false.
        // AutoCommit is done by KafkaJS itself.
        // autoCommitIntervall is set at the run and not as a single property
        // There are no single property for that in JS.
        if (topicConfig.useSASLAuthentication) {
            kafkaOptions.ssl = topicConfig.useSASLAuthentication
            kafkaOptions.sasl?.mechanism = topicConfig.saslMechanism!!
            kafkaOptions.sasl?.username = topicConfig.saslUsername!!
            kafkaOptions.sasl?.password = topicConfig.saslPassword!!
        }

        val consumerOptions: ConsumerConfig = js("({})")
        if (topicConfig.groupID.isBlank()) {
            consumerOptions.groupId = "7Facette_ConsumerGroup_" + (0..36).shuffled().first().toString()
        } else {
            consumerOptions.groupId = topicConfig.groupID
        }

        if (!topicConfig.isolationLevel.isBlank()) {
            // ToDo: Discuss about...
            consumerOptions.readUncommitted = topicConfig.isolationLevel.toBoolean()
        }
        consumerOptions.maxWaitTimeInMs = topicConfig.maxConsumingTime
        consumer = Kafka(kafkaOptions).consumer(consumerOptions)

        return consumer
    }
}
