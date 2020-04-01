package de.p7s1.qa.sevenfacette.kafka

import de.p7s1.qa.sevenfacette.kafka.config.KTableTopicConfig

class KFactory {
    companion object {
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

        @JvmStatic
        fun createKProducer(tableTopicConfig: KTableTopicConfig, autoSend: Boolean) : KProducer {
            return KProducer(tableTopicConfig, autoSend)
        }
    }
}
