package de.p7s1.qa.sevenfacette.kafka

import de.p7s1.qa.sevenfacette.kafka.config.KTableTopicConfig

/**
 * JS specific implementation of the KFactory to create consumer and producer objects
 *
 * @author Patrick Döring
 */
class KFactory {

    /**
     * Creates a KConsumer object based on the tableTopicConfig
     *
     * @param [tableTopicConfig]
     * @return [asDynamic]
     */
    fun createKConsumer(tableTopicConfig: KTableTopicConfig): dynamic {
        return KConsumer(tableTopicConfig).createConsumer()
    }

    /**
     * Creates a KProducer object based on the tableTopicConfig
     *
     * @param [tableTopicConfig]
     * @return [asDynamic]
     */
    fun createKProducer(tableTopicConfig: KTableTopicConfig): dynamic {
        return KProducer(tableTopicConfig).createProducer()
    }
}
