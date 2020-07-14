package de.p7s1.qa.sevenfacette.locators

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement


class WebElementListLocator(private val by: By, private val driver: WebDriver) : ElementLocator<List<WebElement>> {
    override fun find(): List<WebElement> {
        return driver.findElements(by)
    }

    override val description: String
        get() = "collection located {$by}"
}
