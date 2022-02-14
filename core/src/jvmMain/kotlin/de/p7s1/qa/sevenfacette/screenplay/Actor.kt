package de.p7s1.qa.sevenfacette.screenplay

import java.util.*
import java.util.function.Function.identity
import java.util.stream.Collectors.toMap


/**
 * Actor
 *
 * @author Patrick Döring
 */
open class Actor(protected val name: String?) {

    private val abilities: MutableMap<String, Ability> = mutableMapOf()
    private val properties: MutableMap<String, Any> = mutableMapOf()
    private val myAbilities: MutableMap<Class<out Ability>, Ability> = mutableMapOf()

    companion object {

        fun withName(name: String?): Actor {
            return Actor(name)
        }
    }

    fun can(vararg abilities: Ability): Actor {
        this.myAbilities.putAll(Arrays.stream(abilities).collect(toMap(Ability::javaClass, identity())))
        return this
    }


    fun <A : Ability?> uses(abilityClass: java.lang.Class<A>): A {
        return Optional.ofNullable(myAbilities[abilityClass])
            .map { obj: Any? -> abilityClass.cast(obj) }
            .orElseThrow { MissingAbilityException(this, abilityClass) }
    }

    fun withAbility(ability: Ability): Actor {
        abilities[ability.name()] = ability
        return this
    }

    fun hasAbility(abilityName: String): Boolean {
        return abilities.containsKey(abilityName)
    }

    fun <T> getAbility(abilityName: String, ability: Class<T>): T {
        return abilities[abilityName] as T
    }

    fun putProperty(key: String, value: Any): Actor {
        properties[key] = value
        return this
    }

    fun getProperty(key: String) : Any {
        return properties[key]!!
    }

    fun executeTasks(vararg tasks: Task) {
        tasks.forEach { it.executeWith(this) }
    }

    fun executeTask(task: Task) {
        task.executeWith(this)
    }

    fun execute(vararg actions: Action){
        actions.forEach { it.executeWith(this) }
    }

    fun askQuestions(vararg questions: Question) {
        questions.forEach { it.askWith(this) }
    }

    fun askQuestion(question: Question) {
        question.askWith(this)
    }
}
