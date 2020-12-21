package de.p7s1.qa.sevenfacette.config.types

import de.p7s1.qa.sevenfacette.config.types.IsolationLevel.READ_UNCOMMITTED
import kotlinx.serialization.Serializable

// ToDo: Add Enum class for IsolationLevel...

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

        var readIsolationLevel: IsolationLevel = READ_UNCOMMITTED,
//        @Deprecated("Please use the Enum instead", ReplaceWith("readIsolationLevel"))
        var isolationLevel: String = "",
        var autoCommit: Boolean = false,
        var autoCommitInterval: Int = 0,
        var topicName: String = ""
)
enum class IsolationLevel(val isolationLevel: String) {
    READ_COMMITTED("read_committed"),
    READ_UNCOMMITTED("read_uncommitted")
}
