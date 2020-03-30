package de.p7s1.qa.sevenfacette.kafka

import de.p7s1.qa.sevenfacette.kafka.SaslSecurityProtocol.SSL
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.config.SaslConfigs
import org.apache.kafka.common.serialization.StringDeserializer
import java.time.Duration
import java.util.UUID
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.CountDownLatch
import kotlin.coroutines.CoroutineContext

class KConsumer (
        private val consumerConfig: KConfig
) : CoroutineScope by CoroutineScope(Dispatchers.Default){
    private val job = Job()
    private val messagequeue = ConcurrentLinkedQueue<String>()
    private var keepGoing = true
    private val consumer = createConsumer()

    public fun createConsumer() : Consumer<String, String> {
        //return KafkaConsumer<String, String>(KConsumerConfig)
        var config : MutableMap<String, Any> = mutableMapOf()
        config[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = consumerConfig.bootstrapServer
        config[ConsumerConfig.GROUP_ID_CONFIG] = UUID.randomUUID().toString()
        config[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        config[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        config[ConsumerConfig.AUTO_OFFSET_RESET_CONFIG] = consumerConfig.autoOffset
        /**
         * TODO: Discuss if it makes sense...
         */
        if (consumerConfig.saslConfig) {
            config = SaslConfiguration.addSaslProperties(config, consumerConfig)
        }
        return KafkaConsumer<String, String>(config)
    }

    override val coroutineContext: CoroutineContext
        get() = job

    private fun shutdown() {
        job.complete()
        try {
            consumer.close(Duration.ofMillis(5000L))
        } catch (ex: ConcurrentModificationException) {
            println("Kafka consumer closed")
        }
    }

    fun waitForMessage(waitingTime: Int): Boolean {
        var waited : Int = 0
        var hasMessage : Boolean = false

        do {
            Thread.sleep(500)
            waited += 500
            hasMessage = hasMessage()
        } while (!hasMessage && waited <= waitingTime)
        stopConsumer()
        return hasMessage
    }

    fun consume()  {
        consumer.subscribe(listOf(consumerConfig.kafkaTopic))
        GlobalScope.launch {
            println("Consuming and processing data")
            while (keepGoing) {
                consumer.poll(Duration.ofSeconds(consumerConfig.maxConsumingTime)).forEach {
                    messagequeue.add(it.value())
                    /**
                     * TODO: Think about using the key
                     */
                }
                stopConsumer()
            }
        }
    }

    fun getMessages(): ConcurrentLinkedQueue<String> {
        return messagequeue
    }

    fun getMessageCount() : Int {
        return messagequeue.size
    }

    fun getLastMessage(): String? {
        return messagequeue.elementAt(messagequeue.size -1)
        //return messageList[messageList.size - 1]
    }

    private fun hasMessage(): Boolean {
        return !messagequeue.isEmpty()
    }

    private fun stopConsumer() {
        keepGoing = false
        shutdown()
    }
}
