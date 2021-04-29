package de.p7s1.qa.sevenfacette.screenplay

import de.p7s1.qa.sevenfacette.http.Abilities
import de.p7s1.qa.sevenfacette.http.GenericHttpClient
import de.p7s1.qa.sevenfacette.http.HttpClientAbility
import de.p7s1.qa.sevenfacette.http.createHttpClient
import io.ktor.util.KtorExperimentalAPI

/**
 * TODO: Add Description
 *
 * @author Patrick Döring
 */
@ExperimentalJsExport
@JsName("Actor")
@JsExport
open class Actor(protected val name: String?) {

    private var abilities: MutableMap<String, GenericHttpClient> = mutableMapOf()
    private var properties: MutableMap<String, Any> = mutableMapOf()

    companion object {
        fun withName(name: String?): Actor {
            return Actor(name)
        }
    }

    fun getName() : String? {
        return name
    }

    @JsName("withAbility")
    fun withAbility(ability: Array<Abilities>): Actor {
        ability.forEach {
            abilities.put(it.key!!, it.value)
        }
        return this
    }

    @JsName("hasAbility")
    fun hasAbility(abilityName: String): Boolean {
        return abilities.containsKey(abilityName)
    }

    @JsName("getAbility")
    fun getAbility(abilityName: String): GenericHttpClient? {
        println(abilities[abilityName])
        return abilities[abilityName]
    }

    @JsName("putProperty")
    fun putProperty(key: String, value: Any): Actor {
        properties[key] = value
        return this
    }

    @JsName("getProperty")
    fun getProperty(key: String): Any {
        return properties[key]!!
    }

    @JsName("executeTask")
    fun executeTask(task: Task) {
        task.executeWith(this)
    }

    @JsName("executeTasks")
    fun executeTasks(vararg tasks: Task) {
        tasks.forEach { it.executeWith(this) }
    }

    @JsName("execute")
    fun execute(vararg actions: Action) {
        actions.forEach { it.executeWith(this) }
    }

    @JsName("askQuestions")
    fun askQuestions(vararg questions: Question) {
        questions.forEach { it.askWith(this) }
    }

    @JsName("askQuestion")
    fun askQuestion(question: Question) {
        question.askWith(this)
    }
}
