package de.p7s1.qa.sevenfacette.kafka

import de.p7s1.qa.sevenfacette.config.types.KafkaTopicConfig
import de.p7s1.qa.sevenfacette.kafka.config.SaslConfig
import org.apache.kafka.clients.consumer.*
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.*
import org.apache.kafka.common.errors.WakeupException
import org.apache.kafka.common.serialization.StringDeserializer
import org.awaitility.Awaitility.with
import java.text.SimpleDateFormat
import java.time.Duration
import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.regex.Pattern
import java.util.stream.Collectors
import kotlin.collections.HashMap

/**
 * TODO: Add Description
 *
 * @author Patrick Doering
 */
actual class KConsumer actual constructor(topicConfig: KafkaTopicConfig) :  Runnable {
    // the KafkaConsumer. (from org.apache.kafka)
    private val consumer: KafkaConsumer<String, String>

    // map of offsets.
    private val currentOffsets: MutableMap<TopicPartition, OffsetAndMetadata> = HashMap()

    // list of consumed records.
    private val kafkaRecordQueue: ConcurrentLinkedQueue<KRecord> = ConcurrentLinkedQueue<KRecord>()

    // consumer will consume as long as keepGoing == true.
    private var keepGoing = true

    // the Thread the consumer will consume on.
    private lateinit var consumerThread: Thread

    private val maxConsumingTime: Long

    var topicName: String

    /**
     * initialize the library KafkaConsumer with the given KafkaClientConfig values.
     */
    init {
        var kafkaConfig: MutableMap<String, Any> = HashMap()
        kafkaConfig[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = topicConfig.bootstrapServer
        kafkaConfig[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        kafkaConfig[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        kafkaConfig[ConsumerConfig.AUTO_OFFSET_RESET_CONFIG] = topicConfig.autoOffset
        if (topicConfig.autoCommit) {
            kafkaConfig[ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG] = true
            kafkaConfig[ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG] = topicConfig.autoCommitInterval
        }
        if (topicConfig.groupID.isBlank()) {
            kafkaConfig[ConsumerConfig.GROUP_ID_CONFIG] = UUID.randomUUID().toString()
        } else {
            kafkaConfig[ConsumerConfig.GROUP_ID_CONFIG] = topicConfig.groupID
        }
        if (topicConfig.readIsolationLevel.isolationLevel.isNotBlank()) {
            kafkaConfig[ConsumerConfig.ISOLATION_LEVEL_CONFIG] = topicConfig.readIsolationLevel.toString().toLowerCase()
        }
        if (topicConfig.useSASLAuthentication) {
            kafkaConfig = SaslConfig.addSaslProperties(kafkaConfig, topicConfig)
        }
        consumer = KafkaConsumer(kafkaConfig)
        consumer.subscribe(listOf(topicConfig.topicName), HandleRebalance())
        maxConsumingTime = topicConfig.maxConsumingTime
        topicName = topicConfig.topicName
        //this.config = config;
    }

    /**
     * Start consuming on a separate thread. The KafkaConsumer will keep consuming until stop() is called.
     */
    fun consume() {
        keepGoing = true
        // create a new thread where the consumer will run on (consume() until stopped)
        consumerThread = Thread(this)
        consumerThread.start()
    }

    /**
     * Actual consuming function. Will be called when initializing the separate thread.
     */
    override fun run() {
        try {
            // looping until stop() is called on main thread, the shutdown hook will clean up on exit
            while (keepGoing) {
                val records = consumer.poll(Duration.ofMillis(maxConsumingTime))
                println("$currentTimeStamp  --  waiting for data...")
                for (myRecord in records) {
                    println("offset = " + myRecord.offset() + ", value = " + myRecord.value())
                    // add consumed record to record list
                    kafkaRecordQueue.add(
                        KRecord(
                            myRecord.key(),
                            myRecord.value(),
                            myRecord.offset(),
                            myRecord.partition()
                        )
                    )
                    currentOffsets[TopicPartition(myRecord.topic(), myRecord.partition())] =
                        OffsetAndMetadata(myRecord.offset() + 1, "no metadata")
                }
                // commit offsets
                for (tp in consumer.assignment()) println("Committing offset at position:$currentOffsets")
                consumer.commitAsync(
                    currentOffsets
                ) { offsets: Map<TopicPartition?, OffsetAndMetadata?>, exception: Exception? ->
                    if (exception != null) {
                        System.err.println("Failed to commit offsets: " + offsets + "; " + exception.message)
                    }
                }
            }
        } catch (e: WakeupException) {
            // ignore for shutdown
        } finally {
            try {
                consumer.commitSync(currentOffsets)
            } finally {
                consumer.close()
                println("Closed consumer and we are done")
            }
        }
    }

    /**
     * Stop the KafkaConsumer from consuming.
     */
    fun stop() {
        println("stop() was called. closing consumer...")
        keepGoing = false
        try {
            consumerThread.join()
        } catch (ex: InterruptedException) {
            println("Joining Thread failed!")
            Thread.currentThread().interrupt()
        }
    }

    /**
     * Seek to the last offset for each of the given partitions.
     */
    fun seekToEnd() {
        val partitions = consumer.assignment()
        consumer.seekToEnd(partitions)
    }

    /**
     * Wait for the KafkaConsumer to consume records and return them as a List.
     * If no records were consumed, this function will cause an exception.
     *
     * @param timeout maximum timeout to wait.
     * @param pollInterval polling interval.
     */
    fun waitForRecords(timeout: Int, pollInterval: Int): List<KRecord> {
        printTimeOutWarning(timeout)
        with().pollInterval(Duration.ofMillis(pollInterval.toLong())).await()
            .atMost(Duration.ofMillis(timeout.toLong()))
            .until { hasRecords() }
        return getRecords()
    }

    /**
     * Filter the consumed records by key and return them as a List. Also supports regular expressions.
     * If no consumed records match the given pattern, this function will cause an exception.
     *
     * @param pattern the pattern to filter by.
     * @param timeout (optional) the maximum timeout to wait.
     */
    fun filterByKey(pattern: String, timeout: Int?): List<KRecord> {
        if (timeout != null) {
            printTimeOutWarning(timeout)
        }
        return filterRecords(pattern, timeout ?: 5000, true)
    }

    /**
     * Filter the consumed records by value and return them as a List. Also supports regular expressions.
     * If no consumed records match the given pattern, this function will cause an exception.
     *
     * @param pattern the pattern to filter by.
     * @param timeout (optional) the maximum timeout to wait.
     */
    fun filterByValue(pattern: String, timeout: Int?): List<KRecord> {
        if (timeout != null) {
            printTimeOutWarning(timeout)
        }
        return filterRecords(pattern, timeout ?: 5000, false)
    }

    /**
     * Check if the list of consumed records is empty.
     */
    fun hasRecords(): Boolean {
        return !kafkaRecordQueue.isEmpty()
    }

    /**
     * Get the consumed records as a List.
     */
    fun getRecords(): List<KRecord> {
        return kafkaRecordQueue.toList()
    }

    /**
     * Get the number of consumed records.
     */
    fun getRecordsCount(): Int {
        return kafkaRecordQueue.size
    }

    /**
     * Get the last consumed record.
     */
    fun getLastRecord(): KRecord {
        return kafkaRecordQueue.last()
    }

    /**
     * Private helper method to filter the consumed records.
     */
    private fun filterRecords(pattern: String, timeout: Int, mode: Boolean): List<KRecord> {
        var filteredList: List<KRecord> = listOf()
        with().pollInterval(Duration.ofMillis(500)).await().atMost(Duration.ofMillis(timeout.toLong())).until {
            if (mode) {
                filteredList =
                    getRecords().stream().filter { r: KRecord ->
                        Pattern.compile(
                            pattern
                        ).matcher(r.key ?: "").find()
                    }
                        .collect(Collectors.toList())
            } else {
                filteredList =
                    getRecords().stream().filter { r: KRecord ->
                        Pattern.compile(
                            pattern
                        ).matcher(r.value ?: "").find()
                    }
                        .collect(Collectors.toList())
            }
            filteredList.isNotEmpty()
        }
        return filteredList
    }

    /**
     * Private helper method that prints a warning regarding the timeouts.
     * Because library KafkaConsumer performance, the timeout should be at least 5000ms...
     */
    private fun printTimeOutWarning(timeout: Int) {
        if (timeout < 5000) {
            System.err.println(
                "WARNING: consumer might need more time to consume than the set timeout. " +
                        "You might want to set the timeout to at least 5000ms."
            )
        }
    }

    /**
     * Private helper method to print timestamps.
     */
    private val currentTimeStamp: String
        get() = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(Date())

    /**
     * Private class to deal with KafkaConsumer internals.
     */
    private inner class HandleRebalance : ConsumerRebalanceListener {
        override fun onPartitionsRevoked(collection: Collection<TopicPartition>) {
            // commit offsets, so we won't calculate avg of data we've already read
            // println("Lost partitions in rebalance. Committing current offsets:$currentOffsets")
            consumer.commitSync(currentOffsets)
        }

        override fun onPartitionsAssigned(collection: Collection<TopicPartition>) {
            // nothing to do, since we have no state-store from which to recover previous buffers
        }
    }

    actual fun getKRecordsCount(): Int {
        return kafkaRecordQueue.size
    }
}
