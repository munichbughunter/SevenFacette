package de.p7s1.qa.sevenfacette.kafka.config

import de.p7s1.qa.sevenfacette.kafka.config.KConfig.IsolationLevel.READ_UNCOMMITTED as READ_UNCOMMITTED1

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

    var readIsolationLevel: IsolationLevel = IsolationLevel.READ_UNCOMMITTED

    @Deprecated("Please use the new Enum instead", ReplaceWith("readIsolationLevel"))
    var isolationLevel: String = ""

    var autoCommit: Boolean = false

    var autoCommitInterval: Int = 0

    enum class IsolationLevel(val isolationLevel: String) {
        READ_COMMITTED("read_committed"),
        READ_UNCOMMITTED("read_uncommitted")
    }
}
