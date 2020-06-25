package de.p7s1.qa.sevenfacette.driver

import org.openqa.selenium.WebElement

/**
 * TODO: Add Description
 *
 * @author Patrick Döring
 */
interface ComponentMappingSupport {
    fun <T : Component> WebElement.component(factory: (Page, WebElement) -> T) : T

    fun <T : Component> List<WebElement>.component(factory: (Page, WebElement) -> T) : List<T>
}
