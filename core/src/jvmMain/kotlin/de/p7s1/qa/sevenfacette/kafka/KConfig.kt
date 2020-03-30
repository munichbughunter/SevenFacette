package de.p7s1.qa.sevenfacette.kafka

class KConfig {
    // Think about companion object
    var kafkaTopic: String = ""

    var bootstrapServer: String = ""

    var autoOffset: String = ""

    var saslMechanism: String = ""

    var kafkaUser: String = ""

    var kafkaPW: String = ""

    var maxConsumingTime: Long = 0

    var saslConfig: Boolean = true

    var kafkaProtocol: String = ""

    fun setUp() {
        kafkaUser = System.getenv("KAFKA_SASL_USERNAME")
        kafkaPW = System.getenv("KAFKA_SASL_PASSWORD")
        kafkaProtocol = System.getenv("KAFKA_PROTOCOL")
        saslConfig = System.getenv("USE_SASL_CONFIG")!!.toBoolean()
    }
}
