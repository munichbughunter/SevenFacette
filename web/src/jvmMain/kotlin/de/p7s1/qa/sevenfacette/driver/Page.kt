package de.p7s1.qa.sevenfacette.driver

import org.openqa.selenium.SearchContext
import org.openqa.selenium.WebElement

/**
 * TODO: Add Description
 *
 * @author Patrick Döring
 */
abstract class Page (val browser: Browser) : ClickAndNavigate, ComponentMappingSupport,
        JavaScriptSupport by browser,
        SearchContext by browser,
        WaitingSupport by browser {

    companion object {
        @JvmStatic
        fun at(block: Browser.() -> Any): Browser.() -> Any = block
    }

    open val at: Browser.() -> Any = { true }

    open val url: String? = null

    override fun <T : Page> WebElement.click(factory: (Browser) -> T) : T {
        this.click()
        return browser.at(factory)
    }

    override fun <T : Component> WebElement.component(factory: (Page, WebElement) -> T) : T = factory(this@Page, this)

    override fun <T : Component> List<WebElement>.component(factory: (Page, WebElement) -> T): List<T> = this.map {
        factory(this@Page, it)
    }

    internal fun verifyAt() : Boolean = when(val result = at(browser)) {
        is Boolean -> result
        is Unit -> true
        else -> throw Error("Expressions of type `${result.javaClass.canonicalName}` are not allowed.")
    }
}
