package de.p7s1.qa.sevenfacette.kafka

import org.apache.kafka.common.config.SaslConfigs
import java.util.Properties

class SaslConfiguration {
    companion object {
        @JvmStatic
        fun addSaslProperties(properties: MutableMap<String, Any>, consumerConfig: KConfig): MutableMap<String, Any> {
            properties["security.protocol"] = consumerConfig.kafkaProtocol
            properties[SaslConfigs.SASL_MECHANISM] = consumerConfig.saslMechanism
            properties[SaslConfigs.SASL_JAAS_CONFIG] = JaasConfig.create(consumerConfig)
            return properties
        }
    }
}
