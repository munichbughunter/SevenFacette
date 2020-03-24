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
import org.apache.kafka.common.serialization.StringDeserializer
import java.time.Duration
import java.util.UUID
import java.util.concurrent.CountDownLatch
import kotlin.coroutines.CoroutineContext

class KConsumer(
        private val topic: String,
        private var expectedMessageCount: Int,
        private var pattern: String,
        private var latchWaitTime: Int
) : CoroutineScope by CoroutineScope(Dispatchers.Default){
    private val job = Job()
    private var messageCount = expectedMessageCount
    private val intermediateList: MutableList<String> = mutableListOf()
    private var latch = CountDownLatch(messageCount)
    private var keepGoing = true
    private val messageList: MutableList<String> = mutableListOf()
    private val consumer = createConsumer()

    private fun createConsumer() : Consumer<String, String> {
        var config : MutableMap<String, Any> = mutableMapOf()
        // ToDo: That should come from the config
        config[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = System.getenv("BOOT_STRAP_SERVER")
        config[ConsumerConfig.GROUP_ID_CONFIG] = UUID.randomUUID().toString()
        config[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        config[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        // ToDo: That should come from the config
        config[ConsumerConfig.AUTO_OFFSET_RESET_CONFIG] = System.getenv("AUTO_OFFSET")
        // ToDo: THE SSL should also come from the config
        config = SaslConfiguration.addSaslProperties(config, SSL)
        return KafkaConsumer<String, String>(config)
    }

    fun reConfigure(expectedMessageCount: Int,
                    pattern: String,
                    latchWaitTime: Int,
                    streamIndex: String) {
        this.latchWaitTime = latchWaitTime
        this.messageCount = expectedMessageCount
        if (!"".equals(streamIndex)) {
            this.pattern = streamIndex
        } else {
            this.pattern = pattern
        }
        latch = CountDownLatch(this.messageCount)
        intermediateList
                .filter { msg -> msg.contains(streamIndex) }
                .forEach(this::collectMessage)
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
        consumer.subscribe(listOf(topic))
        GlobalScope.launch {
            println("Consuming and processing data")
            while (keepGoing) {
                consumer.poll(Duration.ofSeconds(latchWaitTime.toLong())).forEach {
                    collectMessage(it.value())
                }
                stopConsumer()
            }
        }
    }

    private fun collectMessage(message: String) {
        if (pattern.isEmpty()) {
            intermediateList.add(message)
            // ToDo Add logging
            return
        }
        if (latch.count == 0L) {
            return stopConsumer()
        }
        // ToDo: Validate that
        if (message.contains(pattern) || pattern == "*") {
            messageList.add(message)
            // ToDo Add logging
            latch.countDown()
            checkLatch()
        }
    }

    fun getMessageList(): List<String?>? {
        return messageList
    }

    private fun checkLatch() {
        if (latch.count == 0L) {
            stopConsumer()
        }
    }

    fun getMessageCount() : Int {
        return messageList.size
    }

    fun getAllMessages(): List<String?>? {
        return messageList
    }

    fun getLastMessage(): String? {
        return messageList[messageList.size - 1]
    }

    private fun hasMessage(): Boolean {
        return messageList.isNotEmpty()
    }

    private fun stopConsumer() {
        keepGoing = false
        shutdown()
    }
}
