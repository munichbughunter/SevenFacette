package de.p7s1.qa.sevenfacette.kafka

import org.apache.kafka.clients.CommonClientConfigs
import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringSerializer
import java.util.Properties
import java.time.Duration

class KConsumer (brokers: String) {
    private val consumer = createConsumer(brokers)

    private fun createConsumer(brokers: String): Consumer<String,String> {
        val props = Properties()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = brokers
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        props[ConsumerConfig.GROUP_ID_CONFIG] = "group-id"
        props[CommonClientConfigs.SECURITY_PROTOCOL_CONFIG] = "SASL_SSL"
        return KafkaConsumer<String, String>(props)
    }

    fun process() {
        consumer.subscribe(listOf("topic..."))

        while (true) {
            val records = consumer.poll(Duration.ofSeconds(1))

            records.iterator().forEach {
                val message = it.value()
            }
        }
    }
}
