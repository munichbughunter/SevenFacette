package de.p7s1.qa.sevenfacette.locators

import de.p7s1.qa.sevenfacette.driver.FElement
import org.openqa.selenium.By
import org.openqa.selenium.WebElement


class InnerWebElementLocator(private val by: By, private val element: FElement) : ElementLocator<WebElement> {
    override fun find(): WebElement {
        return element.webElement.findElement(by)
    }

    override val description: String
        get() = "($element).find($by)"
}
