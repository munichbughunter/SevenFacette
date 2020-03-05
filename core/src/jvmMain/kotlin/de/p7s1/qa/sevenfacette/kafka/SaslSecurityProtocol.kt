package de.p7s1.qa.sevenfacette.kafka

enum class SaslSecurityProtocol(val protocol: String) {
    SSL ("SASL_SSL"),
    PLAINTEXT ("SASL_PLAINTEXT")
}
