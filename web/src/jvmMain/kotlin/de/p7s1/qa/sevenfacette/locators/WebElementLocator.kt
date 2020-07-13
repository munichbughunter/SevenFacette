package de.p7s1.qa.sevenfacette.locators

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

class WebElementLocator(private val by: By,
                        private val driver: WebDriver) : ElementLocator<WebElement> {
    override fun find(): WebElement {
        return driver.findElement(by)
    }

    override val description: String
        get() = "element located {$by}"
}
