package de.p7s1.qa.sevenfacette.kafka.config

import de.p7s1.qa.sevenfacette.config.types.KafkaTopicConfig
import kotlin.text.format

/**
 * Adds JAAS configuration property
 *
 * @author Patrick Döring
 */
class JaasConfig {
    companion object {
        @JvmStatic
        fun create(tableTopicConfig: KafkaTopicConfig): String {
            return String.format(
                    "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"%s\" password=\"%s\";",
                    tableTopicConfig.saslUsername,
                    tableTopicConfig.saslPassword
            )
        }
    }
}
