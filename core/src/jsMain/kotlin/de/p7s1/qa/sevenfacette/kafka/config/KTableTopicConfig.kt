package de.p7s1.qa.sevenfacette.kafka.config

import de.p7s1.qa.sevenfacette.kafka.KFactory

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
     *
     * @return [KConsumer]
     */
    @JsName("createKConsumer")
    fun createKConsumer() : dynamic {
        return KFactory().createKConsumer(this@KTableTopicConfig)
    }

    /**
     * Creates a KProducer object via a factory
     *
     *
     * @return [KProducer]
     */
    @JsName("createKProducer")
    fun createKProducer() : dynamic {
        println("CREATING A NEW PRODUCER")
        return KFactory().createKProducer(this@KTableTopicConfig)
    }
}
