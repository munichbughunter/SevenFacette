package kafka.config

import de.p7s1.qa.sevenfacette.config.types.IsolationLevel.READ_UNCOMMITTED
import de.p7s1.qa.sevenfacette.config.types.KafkaTopicConfig
import de.p7s1.qa.sevenfacette.kafka.config.SaslConfig
import de.p7s1.qa.sevenfacette.kafka.config.SaslConfig.Companion
import org.junit.Test
/**
 * TODO: Add Description
 *
 * @author Patrick Döring
 */
class SaslConfigTest {

    @Test
    fun saslPropertiesTest() {
        //properties: MutableMap<String, Any>, tableTopicConfig: KafkaTopicConfig
        val properties : MutableMap<String, Any> = mutableMapOf()
        val tableTopicConfig = KafkaTopicConfig(true,
                "saslMechanism",
                "test",
                "test",
                "latest",
                0,
                "",
                "",
                "",
                READ_UNCOMMITTED,
                "",
                false,
                0,
                "testTopic")


        var saslConf = SaslConfig.addSaslProperties(properties, tableTopicConfig)

    }
}
