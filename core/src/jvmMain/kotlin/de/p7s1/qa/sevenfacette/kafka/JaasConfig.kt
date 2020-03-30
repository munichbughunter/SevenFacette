package de.p7s1.qa.sevenfacette.kafka

class JaasConfig {
    companion object {
        @JvmStatic
        fun create(consumerConfig: KConfig): String {
            return String.format(
                    "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"%s\" password=\"%s\";",
                    consumerConfig.kafkaUser,
                    consumerConfig.kafkaPW
                    //System.getenv("KAFKA_SASL_USERNAME"),
                    //System.getenv("KAFKA_SASL_PASSWORD")
            )
        }
    }
}
