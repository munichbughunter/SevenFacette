package de.p7s1.qa.sevenfacette.driver

import de.p7s1.qa.sevenfacette.conditions.CollectionCondition
import de.p7s1.qa.sevenfacette.conditions.ElementCondition
import de.p7s1.qa.sevenfacette.conditions.empty
import de.p7s1.qa.sevenfacette.conditions.sizeAtLeast
import de.p7s1.qa.sevenfacette.locators.CachedElementCollectionLocator
import de.p7s1.qa.sevenfacette.locators.CachedWebElementLocator
import de.p7s1.qa.sevenfacette.locators.ElementLocator
import de.p7s1.qa.sevenfacette.locators.WebElementListLocator
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement


class FElementCollection(locator: ElementLocator<List<WebElement>>,
                         driver: WebDriver) :
        Element<List<WebElement>>(locator, driver), Collection<FElement> {

    constructor(locator: By, driver: WebDriver)
            : this(WebElementListLocator(locator, driver), driver)

    val webElements: List<WebElement>
        get() = locator.find()

    override val size: Int
        get() = webElements.size

    infix fun should(condition: CollectionCondition) {
        super.should(condition)
    }

    infix fun shouldNot(condition: CollectionCondition) {
        super.shouldNotBe(empty)
        super.shouldNot(condition)
    }

    fun waitUntil(condition: CollectionCondition, timeout: Int = this.waitTimeout): FElementCollection {
        this.should(condition, timeout)
        return this
    }

    override fun isEmpty(): Boolean {
        return webElements.isEmpty()
    }

    override fun iterator(): Iterator<FElement> = object : Iterator<FElement> {
        var index = 0

        override fun hasNext(): Boolean {
            return webElements.size > this.index
        }

        override fun next(): FElement {
            val indexedElement = get(index)
            this.index += 1
            return indexedElement
        }
    }

    override fun toString(): String {
        return locator.description
    }

    override fun contains(element: FElement): Boolean {
        throw NotImplementedError()
    }

    override fun containsAll(elements: Collection<FElement>): Boolean {
        throw NotImplementedError()
    }

    operator fun get(index: Int): FElement {
        shouldHave(sizeAtLeast(index))
        return FElement(CachedWebElementLocator(locator, index), driver)
    }

    fun filterBy(condition: ElementCondition): FElementCollection {
        return FElementCollection(CachedElementCollectionLocator(this.filter { condition.matches(it.webElement) }, "$this and filtered by $condition"), driver)
    }
}
