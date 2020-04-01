package de.p7s1.qa.sevenfacette.kafka.config

import org.apache.kafka.common.config.SaslConfigs

class SaslConfiguration {
    companion object {
        @JvmStatic
        fun addSaslProperties(properties: MutableMap<String, Any>, tableTopicConfig: KTableTopicConfig): MutableMap<String, Any> {
            properties["security.protocol"] = tableTopicConfig.kafkaConfig.kafkaProtocol
            properties[SaslConfigs.SASL_MECHANISM] = tableTopicConfig.kafkaConfig.saslMechanism
            properties[SaslConfigs.SASL_JAAS_CONFIG] = JaasConfig.create(tableTopicConfig)
            return properties
        }
    }
}
