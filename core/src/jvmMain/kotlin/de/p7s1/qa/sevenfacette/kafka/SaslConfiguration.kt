package de.p7s1.qa.sevenfacette.kafka

import org.apache.kafka.common.config.SaslConfigs
import java.util.*

class SaslConfiguration {
    companion object {
        @JvmStatic
        fun addSaslProperties(properties: Properties, protocol: SaslSecurityProtocol): Properties {
            val password = System.getenv("SASL_TRUSTSTORE_PASSWORD")
            properties.put("ssl.truststore.location", System.getProperty("user.dir") + "/kafka.client.keystore.jks")
            properties.put("ssl.truststore.password", password)
            properties.put("ssl.keystore.location", System.getProperty("user.dir") + "/kafka.client.keystore.jks")
            properties.put("ssl.keystore.password", password)
            properties.put("security.protocol", protocol.protocol)
            properties.put(SaslConfigs.SASL_MECHANISM, "SCRAM-SHA-256")
            properties.put(SaslConfigs.SASL_JAAS_CONFIG, JaasConfig.create())
            return properties
        }
    }
}
