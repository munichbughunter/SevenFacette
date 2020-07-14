package de.p7s1.qa.sevenfacette.conditions

import de.p7s1.qa.sevenfacette.core.Select

abstract class SelectCondition : BaseCondition<Select>()

class SelectedOptions(val options: List<String>) : SelectCondition() {

    var actual: List<String>? = null

    override fun matches(item: Select): Boolean {
        actual = item.allSelectedOptions.map { it.text }
        return actual == options
    }

    override fun description(item: Select): Description {
        return SelectConditionDesc(actual!!, options, item, this)
    }
}

class SelectType : SelectCondition() {
    var actual: Boolean? = null

    override fun matches(item: Select): Boolean {
        actual = item.isMultiple
        return actual!!
    }

    override fun description(item: Select): Description {
        return SelectConditionDesc(actual!!, true, item, this)
    }
}

class SelectConditionDesc(actual: Any, expected: Any, val item: Select, val condition: SelectCondition) : Description(actual, expected) {
    override var message: String
        set(value) {}
        get() = """
            failed to assert $condition
            for $item
            reason: ${super.message}
                    """
}

class SelectOptions(val options: List<String>) : SelectCondition() {
    var actual: List<String>? = null

    override fun matches(item: Select): Boolean {
        actual = item.options.map { it.text }
        return actual == options
    }

    override fun description(item: Select): Description {
        return SelectConditionDesc(actual!!, options, item, this)
    }

}

fun selected(vararg options: String): SelectedOptions {
    return SelectedOptions(options.toList())
}

fun options(vararg options: String): SelectOptions {
    return SelectOptions(options.toList())
}

val multiple = SelectType()
