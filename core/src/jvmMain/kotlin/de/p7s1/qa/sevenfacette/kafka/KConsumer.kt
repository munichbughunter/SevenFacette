package de.p7s1.qa.sevenfacette.kafka

import de.p7s1.qa.sevenfacette.kafka.SaslSecurityProtocol.SSL
import kotlinx.coroutines.withTimeoutOrNull
import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import java.time.Duration
import java.util.*
import java.util.concurrent.CountDownLatch

class KConsumer (private val topic: String, expectedMessageCount: Int,
                  private var pattern: String,
                  private val latchWaitTime: Int)
{
    private val consumer = createConsumer()
    private val intermediateList: MutableList<String> = mutableListOf()
    private val messageList: MutableList<String> = mutableListOf()
    private var latch = CountDownLatch(expectedMessageCount)

    @Volatile
    var keepGoing = true

    private fun createConsumer() : Consumer<String, String> {
        var config = Properties()
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



    fun consume() {

        consumer.subscribe(listOf(topic))
        println("Consuming and processing data")

        consumer.use { kc ->
            while (keepGoing) {
                kc.poll(Duration.ofSeconds(latchWaitTime.toLong())).forEach {
                    collectMessage(it.value())
                }
                stop()
            }
        }
    }

    fun stop() {
        keepGoing = false
    }

    private fun collectMessage(message: String) {
        if (pattern.isEmpty()) {
            intermediateList.add(message)
            // ToDo Add logging
            return
        }
        if (latch.count == 0L) {
            return stop()
        }
        // ToDo: Validate that
        if (message.contains(pattern) || pattern == "*") {
            messageList.add(message)
            // ToDo Add logging
            latch.countDown()
            checkLatch()
        }
    }

    private fun checkLatch() {
        if (latch.count == 0L) {
            stop()
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

    fun hasMessage(): Boolean {
        return messageList.isNotEmpty()
    }
}
