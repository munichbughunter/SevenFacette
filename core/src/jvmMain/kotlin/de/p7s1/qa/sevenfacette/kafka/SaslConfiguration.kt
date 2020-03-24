package de.p7s1.qa.sevenfacette.kafka

import org.apache.kafka.common.config.SaslConfigs
import java.util.Properties

class SaslConfiguration {
    companion object {
        @JvmStatic
        fun addSaslProperties(properties: MutableMap<String, Any>, protocol: SaslSecurityProtocol): MutableMap<String, Any> {
            properties["security.protocol"] = protocol.protocol
            // ToDo: That should come from the config
            properties[SaslConfigs.SASL_MECHANISM] = System.getenv("SASL_MECHANISM")
            properties[SaslConfigs.SASL_JAAS_CONFIG] = JaasConfig.create()
            return properties
        }
    }
}
