package de.p7s1.qa.sevenfacette.kafka.config

import de.p7s1.qa.sevenfacette.config.types.KafkaTopicConfig
import de.p7s1.qa.sevenfacette.kafka.config.JaasConfig
import org.apache.kafka.common.config.SaslConfigs

/**
 * Adds SASL configuration properties
 *
 * @author Patrick Döring
 */
class SaslConfig {
    companion object {
        @JvmStatic
        fun addSaslProperties(properties: MutableMap<String, Any>, tableTopicConfig: KafkaTopicConfig): MutableMap<String, Any> {
            properties["security.protocol"] = tableTopicConfig.kafkaProtocol
            properties[SaslConfigs.SASL_MECHANISM] = tableTopicConfig.saslMechanism ?: ""
            properties[SaslConfigs.SASL_JAAS_CONFIG] = JaasConfig.create(tableTopicConfig)
            return properties
        }
    }
}
