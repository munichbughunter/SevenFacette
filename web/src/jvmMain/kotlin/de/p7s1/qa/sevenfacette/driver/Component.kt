package de.p7s1.qa.sevenfacette.driver

import org.openqa.selenium.SearchContext
import org.openqa.selenium.WebElement

/**
 * TODO: Add Description
 *
 * @author Patrick Döring
 */
abstract class Component(val page: Page, val rootElement: WebElement) : ClickAndNavigate by page,
        ComponentMappingSupport by page,
        JavaScriptSupport by page,
        SearchContext by rootElement,
        WaitingSupport by page {

    val browser: Browser = page.browser
}
