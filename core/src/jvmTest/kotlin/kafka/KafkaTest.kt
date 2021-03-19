package kafka

import de.p7s1.qa.sevenfacette.config.types.KafkaTopicConfig
import de.p7s1.qa.sevenfacette.kafka.KFactory

class KafkaTest {

    fun getConsumerWithConfigFromYaml() {
        val consumer = KFactory.createKConsumer("testtopic1", true)
        consumer.consume()
    }

    fun getConsumerWithOwnConfig() {
        val config = KafkaTopicConfig(
            useSASLAuthentication=false,
            saslMechanism="",
            saslUsername="",
            saslPassword="",
            autoOffset="latest",
            maxConsumingTime=10,
            kafkaProtocol="",
            bootstrapServer="kafka:1234"
        )
        val consumer = KFactory.createKConsumer(config, true)
        consumer.consume()
    }

    fun getProducerWithConfigFromYaml() {
        val producer = KFactory.createKProducer("testProducer1", false)
        producer.send("Hello world")
        producer.flush()
    }

    fun getProducerWithOwnConfig() {
        val config = KafkaTopicConfig(
            useSASLAuthentication=false,
            saslMechanism="",
            saslUsername="",
            saslPassword="",
            autoOffset="latest",
            maxConsumingTime=10,
            kafkaProtocol="",
            bootstrapServer="kafka:1234"
        )
        val producer = KFactory.createKProducer(config,false)
        producer.send("Hello world")
        producer.flush()
    }
}
