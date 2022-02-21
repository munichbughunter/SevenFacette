package de.p7s1.qa.sevenfacette.kafka

import de.p7s1.qa.sevenfacette.screenplay.Ability

/**
 * TODO: Add Description
 *
 * @author Patrick Döring
 */
class ProducerAbility (private val producer: KProducer) : Ability {

    override fun name(): String {
        return abilityName
    }

    override fun <T> withConfiguration(name: String): ProducerAbility {
        abilityName = name
        return ProducerAbility(KFactory.createKProducer(name, true))
    }

    companion object {
        var abilityName : String = ""
       /* fun withConfiguration(name: String, autoSend: Boolean) : ProducerAbility {
            abilityName = name
            return ProducerAbility(KFactory.createKProducer(name, autoSend))
        }*/
    }

    fun send(msg: String) {
        producer.send(msg)
    }

    fun sendKeyMessage(key: String, msg: String) {
        producer.sendKeyMessage(key, msg)
    }

    fun flush() = producer.flush()
}
