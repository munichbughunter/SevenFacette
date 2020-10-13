package de.p7s1.qa.sevenfacette.kafka

import de.p7s1.qa.sevenfacette.kafka.config.KTableTopicConfig
import de.p7s1.qa.sevenfacette.kafka.config.SaslConfiguration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import mu.KotlinLogging
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
 * @constructor the constructor receives the [tableTopicConfig]
 *
 * @author Patrick Döring
 */
private val logger = KotlinLogging.logger {}
class KConsumer(
        private val tableTopicConfig: KTableTopicConfig
) : CoroutineScope by CoroutineScope(Dispatchers.Default) {
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
        config[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = tableTopicConfig.kafkaConfig.bootstrapServer
        config[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        config[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        config[ConsumerConfig.AUTO_OFFSET_RESET_CONFIG] = tableTopicConfig.kafkaConfig.autoOffset

        if (tableTopicConfig.kafkaConfig.autoCommit) {
            config[ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG] = tableTopicConfig.kafkaConfig.autoCommit
            config[ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG] = tableTopicConfig.kafkaConfig.autoCommitInterval
        }

        if (tableTopicConfig.kafkaConfig.groupID.isBlank()) {
            config[ConsumerConfig.GROUP_ID_CONFIG] = UUID.randomUUID().toString()
        } else {
            config[ConsumerConfig.GROUP_ID_CONFIG] = tableTopicConfig.kafkaConfig.groupID
        }

        if (!tableTopicConfig.kafkaConfig.isolationLevel.isBlank()) {
            config[ConsumerConfig.ISOLATION_LEVEL_CONFIG] = tableTopicConfig.kafkaConfig.isolationLevel
        }

        if (tableTopicConfig.kafkaConfig.useSASL) {
            config = SaslConfiguration.addSaslProperties(config, tableTopicConfig)
        }
        consumer = KafkaConsumer<String, String>(config)
        logger.info("Create KConsumer")
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
        } while (waited <= waitingTime)
        stopConsumer()
        return hasMessage
    }

    /**
     * Subscribe consumer on table topic, start consuming and add KRecords to kRecordQueue
     */
    @ObsoleteCoroutinesApi
    fun consume() = launch(newSingleThreadContext(tableTopicConfig.kafkaTopic)){
        consumer.subscribe(listOf(tableTopicConfig.kafkaTopic))
        logger.info("Start consuming and processing records")
        while (keepGoing) {
            consumer.poll(Duration.ofSeconds(tableTopicConfig.kafkaConfig.maxConsumingTime)).forEach {
                kRecordQueue.add(KRecord(it.key(), it.value(), it.offset(), it.partition()))
            }
        }
        logger.info("Shut down consumer...: {}", tableTopicConfig.kafkaTopic)
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
    fun getKRecordsCount() : Int {
        return kRecordQueue.size
    }

    /**
     * Returns the last consumed record
     *
     * @return kRecordQueue.elementAt(kRecordQueue.size -1)
     */
    fun getLastKRecord(): KRecord? {
        return kRecordQueue.elementAt(kRecordQueue.size - 1)
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
    }
}
