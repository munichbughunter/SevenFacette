package de.p7s1.qa.sevenfacette.kafka.config

class KConfig {

    var bootstrapServer: String = ""

    var autoOffset: String = ""

    var saslMechanism: String = ""

    var kafkaUser: String = ""

    var kafkaPW: String = ""

    var maxConsumingTime: Long = 0

    var saslConfig: Boolean = true

    var kafkaProtocol: String = ""
}
