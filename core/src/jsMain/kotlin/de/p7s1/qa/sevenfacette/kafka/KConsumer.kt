package de.p7s1.qa.sevenfacette.kafka

import de.p7s1.qa.sevenfacette.config.types.KafkaTopicConfig
import de.p7s1.qa.sevenfacette.kafka.externals.*
import io.ktor.util.Identity.decode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.promise
import kotlin.coroutines.suspendCoroutine
import kotlin.js.Promise

/**
 * JS specific implementation of the Kafka consumer
 *
 * @constructor the constructor receives the [topicConfig]
 *
 * @author Patrick Döring
 */
class KConsumer(
        private val topicConfig: KafkaTopicConfig
) {
    private var consumer: dynamic = ""
    private var fromBeginning: Boolean = false
    //private val kRecordQueue = arrayListOf<KRecord>()
    private var kRecordQueue = arrayOf<KafkaMessage>()
    private var keepGoing: Boolean = true

    @JsName("createKConsumer")
    fun createKConsumer(): KConsumer {
        createConsumer()
        return this
    }

    /**
     * Create a KafkaConsumer
     * @return [consumer]
     */
    private fun createConsumer() {
        val kafkaOptions: KafkaConfig = js("({})")
        kafkaOptions.brokers = arrayOf(topicConfig.bootstrapServer)

        // AutoOffset is set at the call with true or false.
        // AutoCommit is done by KafkaJS itself.
        // autoCommitIntervall is set at the run and not as a single property
        // There are no single property for that in JS.
        if (topicConfig.useSASLAuthentication) {
            kafkaOptions.ssl = topicConfig.useSASLAuthentication
            kafkaOptions.sasl?.mechanism = topicConfig.saslMechanism!!
            kafkaOptions.sasl?.username = topicConfig.saslUsername!!
            kafkaOptions.sasl?.password = topicConfig.saslPassword!!
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
        consume()
    }


    fun consume() {
        val consumerSubscription: ConsumerSubscribeTopic = js("({})")
        consumerSubscription.topic = getTopic()
        //consumerSubscription.fromBeginning = fromBeginning
        consumerSubscription.fromBeginning = true

        // default values set
        val config: ConsumerRunConfig = js("({})")
        config.autoCommit = true
        config.autoCommitInterval = 0
        config.autoCommitThreshold = null
        config.eachBatchAutoResolve = true
        config.partitionsConsumedConcurrently = 1
        config.eachBatch = null
        config.eachMessage = null

        consumer.logger().debug

        consumer.connect()
        GlobalScope.launch ( context = Dispatchers.Default ) {
            delay(1000)
            consumer.subscribe(consumerSubscription)
            delay(1000)


            val res: Promise<Unit> = consumer.run(config.apply {

                config.eachMessage = {
//                    val topic = it.batch.topic
//                    val partition = it.batch.partition
//                    val message = it.batch.messages
                    val topic = it.topic
                    val partition = it.partition
                    val message = it.message

                    println(topic)
                    println(partition)
                    println(message.value.toString())
//                    kRecordQueue.forEach {
//                        println(it.value.toString())
//                    }

                    Promise { resolve, reject ->
                        resolve()
                    }
                }
            })
            delay(500)
        }
    }

    private fun resolve() {

    }

    @JsName("getMessages")
    fun getMessages(): Array<KafkaMessage> {
        return kRecordQueue
    }

    @JsName("shutdown")
    fun shutdown() {
        keepGoing = false
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
