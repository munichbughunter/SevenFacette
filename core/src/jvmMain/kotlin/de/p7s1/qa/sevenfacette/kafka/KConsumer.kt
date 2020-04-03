package de.p7s1.qa.sevenfacette.kafka

import de.p7s1.qa.sevenfacette.kafka.config.KTableTopicConfig
import de.p7s1.qa.sevenfacette.kafka.config.SaslConfiguration
import java.time.Duration
import java.util.UUID
import java.util.concurrent.ConcurrentLinkedQueue
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import mu.KotlinLogging
import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer

/**
 * JVM specific implementation of the Kafka consumer
 *
 * @constructor the constructor receives the [tableTopicConfig]
 *
 * @author Patrick Döring
 */
private val logger = KotlinLogging.logger {}
class KConsumer (
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
        config[ConsumerConfig.GROUP_ID_CONFIG] = UUID.randomUUID().toString()
        config[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        config[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        config[ConsumerConfig.AUTO_OFFSET_RESET_CONFIG] = tableTopicConfig.kafkaConfig.autoOffset

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
    fun consume()  {
        consumer.subscribe(listOf(tableTopicConfig.kafkaTopic))
        GlobalScope.launch {
            logger.info("Start consuming and processing records")
            while (keepGoing) {
                consumer.poll(Duration.ofSeconds(tableTopicConfig.kafkaConfig.maxConsumingTime)).forEach {
                    kRecordQueue.add(KRecord(it.key(), it.value(), it.offset(), it.partition()))
                }
                stopConsumer()
            }
        }
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
