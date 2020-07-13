package de.p7s1.qa.sevenfacette.driver

import de.p7s1.qa.sevenfacette.core.PageClass
import de.p7s1.qa.sevenfacette.core.SearchContext
import de.p7s1.qa.sevenfacette.core.Select
import de.p7s1.qa.sevenfacette.extension.select



abstract class Page(var browser: Browser) : SearchContext by browser {

    open val url: String? = null
    open val isAt: Browser.() -> Boolean = { true }
    val currentUrl: String
        get() = browser.currentUrl

    fun select(cssLocator: String): Select {
        return browser.select(cssLocator)
    }

    fun <T : Page> page(pageClass: PageClass<T>): T {
        return browser.at(pageClass)
    }
}
