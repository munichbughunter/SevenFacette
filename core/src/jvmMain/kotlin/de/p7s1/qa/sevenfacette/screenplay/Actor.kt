package de.p7s1.qa.sevenfacette.screenplay

/**
 * Actor
 *
 * @author Patrick Döring
 */
open class Actor(protected val name: String?) {

    private val abilities: MutableMap<String, Ability> = mutableMapOf()
    private val properties: MutableMap<String, Any> = mutableMapOf()

    companion object {

        fun withName(name: String?): Actor {
            return Actor(name)
        }
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
