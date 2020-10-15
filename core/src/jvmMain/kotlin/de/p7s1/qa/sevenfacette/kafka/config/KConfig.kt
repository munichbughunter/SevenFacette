package de.p7s1.qa.sevenfacette.kafka.config

/**
 * Holds the basic Kafka configuration parameter
 *
 * @author Patrick Döring
 */
class KConfig {

    var bootstrapServer: String = ""

    var autoOffset: String = ""

    var saslMechanism: String = ""

    var kafkaUser: String = ""

    var kafkaPW: String = ""

    var maxConsumingTime: Long = 0

    var useSASL: Boolean = true

    var kafkaProtocol: String = ""

    var groupID: String = ""

    var readIsolationLevel: IsolationLevel = IsolationLevel.DEFAULT

    @Deprecated("Please use the new Enum instead", ReplaceWith("readIsolationLevel"))
    var isolationLevel: String = ""

    var autoCommit: Boolean = false

    var autoCommitInterval: Int = 0

    enum class IsolationLevel(val isolationLevel: String) {
        DEFAULT(""),
        READ_COMMITTED("read_committed")
    }
}
