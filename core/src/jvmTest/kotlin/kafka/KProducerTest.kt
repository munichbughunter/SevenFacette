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
import java.util.concurrent.ConcurrentLinkedQueue

/**
 * Testingclass for [KProducer].
 *
 * Testcases:
 * - send simple message
 * - send key message
 * - send message after flush
 *
 * @author Stella Bastug, Margaretha Obermayr
 */
class KProducerTest {

    private var kafka: KafkaContainer? = null
    private var kafkaTopicConfig: KafkaTopicConfig? = null
    private var producer: KProducer? = null
    private var consumer: KConsumer? = null
    private var concurrentLinkedQueue: ConcurrentLinkedQueue<KRecord>? = null

    @Before
    @ObsoleteCoroutinesApi
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
        createConsumer()
    }

    //TODO: Change from junit to kotlin Test?
    @Test
    fun sendTest() {
        createProducerWithAutoSend()
        producer?.send("{greeting: Hello}")
        concurrentLinkedQueue = consumer?.getKRecords()
        assert(!concurrentLinkedQueue.isNullOrEmpty())
    }

    @Test
    fun sendKeyMessageTest() {
        createProducerWithAutoSend()
        producer?.sendKeyMessage("{Key, foo}", "{greeting: Hello}")
        concurrentLinkedQueue = consumer?.getKRecords()
        assert(!concurrentLinkedQueue.isNullOrEmpty())
    }

    @Test
    fun flushTest() {
        createProducerWithoutAutoSend()
        producer?.send("{greeting: Hello}")
        concurrentLinkedQueue = consumer?.getKRecords()
        assert(concurrentLinkedQueue.isNullOrEmpty())
        producer?.flush()
        concurrentLinkedQueue = consumer?.getKRecords()
        assert(!concurrentLinkedQueue.isNullOrEmpty())
    }

    private fun createProducerWithoutAutoSend() {
        producer = kafkaTopicConfig?.let { KProducer(it, false) }
        producer?.createProducer()
    }

    private fun createProducerWithAutoSend() {
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
