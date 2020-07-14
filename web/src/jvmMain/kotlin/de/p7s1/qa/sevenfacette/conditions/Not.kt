package de.p7s1.qa.sevenfacette.conditions


abstract class BaseCondition<in T> : Condition<T>()

class Not<in T>(val condition: Condition<T>) : BaseCondition<T>() {

    val msg: String =
            """%s
                expected not: %s
                actual: %s
                """

    override fun matches(item: T): Boolean {
        return !condition.matches(item)
    }

    override fun description(item: T): Description {
        return condition.description(item).apply {
            diff = false
            message = msg
        }
    }

    override fun toString(): String {
        return condition.toString()
    }
}

fun <T> not(condition: Condition<T>): Condition<T> {
    return Not(condition)
}
