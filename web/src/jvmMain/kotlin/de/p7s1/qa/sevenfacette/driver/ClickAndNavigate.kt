package de.p7s1.qa.sevenfacette.driver

import org.openqa.selenium.WebElement

/**
 * TODO: Add Description
 *
 * @author Patrick Döring
 */
interface ClickAndNavigate {
    fun <T : Page> WebElement.click(factory: (Browser) -> T) : T
}
