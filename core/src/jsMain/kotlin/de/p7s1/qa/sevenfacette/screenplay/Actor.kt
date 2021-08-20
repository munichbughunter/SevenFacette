package de.p7s1.qa.sevenfacette.screenplay

import de.p7s1.qa.sevenfacette.http.HttpAbility
import de.p7s1.qa.sevenfacette.http.GenericHttpClient
import de.p7s1.qa.sevenfacette.kafka.KConsumer
/*import de.p7s1.qa.sevenfacette.kafka.KProducer
import de.p7s1.qa.sevenfacette.kafka.KafkaConsumerAbility
import de.p7s1.qa.sevenfacette.kafka.KafkaProducerAbility*/
import io.ktor.util.KtorExperimentalAPI

/**
 * TODO: Add Description
 *
 * @author Patrick Döring
 */
@KtorExperimentalAPI
@ExperimentalJsExport
@JsName("Actor")
@JsExport
open class Actor(protected val name: String?) {

    private var httpAbilities : MutableMap<String, GenericHttpClient> = mutableMapOf()
    private var kafkaConsumerAbilities : MutableMap<String, KConsumer> = mutableMapOf()
    //private var kafkaProducerAbilities : MutableMap<String, KProducer> = mutableMapOf()
    private var properties: MutableMap<String, Any> = mutableMapOf()

    companion object {
        fun withName(name: String?): Actor {
            return Actor(name)
        }
    }

    fun getName() : String? {
        return name
    }

    fun withHttpAbility(ability: Array<HttpAbility>): Actor {
        ability.forEach {
            httpAbilities.put(it.key!!, it.value)
        }
        return this
    }

    fun hasHttpAbility(abilityName: String): Boolean {
        return httpAbilities.containsKey(abilityName)
    }

    fun getHttpAbility(abilityName: String): GenericHttpClient? {
        return httpAbilities[abilityName]
    }

    /*fun withConsumerAbility(ability: Array<KafkaConsumerAbility>): Actor {
        ability.forEach {
            kafkaConsumerAbilities.put(it.key!!, it.value)
        }
        return this
    }*/

    fun hasConsumerAbility(abilityName: String) : Boolean {
        return kafkaConsumerAbilities.containsKey(abilityName)
    }

    fun getConsumerAbility(abilityName: String) : KConsumer? {
        return kafkaConsumerAbilities[abilityName]
    }

    /*fun withProducerAbility(ability: Array<KafkaProducerAbility>): Actor {
        ability.forEach {
            kafkaProducerAbilities.put(it.key!!, it.value)
        }
        return this
    }

    fun hasProducerAbility(abilityName: String) : Boolean {
        return kafkaProducerAbilities.containsKey(abilityName)
    }

    fun getProducerAbility(abilityName: String) : KProducer? {
        return kafkaProducerAbilities[abilityName]
    }*/

    fun putProperty(key: String, value: Any): Actor {
        properties[key] = value
        return this
    }

    fun getProperty(key: String): Any {
        return properties[key]!!
    }

    fun executeTask(task: Task) {
        task.executeWith(this)
    }

    fun executeTasks(vararg tasks: Task) {
        tasks.forEach { it.executeWith(this) }
    }

    fun execute(vararg actions: Action) {
        actions.forEach { it.executeWith(this) }
    }

    fun askQuestions(vararg questions: Question) {
        questions.forEach { it.askWith(this) }
    }

    fun askQuestion(question: Question) {
        question.askWith(this)
    }
}
