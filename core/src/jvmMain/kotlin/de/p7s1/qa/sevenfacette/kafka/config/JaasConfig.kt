package de.p7s1.qa.sevenfacette.kafka.config

/**
 * Adds JAAS configuration property
 *
 * @author Patrick Döring
 */
class JaasConfig {
    companion object {
        @JvmStatic
        fun create(tableTopicConfig: KTableTopicConfig): String {
            return String.format(
                    "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"%s\" password=\"%s\";",
                    tableTopicConfig.kafkaConfig.kafkaUser,
                    tableTopicConfig.kafkaConfig.kafkaPW
            )
        }
    }
}