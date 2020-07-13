package de.p7s1.qa.sevenfacette.locators

import de.p7s1.qa.sevenfacette.driver.FElement
import org.openqa.selenium.By
import org.openqa.selenium.WebElement

class InnerListWebElementLocator(private val by: By, private val element: FElement) : ElementLocator<List<WebElement>> {
    override fun find(): List<WebElement> {
        return element.webElement.findElements(by)
    }

    override val description: String
        get() = "($element).findAll($by)"
}
