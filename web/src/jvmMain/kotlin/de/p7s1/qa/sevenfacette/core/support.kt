package de.p7s1.qa.sevenfacette.core


import de.p7s1.qa.sevenfacette.conditions.ConditionAssert
import de.p7s1.qa.sevenfacette.conditions.SelectCondition
import de.p7s1.qa.sevenfacette.driver.FElement
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.Select


class Select(private val element: FElement) {

    private val select: Select
        get() = Select(element.webElement)

    val options: MutableList<WebElement> get() = select.options

    val isMultiple get() = select.isMultiple

    val firstSelectedOption get() = select.firstSelectedOption

    val allSelectedOptions: MutableList<WebElement> get() = select.allSelectedOptions

    fun selectOption(text: String) = select.selectByVisibleText(text)

    fun selectOption(index: Int) = select.selectByIndex(index)

    fun deselect(index: Int) = select.deselectByIndex(index)

    fun selectOptionByValue(value: String?) = select.selectByValue(value)

    fun deselect(text: String) = select.deselectByVisibleText(text)

    fun deselectOptionByValue(value: String?) = select.deselectByValue(value)

    fun deselectAll() = select.deselectAll()

    fun shouldBe(condition: SelectCondition) {
        shouldHave(condition)
    }

    fun shouldHave(condition: SelectCondition) {
        ConditionAssert.evaluate(this, condition)
    }

    override fun toString(): String {
        return element.toString()
    }
}
