package de.p7s1.qa.sevenfacette.locators

import de.p7s1.qa.sevenfacette.driver.FElement
import org.openqa.selenium.WebElement

class CachedElementCollectionLocator(private val list: List<FElement>, private val label: String) : ElementLocator<List<WebElement>> {
    override fun find(): List<WebElement> {
        return list.map { it.webElement }
    }

    override val description: String
        get() = label
}
