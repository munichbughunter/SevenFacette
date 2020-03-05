package de.p7s1.qa.sevenfacette.kafka

class JaasConfig {
    companion object {
        @JvmStatic
        fun create(): String {
            return String.format(
                    "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"%s\" password=\"%s\";",
                    System.getenv("KAFKA_SASL_USERNAME"),
                    System.getenv("KAFKA_SASL_PASSWORD")
            )
        }
    }
}
