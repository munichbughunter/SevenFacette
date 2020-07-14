package de.p7s1.qa.sevenfacette.conditions

import org.openqa.selenium.WebElement

abstract class CollectionCondition : BaseCondition<List<WebElement>>()

class CollectionSize(val size: Int) : CollectionCondition() {
    override fun matches(item: List<WebElement>): Boolean {
        return item.size == size
    }

    override fun description(item: List<WebElement>): Description {
        return Description(item.size, size)
    }
}

class CollectionMinimumSize(val index: Int) : CollectionCondition() {
    override fun matches(item: List<WebElement>): Boolean {
        return item.size-1 >= index
    }

    override fun description(item: List<WebElement>): Description {
        return MinimumSizeConditionDesc(item.size-1, index, this)
    }

    class MinimumSizeConditionDesc(actual: Any, expected: Any, val condition: CollectionCondition):
            Description(actual, expected) {
        override val reason: String? = "required index $actual exceeds collections size"
    }
}

class CollectionExactText(val text: Array<out String>) : CollectionCondition() {
    override fun matches(item: List<WebElement>): Boolean {
        return item.map { it.text } == text.toList()
    }

    override fun description(item: List<WebElement>): Description {
        return Description(item.map { it.text }, text.toList())
    }
}

class CollectionContainText(val text: String) : CollectionCondition() {
    override fun matches(item: List<WebElement>): Boolean {
        return item.any { it.text == text }
    }

    override fun description(item: List<WebElement>): Description {
        return Description(item.map { it.text }, text)
    }

    override fun toString(): String {
        return "collection have element with text"
    }
}
