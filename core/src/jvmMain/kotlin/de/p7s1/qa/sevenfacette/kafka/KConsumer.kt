package de.p7s1.qa.sevenfacette.kafka

import de.p7s1.qa.sevenfacette.config.types.KafkaTopicConfig
import de.p7s1.qa.sevenfacette.kafka.config.SaslConfig
import de.p7s1.qa.sevenfacette.utils.Logger
import kotlinx.coroutines.*
import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import org.awaitility.Awaitility.with
import java.time.Duration
import java.util.UUID
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.TimeUnit.MILLISECONDS
import java.util.concurrent.TimeUnit.SECONDS
import kotlin.coroutines.CoroutineContext


/**
 * JVM specific implementation of the Kafka consumer
 *
 * @constructor the constructor receives the [topicConfig]
 *
 * @author Patrick Döring
 */
actual class KConsumer actual constructor(
    private val topicConfig: KafkaTopicConfig
) : CoroutineScope by CoroutineScope(Dispatchers.Default) {
    private var logger: Logger = Logger()
    private val job = Job()
    private val kRecordQueue = ConcurrentLinkedQueue<KRecord>()
    private var keepGoing = true
    private lateinit var consumer: KafkaConsumer<String, String>

    /**
     * Create a KafkaConsumer
     * @return [consumer]
     */
    fun createConsumer() : Consumer<String, String> {
        var config : MutableMap<String, Any> = mutableMapOf()
        config[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = topicConfig.bootstrapServer
        config[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        config[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        config[ConsumerConfig.AUTO_OFFSET_RESET_CONFIG] = topicConfig.autoOffset

        if (topicConfig.autoCommit) {
            config[ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG] = topicConfig.autoCommit
            config[ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG] = topicConfig.autoCommitInterval
        }

        if (topicConfig.groupID.isBlank()) {
            config[ConsumerConfig.GROUP_ID_CONFIG] = UUID.randomUUID().toString()
        } else {
            config[ConsumerConfig.GROUP_ID_CONFIG] = topicConfig.groupID
        }

        if(!topicConfig.readIsolationLevel.isolationLevel.isBlank()) {
            config[ConsumerConfig.ISOLATION_LEVEL_CONFIG] = topicConfig.readIsolationLevel.isolationLevel
        }

        if (topicConfig.useSASLAuthentication) {
            config = SaslConfig.addSaslProperties(config, topicConfig)
        }
        consumer = KafkaConsumer<String, String>(config)
        return consumer
    }

    override val coroutineContext: CoroutineContext
        get() = job

    /**
     * Stop consuming and close
     */
    private fun shutdown() {
        job.complete()
        try {
            consumer.close(Duration.ofMillis(5000L))
        } catch (ex: ConcurrentModificationException) {
            logger.warn("Consumer was closed")
        }
    }

    fun filterByValue(pattern: String, pollingTime: Duration): List<KRecord> {
        var filteredList: List<KRecord> = listOf()

        with().pollInterval(500, MILLISECONDS).await().atMost(pollingTime.seconds, SECONDS).until {
            filteredList = getKRecords().filter { (_, value) -> value!!.contains(pattern) }

            if (filteredList.size > 0) {
                return@until true
            }
            false
        }
        return filteredList
    }

    fun filterByKey(pattern: String, pollingTime: Duration): List<KRecord> {
        var filteredList: List<KRecord> = listOf()

        with().pollInterval(500, MILLISECONDS).await().atMost(pollingTime.seconds, SECONDS).until {
            filteredList = getKRecords().filter { (key, _) -> key!!.contains(pattern) }

            if (filteredList.size > 0) {
                return@until true
            }
            false
        }
        return filteredList
    }

    fun waitForKRecordsCount(count: Int, pollingTime: Duration): ConcurrentLinkedQueue<KRecord> {
        with().pollInterval(1, SECONDS).await().atMost(pollingTime.seconds, SECONDS).until { getKRecords().size == count}
        return getKRecords()
    }

    /**
     * Wait for consumed KRecords within a given timespan
     *
     * @param [waitingTime]
     * @return [hasMessage]
     */
    fun waitForKRecords(waitingTime: Int): Boolean {
        var waited : Int = 0
        var hasMessage: Boolean

        do {
            Thread.sleep(500)
            waited += 500
            hasMessage = hasKRecords()
        } while (!hasMessage && waited <= waitingTime)
        stopConsumer()
        return hasMessage
    }

    /**
     * Subscribe consumer on table topic, start consuming and add KRecords to kRecordQueue
     */
    @ObsoleteCoroutinesApi
    fun consume() = launch(newSingleThreadContext(topicConfig.topicName)){
        consumer.subscribe(listOf(topicConfig.topicName))
        logger.info("Start consuming and processing records")
        while (keepGoing) {
            consumer.poll(Duration.ofSeconds(topicConfig.maxConsumingTime)).forEach {
                kRecordQueue.add(KRecord(it.key(), it.value(), it.offset(), it.partition()))
            }
        }
        logger.info("Shut down consumer: ${topicConfig.topicName}")
        shutdown()
    }

    /**
     * Returns the consumed KRecords
     *
     * @return [kRecordQueue]
     */
    fun getKRecords(): ConcurrentLinkedQueue<KRecord> {
        return kRecordQueue
    }

    /**
     * Returns the consumed record count
     *
     * @return kRecordQueue.size
     */
    actual fun getKRecordsCount() : Int {
        return kRecordQueue.size
    }

    /**
     * Returns the last consumed record
     *
     * @return kRecordQueue.elementAt(kRecordQueue.size -1)
     */
    fun getLastKRecord(): KRecord? {
        return kRecordQueue.elementAt(kRecordQueue.size -1)
    }

    /**
     * Returns true if messages are consumed
     *
     * @return !kRecordQueue.isEmpty()
     */
    private fun hasKRecords(): Boolean {
        return !kRecordQueue.isEmpty()
    }

    /**
     * Sets the keepGoing flag to false and stops consumer
     */
    private fun stopConsumer() {
        keepGoing = false
        shutdown()
    }
}
