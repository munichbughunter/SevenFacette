package de.p7s1.qa.sevenfacette.conditions

import de.p7s1.qa.sevenfacette.utils.ConditionMismatchException
import org.apache.commons.lang3.StringUtils

class ConditionAssert {

    companion object {
        fun <T> evaluate(item: T, matcher: Condition<T>) {
            if (!matcher.matches(item)) {
                throw ConditionMismatchException(matcher.description(item).toString())
            }
        }
    }
}

abstract class Condition<in T> {

    abstract fun matches(item: T): Boolean

    abstract fun description(item: T): Description

    override fun toString(): String {
        return StringUtils.join(StringUtils.splitByCharacterTypeCamelCase(this.javaClass.simpleName), " ").toLowerCase()
    }
}
