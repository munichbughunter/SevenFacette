package de.p7s1.qa.sevenfacette.kafka

import de.p7s1.qa.sevenfacette.config.types.KafkaTopicConfig
import de.p7s1.qa.sevenfacette.kafka.externals.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * JS specific implementation of the Kafka consumer
 *
 * @constructor the constructor receives the [topicConfig]
 *
 * @author Patrick Döring
 */
actual class KConsumer actual constructor (
        private val topicConfig: KafkaTopicConfig
) {
    private var consumer: dynamic = ""
    private var fromBeginning: Boolean = false
    private var kRecords = mutableListOf<KRecord>()
    private var rejectUnauthorized = false

    @JsName("createKConsumer")
    fun createKConsumer(): KConsumer {
        createConsumer()
        initializeConsumer()
        return this
    }

    @JsName("getConsumer")
    fun getConsumer(): dynamic {
        return consumer
    }

    private fun initializeConsumer() {
        val consumerSubscription: ConsumerSubscribeTopic = js("({})")
        consumerSubscription.topic = getTopic()
        consumerSubscription.fromBeginning = fromBeginning
        consumer.connect()
        consumer.subscribe(consumerSubscription)
    }

    /**
     * Create a KafkaConsumer
     * @return [consumer]
     */
    fun createConsumer() {
        val kafkaOptions: KafkaConfig = js("({})")
        kafkaOptions.brokers = arrayOf(topicConfig.bootstrapServer)
        kafkaOptions.clientId = "7Facette-consumer"

        // AutoOffset is set at the call with true or false.
        // AutoCommit is done by KafkaJS itself.
        // autoCommitIntervall is set at the run and not as a single property
        // There are no single property for that in JS.

        if (topicConfig.useSASLAuthentication) {
            val saslOption: SASLOptions = js("({})")
            saslOption.mechanism = topicConfig.saslMechanism
            saslOption.password = topicConfig.saslPassword
            saslOption.username = topicConfig.saslUsername

            val sslOptions: SSLOptions = js("({})")
            sslOptions.rejectUnauthorized = rejectUnauthorized
            kafkaOptions.ssl = sslOptions
            kafkaOptions.sasl = saslOption
        }

        if (topicConfig.autoOffset.equals("earliest")) {
            fromBeginning = true
        }

        val consumerOptions: ConsumerConfig = js("({})")
        if (topicConfig.groupID.isBlank()) {
            consumerOptions.groupId = "7Facette_ConsumerGroup_" + (0..36).shuffled().first().toString()
        } else {
            consumerOptions.groupId = topicConfig.groupID
        }

        if (!topicConfig.isolationLevel.isBlank()) {
            // ToDo: Discuss about...
            consumerOptions.readUncommitted = topicConfig.isolationLevel.toBoolean()
        }
        consumerOptions.maxWaitTimeInMs = topicConfig.maxConsumingTime
        consumer = Kafka(kafkaOptions).consumer(consumerOptions)
    }

    @JsName("addKRecord")
    fun addKRecord(key: String, value: String, offset: Int, partition: Int) {
        kRecords.add(KRecord(key, value, offset, partition))
    }

    @JsName("getMessages")
    fun getMessages(): Array<KRecord> {
        return kRecords.toTypedArray()
    }

    @JsName("getKRecordsCount")
    actual fun getKRecordsCount(): Int {
        return kRecords.size
    }

    @JsName("filterByValue")
    fun filterByValue(pattern: String): Array<KRecord> {
        var valueFilteredList: List<KRecord> = listOf()
        valueFilteredList = kRecords.filter {(_, value) -> value!!.contains(pattern) }
        return valueFilteredList.toTypedArray()
    }

    @JsName("filterByKey")
    fun filterByKey(pattern: String): Array<KRecord> {
        var keyFilteredList: List<KRecord> = listOf()
        keyFilteredList = kRecords.filter {(key, _) -> key!!.contains(pattern) }
        return keyFilteredList.toTypedArray()
    }

    @JsName("getLastKRecord")
    fun getLastKRecord(): KRecord? {
        return kRecords.elementAt(kRecords.lastIndex)
    }

    @JsName("shutdown")
    fun shutdown() {
        GlobalScope.launch ( context = Dispatchers.Default ) {
            consumer.stop()
            delay(1000)
            consumer.disconnect()
        }
    }

    @JsName("getTopic")
    fun getTopic(): String {
        return topicConfig.topicName
    }
}


/*
FOR THE FUTURE
//   private fun consume() {
//        val consumerSubscription: ConsumerSubscribeTopic = js("({})")
//        consumerSubscription.topic = getTopic()
//        //consumerSubscription.fromBeginning = fromBeginning
//        consumerSubscription.fromBeginning = true
//        launch {
//            delay(1000)
//
//            consumer.subscribe(consumerSubscription)
//            delay(1000)
//            console.log("Yoo-Hoo! I'm in a coroutine now!")
//
//            val result = consumer.run(config.apply {
//                config.eachBatch = {
//                    var topic = it.batch.topic
//                    var partition = it.batch.partition
//                    var highWaterMark = it.batch.highWatermark
//                    var offsetLag = it.batch.offsetLag()
//                    var offsetLagLow = it.batch.offsetLagLow()
//                    var firstOffset = it.batch.firstOffset()
//                    var lastOffset = it.batch.lastOffset()
//
//                    println(it.batch.messages)
//
//                    Promise { resolve, reject -> }
//                }



//                config.eachMessage = {
//
//                    val topic = it.topic
//                    val partition = it.partition
//                    val message = it.message
//                    println(topic)
//                    println(partition)
//                    println(message.value.toString())
//
//
//                    Promise { resolve, reject ->
//                        // resolve()
//                    }
//                }
//            })
//            console.log("I have a $result without callbacks!")
//        }
//    }

    private fun launch(block: suspend () -> Unit) {
        block.startCoroutine(object : Continuation<Unit> {
            override val context: CoroutineContext get() = Dispatchers.Main
            override fun resumeWith(result: Result<Unit>) {
                println(result.isSuccess)
            }
        })
    }

    suspend fun <T> Promise<T>.await(): T = suspendCoroutine { cont ->
        then({ cont.resume(it) }, { cont.resumeWithException(it) })
    }
 */
