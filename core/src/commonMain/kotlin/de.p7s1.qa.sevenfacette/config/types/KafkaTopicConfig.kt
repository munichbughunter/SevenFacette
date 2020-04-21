package de.p7s1.qa.sevenfacette.config.types

import kotlinx.serialization.Serializable

@Serializable
data class KafkaTopicConfig(
        var name: String,
        var useSASLAuthentication: Boolean = false,
        var saslUsername: String? = null,
        var saslPassword: String? = null
)
