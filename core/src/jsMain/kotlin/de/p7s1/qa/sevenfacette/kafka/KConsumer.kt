@file:JsModule("kafkajs") // file: is important!

package de.p7s1.qa.sevenfacette.kafka

external object Kafka {
    fun producer(): dynamic
    fun consumer(options: ConsumerOptions): dynamic
}
external interface ConsumerOptions {
    var groupID: String
}
