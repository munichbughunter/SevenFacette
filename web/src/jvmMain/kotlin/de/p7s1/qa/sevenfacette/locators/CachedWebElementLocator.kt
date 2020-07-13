package de.p7s1.qa.sevenfacette.locators

import org.openqa.selenium.WebElement


class CachedWebElementLocator(val locator: ElementLocator<List<WebElement>>,
                              val index: Int) : ElementLocator<WebElement> {
    override val description: String
        get() = "${locator.description}[$index]"

    override fun find(): WebElement {
        return locator.find()[index]
    }
}
