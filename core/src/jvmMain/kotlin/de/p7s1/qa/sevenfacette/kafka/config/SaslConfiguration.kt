package de.p7s1.qa.sevenfacette.kafka.config

import de.p7s1.qa.sevenfacette.kafka.config.JaasConfig
import de.p7s1.qa.sevenfacette.kafka.config.KTopicConfiguration
import org.apache.kafka.common.config.SaslConfigs

class SaslConfiguration {
    companion object {
        @JvmStatic
        fun addSaslProperties(properties: MutableMap<String, Any>, topicConfiguration: KTopicConfiguration): MutableMap<String, Any> {
            properties["security.protocol"] = topicConfiguration.kafkaConfig.kafkaProtocol
            properties[SaslConfigs.SASL_MECHANISM] = topicConfiguration.kafkaConfig.saslMechanism
            properties[SaslConfigs.SASL_JAAS_CONFIG] = JaasConfig.create(topicConfiguration)
            return properties
        }
    }
}
