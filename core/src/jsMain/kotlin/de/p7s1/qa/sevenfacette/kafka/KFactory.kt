package de.p7s1.qa.sevenfacette.kafka

import de.p7s1.qa.sevenfacette.config.types.KafkaTopicConfig

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
    @JsName("createKConsumer")
    fun createKConsumer(consumerName: String, tableTopicConfig: KafkaTopicConfig) : dynamic {
        return createKConsumer(tableTopicConfig)
    }

    fun createKConsumer(tableTopicConfig: KafkaTopicConfig) : dynamic {
        return KConsumer(tableTopicConfig).createConsumer()
    }

    /**
     * Creates a KProducer object based on the tableTopicConfig
     *
     * @param [tableTopicConfig]
     * @return [asDynamic]
     */
    @JsName("createKProducer")
    fun createKProducer(producerName: String, tableTopicConfig: KafkaTopicConfig) : dynamic {
        return createKProducer(tableTopicConfig)
    }

    fun createKProducer(tableTopicConfig: KafkaTopicConfig) : dynamic {
        return KProducer(tableTopicConfig).createProducer()
    }
}
