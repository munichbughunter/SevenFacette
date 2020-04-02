package de.p7s1.qa.sevenfacette.kafka

import de.p7s1.qa.sevenfacette.kafka.config.KTableTopicConfig

/**
 * JVM specific implementation of the KFactory to create consumer and producer objects
 *
 * @author Patrick Döring
 */
class KFactory {
    companion object {
        /**
         * Creates a KConsumer object based on the autoStart flag
         * true -> start consuming and returns the created object
         * false -> returns the created object
         *
         * @param [tableTopicConfig] and [autoStart]
         * @return [KConsumer]
         */
        @JvmStatic
        fun createKConsumer(tableTopicConfig: KTableTopicConfig, autoStart: Boolean) : KConsumer {
            return when (autoStart) {
                true -> KConsumer(tableTopicConfig).apply {
                    createConsumer()
                    consume()
                }
                false -> KConsumer(tableTopicConfig).apply {
                    createConsumer()
                }
            }
        }

        /**
         * Creates a KProducer object based on the autoSend flag
         * true -> set autoSend to true and returns the created object
         * false -> returns the created object with autoSend false
         *
         * @param [tableTopicConfig] and [autoSend]
         * @return [KProducer]
         */
        @JvmStatic
        fun createKProducer(tableTopicConfig: KTableTopicConfig, autoSend: Boolean) : KProducer {
            return KProducer(tableTopicConfig, autoSend)
        }
    }
}
