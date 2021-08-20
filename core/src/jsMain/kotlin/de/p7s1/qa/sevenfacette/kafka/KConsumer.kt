package de.p7s1.qa.sevenfacette.kafka

import de.p7s1.qa.sevenfacette.config.types.KafkaTopicConfig

/**
 * Kafka Consumer Class which needs to be implemented in js- and jvm-target
 * Documentation for functions is described in the corresponding target implementations.
 *
 * @author Patrick Döring
 */
actual class KConsumer actual constructor(topicConfig: KafkaTopicConfig) {

    actual fun getKRecordsCount(): Int {
        TODO("Not yet implemented")
    }
}
