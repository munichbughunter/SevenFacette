package de.p7s1.qa.sevenfacette.kafka.config

import de.p7s1.qa.sevenfacette.kafka.KConsumer
import de.p7s1.qa.sevenfacette.kafka.KFactory
import de.p7s1.qa.sevenfacette.kafka.KProducer

class KTableTopicConfig (var kafkaConfig: KConfig) {
    var kafkaTopic: String = ""

    fun createKConsumer(autoStart: Boolean) : KConsumer {
        return KFactory.createKConsumer(this@KTableTopicConfig, autoStart)
    }

    fun createKProducer(autoSend: Boolean) : KProducer {
        return KFactory.createKProducer(this@KTableTopicConfig, autoSend)
    }
}
