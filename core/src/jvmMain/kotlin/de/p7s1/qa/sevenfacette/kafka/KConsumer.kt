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

    var username = "f79z35t6"
    var password = "TVJN359WjKNeL32KDGGc0c9MT77ng0FF"
    var jaasTemplate = "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"%s\" password=\"%s\";"
    var jaasCfg = String.format(jaasTemplate, username, password)
    // KAFKA_SASL_USERNAME=sg.qa.dev
    //KAFKA_SASL_PASSWORD=YAQWcPPhlyfeIhTKYoDnhkGubSYkMnIDABikoSDxpBGevUeyCcDKSWGsfNbRpauI
// this.topic = username + "-default";
//
//        String jaasTemplate = "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"%s\" password=\"%s\";";
//        String jaasCfg = String.format(jaasTemplate, username, password);
//
//        String serializer = StringSerializer.class.getName();
//        String deserializer = StringDeserializer.class.getName();
//        props = new Properties();
//        props.put("bootstrap.servers", brokers);
//        props.put("group.id", username + "-consumer");
//        props.put("enable.auto.commit", "true");
//        props.put("auto.commit.interval.ms", "1000");
//        props.put("auto.offset.reset", "earliest");
//        props.put("session.timeout.ms", "30000");
//        props.put("key.deserializer", deserializer);
//        props.put("value.deserializer", deserializer);
//        props.put("key.serializer", serializer);
//        props.put("value.serializer", serializer);
//        props.put("security.protocol", "SASL_SSL");
//        props.put("sasl.mechanism", "SCRAM-SHA-256");
//        props.put("sasl.jaas.config", jaasCfg);
    init {
        val config = Properties()
        config[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = "moped-01.srvs.cloudkafka.com:9094"
        config[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        config[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        config[ConsumerConfig.GROUP_ID_CONFIG] = "$username-consumer"
        config["security.protocol"] = "SASL_SSL"
        config["sasl.mechanism"] = "SCRAM-SHA-256"
        config["sasl.jaas.config"] = jaasCfg

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
