package de.p7s1.qa.sevenfacette.kafka

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import java.time.Duration
import java.util.*
import java.util.concurrent.*

class KConsumer (topic: String) {
    private val kConsumer: KafkaConsumer<String, String>
    private val intermediateList: MutableList<String> = mutableListOf()
    private val messageList: MutableList<String> = mutableListOf()
    private var pattern: String = ""
    private var latch = CountDownLatch(1)
    private var latchWaitTime = 0
    private var expectedMessageCount = 1


    @Volatile
    var keepGoing = true


    init {
        val config = Properties()


        config[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        config[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java

        kConsumer = KafkaConsumer<String, String>(config).apply {
            subscribe(listOf(topic))
        }
    }

    fun consume(handler: (value: String) -> Unit) = Thread(Runnable {
        keepGoing = true
        kConsumer.use { kc ->
            while (keepGoing) {
                kc.poll(Duration.ofSeconds(60))?.forEach {
                    handler(it?.value() ?: "???")
                }
            }
        }
    }).start()

    fun configure(expectedMessageCount: Int,
                  pattern: String?,
                  streamIndex: String,
                  latchWaitTime: Int) {
        this.latchWaitTime = latchWaitTime
        this.expectedMessageCount = expectedMessageCount
        if ("" != streamIndex) {
            this.pattern = streamIndex
        } else {
            this.pattern = pattern!!
        }
        latch = CountDownLatch(expectedMessageCount)
        intermediateList
                .stream()
                .filter { message: String -> message.contains(streamIndex) }
                .forEach { message: String? -> collectMessage(message!!) }
    }

    fun getAllMessages(): List<String?>? {
        try {
            latch.await(latchWaitTime.toLong(), TimeUnit.MILLISECONDS)
        } catch (e: InterruptedException) {
            throw RuntimeException(e)
        }
        return messageList
    }

    fun getLastMessage(): String? {
        try {
            latch.await(latchWaitTime.toLong(), TimeUnit.MILLISECONDS)
        } catch (e: InterruptedException) {
            throw RuntimeException(e)
        }
        return messageList[messageList.size - 1]
    }

    fun hasMessage(): Boolean {
        try {
            latch.await(latchWaitTime.toLong(), TimeUnit.MILLISECONDS)
        } catch (e: InterruptedException) {
            throw RuntimeException(e)
        }
        // ToDo: Add Logging
        return messageList.isNotEmpty()
    }

    private fun collectMessage(message: String) {
        if (pattern.isEmpty()) {
            intermediateList.add(message)
            // ToDo Add logging
            return
        }
        if (latch.count == 0L) {
            return
        }
        if (message.contains(pattern) || pattern == "*") {
            messageList.add(message)
            // ToDo Add logging
            latch.countDown()
        }
    }

    fun stop() {
        keepGoing = false
    }
}
