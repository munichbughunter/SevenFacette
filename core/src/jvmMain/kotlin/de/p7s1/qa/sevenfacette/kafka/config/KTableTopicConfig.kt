package de.p7s1.qa.sevenfacette.kafka.config

import de.p7s1.qa.sevenfacette.kafka.KConsumer
import de.p7s1.qa.sevenfacette.kafka.KFactory
import de.p7s1.qa.sevenfacette.kafka.KProducer

/**
 * Based on the KTableTopicConfig the consumer and producer object can be created
 *
 * @constructor the constructor receives the [kafkaConfig]
 *
 * @author Patrick Döring
 */
class KTableTopicConfig (var kafkaConfig: KConfig) {
    var kafkaTopic: String = ""

    /**
     * Creates a KConsumer object via a factory
     *
     * @param [autoStart]
     *
     * @return [KConsumer]
     */
    fun createKConsumer(autoStart: Boolean) : KConsumer {
        return KFactory.createKConsumer(this@KTableTopicConfig, autoStart)
    }

    /**
     * Creates a KProducer object via a factory
     *
     * @param [autoSend]
     *
     * @return [KProducer]
     */
    fun createKProducer(autoSend: Boolean) : KProducer {
        return KFactory.createKProducer(this@KTableTopicConfig, autoSend)
    }
}
