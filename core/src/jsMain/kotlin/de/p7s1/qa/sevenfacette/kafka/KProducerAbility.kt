package de.p7s1.qa.sevenfacette.kafka

import io.ktor.util.KtorExperimentalAPI

/**
 * TODO: Add Description
 *
 * @author Patrick Döring
 */
@KtorExperimentalAPI
@ExperimentalJsExport
@JsName("KProducerAbility")
open class KProducerAbility (private val kafkaProducer: KProducer) {

    private var abilities = mutableListOf<KafkaProducerAbility>()
    private var producerAbility = kafkaProducer
        get() {
            return field
        }
        set(value) {
            field = value
        }

    @JsName("withConfiguration")
    fun withConfiguration(name: String, autoSend: Boolean) : Array<KafkaProducerAbility> {
        producerAbility = createKProducer(name, autoSend)
        abilities.add(KafkaProducerAbility(name, producerAbility))
        return abilities.toTypedArray()
    }

    fun sendKeyMessage(key: String, msg: String) {
        kafkaProducer.sendKeyMessage(key, msg)
    }

    fun disconnect() {
        kafkaProducer.disconnect()
    }

    fun getTopic() : String {
        return kafkaProducer.getTopic()
    }
}
