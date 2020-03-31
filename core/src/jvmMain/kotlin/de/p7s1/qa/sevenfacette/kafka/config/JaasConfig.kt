package de.p7s1.qa.sevenfacette.kafka.config

class JaasConfig {
    companion object {
        @JvmStatic
        fun create(topicConfiguration: KTopicConfiguration): String {
            return String.format(
                    "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"%s\" password=\"%s\";",
                    topicConfiguration.kafkaConfig.kafkaUser,
                    topicConfiguration.kafkaConfig.kafkaPW
                    //System.getenv("KAFKA_SASL_USERNAME"),
                    //System.getenv("KAFKA_SASL_PASSWORD")
            )
        }
    }
}
