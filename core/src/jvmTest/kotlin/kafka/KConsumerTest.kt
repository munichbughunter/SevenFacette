package kafka

import de.p7s1.qa.sevenfacette.config.types.IsolationLevel
import de.p7s1.qa.sevenfacette.config.types.KafkaTopicConfig
import de.p7s1.qa.sevenfacette.kafka.KConsumer
import de.p7s1.qa.sevenfacette.kafka.KProducer
import de.p7s1.qa.sevenfacette.kafka.KRecord
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.testcontainers.containers.KafkaContainer
import org.testcontainers.utility.DockerImageName
import java.time.Duration

/**
 * Testingclass for [KConsumer].
 *
 * Testcases:
 *
 * @author Stella Bastug
 */
class KConsumerTest {

    private var kafka: KafkaContainer? = null
    private var kafkaTopicConfig: KafkaTopicConfig? = null
    private var producer: KProducer? = null
    private var consumer: KConsumer? = null
    private var filteredList: List<KRecord>? = null


    @Before
    fun startKafkaContainer() {
        kafka = KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:5.4.3"))
        kafka!!.start()

        kafkaTopicConfig = KafkaTopicConfig(
            false, "", "", "", "latest", 10,
            "", kafka!!.getBootstrapServers(), "", IsolationLevel.READ_UNCOMMITTED, "", false, 0, "myProducer"
        )
        createProducer()
        createConsumer()
    }

    @Test
    fun createConsumerTest() {
    }

    @Test
    fun filterByValueTest() {
    }

    @Test
    fun filterByKeyTest() {
    }

    @Test
    fun waitForKRecordsCountTest() {
    }

    @Test
    fun waitForKRecordsTest() {
    }

    @Test
    fun waitForKRecordsAndCountTestPositive() {
        var count = 2

        producer?.send("{Key}{greeting: Hello}")
        producer?.send("{Key}{greeting: Hello}")
        producer?.flush()

        filteredList = consumer?.filterByValueAndCount("Hello", count, Duration.ofSeconds(10))!!
        assertEquals(filteredList?.size, count)
    }

    @Test
    fun consumeTest() {
    }

    @Test
    fun getKRecordsTest() {
    }

    @Test
    fun getKRecordsCountTest() {
    }

    @Test
    fun getLastKRecordTest() {
    }

    fun createProducer() {
        producer = kafkaTopicConfig?.let { KProducer(it, true) }
        producer?.createProducer()
    }

    fun createConsumer() {
        consumer = kafkaTopicConfig?.let { KConsumer(it) }
        consumer?.createConsumer()
        consumer?.consume()

        Thread.sleep(2000)
    }
}