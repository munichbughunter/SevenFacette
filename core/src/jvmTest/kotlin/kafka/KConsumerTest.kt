package kafka

import de.p7s1.qa.sevenfacette.config.types.IsolationLevel
import de.p7s1.qa.sevenfacette.config.types.KafkaTopicConfig
import de.p7s1.qa.sevenfacette.kafka.KConsumer
import de.p7s1.qa.sevenfacette.kafka.KProducer
import de.p7s1.qa.sevenfacette.kafka.KRecord
import kotlinx.coroutines.ObsoleteCoroutinesApi
import org.junit.Before
import org.junit.Test
import org.testcontainers.containers.KafkaContainer
import org.testcontainers.utility.DockerImageName
import java.time.Duration
import java.util.concurrent.ConcurrentLinkedQueue
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Testingclass for [KConsumer].
 *
 * Testcases:
 * - filter messages by value
 * - filter messages by key
 * - wait for KRecords count
 * - filter messages by value and count
 * - filter messages by key and count test
 * - get KRecords
 * - get KRecords count
 *
 * @author Stella Bastug, Margaretha Obermayr
 */
class KConsumerTest {

    private var kafka: KafkaContainer? = null
    private var kafkaTopicConfig: KafkaTopicConfig? = null
    private var producer: KProducer? = null
    private var consumer: KConsumer? = null
    private var filteredList: List<KRecord>? = null
    private var concurrentLinkedQueue: ConcurrentLinkedQueue<KRecord>? = null
    private var count = 2

    @ObsoleteCoroutinesApi
    @Before
    fun startKafkaContainer() {
        kafka = KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:5.4.3"))
        kafka!!.start()

        kafkaTopicConfig = KafkaTopicConfig(
            false,
            "",
            "",
            "",
            "latest",
            10,
            "",
            kafka!!.bootstrapServers,
            "",
            IsolationLevel.READ_UNCOMMITTED,
            "",
            false,
            0,
            "myProducer"
        )
        createProducer()
        createConsumer()
        sendKeyMessages()
    }

    @Test
    fun filterByValueTest() {
        filteredList = consumer?.filterByValue("Hello", Duration.ofSeconds(10))!!
        assertTrue(!filteredList.isNullOrEmpty())
    }

    @Test
    fun filterByKeyTest() {
        filteredList = consumer?.filterByKey("Key", Duration.ofSeconds(10))!!
        assertTrue(!filteredList.isNullOrEmpty())
    }

    @Test
    fun waitForKRecordsCountTest() {
        concurrentLinkedQueue = consumer?.waitForKRecordsCount(count, Duration.ofSeconds(10))!!
        assertEquals(concurrentLinkedQueue!!.size, count)
    }

    @Test
    fun filterByValueAndCountTest() {
        filteredList = consumer?.filterByValueAndCount("Hello", count, Duration.ofSeconds(10))!!
        assertEquals(filteredList?.size, count)
    }

    @Test
    fun filterByKeyAndCountTest() {
        filteredList = consumer?.filterByKeyAndCount("Key", count, Duration.ofSeconds(10))!!
        assertEquals(filteredList?.size, count)
    }

    @Test
    fun getKRecordsTest() {
        concurrentLinkedQueue = consumer?.getKRecords()
        assertTrue(!consumer?.getKRecords().isNullOrEmpty())
    }

    @Test
    fun getKRecordsCountTest() {
        val size = consumer?.getKRecordsCount()
        assertEquals(size, count)
    }

    @Test
    fun getLastKRecordTest() {
        producer?.sendKeyMessage("{Key, bar}", "{greeting: Ciao}")
        producer?.flush()

        Thread.sleep(500)

        val record = consumer?.getLastKRecord()
        record?.key?.let { assertTrue(it.contentEquals("{Key, bar}")) }
        record?.value?.let { assertTrue(it.contentEquals("{greeting: Ciao}")) }
    }

    private fun sendKeyMessages() {
        producer?.sendKeyMessage("{Key, foo}", "{greeting: Hello}")
        producer?.sendKeyMessage("{Key, foo}", "{greeting: Hello}")
    }

    private fun createProducer() {
        producer = kafkaTopicConfig?.let { KProducer(it, true) }
        producer?.createProducer()
    }

    @ObsoleteCoroutinesApi
    fun createConsumer() {
        consumer = kafkaTopicConfig?.let { KConsumer(it) }
        consumer?.createConsumer()
        consumer?.consume()

        Thread.sleep(2000)
    }
}