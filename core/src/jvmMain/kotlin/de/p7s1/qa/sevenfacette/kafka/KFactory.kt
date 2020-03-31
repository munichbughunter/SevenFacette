package de.p7s1.qa.sevenfacette.kafka

import de.p7s1.qa.sevenfacette.kafka.config.KTopicConfiguration

class KFactory {
    companion object {
        @JvmStatic
        fun createKConsumer(topicConfig: KTopicConfiguration, autoStart: Boolean) : KConsumer {
            return when (autoStart) {
                true -> KConsumer(topicConfig).apply {
                    createConsumer()
                    consume()
                }
                false -> KConsumer(topicConfig).apply {
                    createConsumer()
                }
            }
        }

        @JvmStatic
        fun createKProducer(topicConfig: KTopicConfiguration, autoStart: Boolean, message: String?) : KProducer {
            return when (autoStart) {
                true -> KProducer(topicConfig).apply {
                    createProducer()
                    if (message != null) {
                        send(message)
                    }
                }
                false -> KProducer(topicConfig).apply {
                    createProducer()
                }
            }
        }
    }
}
