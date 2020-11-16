package de.p7s1.qa.sevenfacette.config.types

import kotlinx.serialization.Serializable

@Serializable
data class KafkaTopicConfig(
        var useSASLAuthentication: Boolean = false,
        var saslMechanism: String = "",
        var saslUsername: String = "",
        var saslPassword: String = "",
        var autoOffset: String,
        var maxConsumingTime: Long = 0,
        var kafkaProtocol: String = "",
        var bootstrapServer: String = "",
        var groupID: String = "",
        var isolationLevel: String = "",
        var autoCommit: Boolean = false,
        var autoCommitInterval: Int = 0,
        var topicName: String = ""
)
