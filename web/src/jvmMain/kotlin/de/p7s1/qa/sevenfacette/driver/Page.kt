package de.p7s1.qa.sevenfacette.driver

import de.p7s1.qa.sevenfacette.core.JsExecutor
import de.p7s1.qa.sevenfacette.core.PageClass
import de.p7s1.qa.sevenfacette.core.SearchContext
import de.p7s1.qa.sevenfacette.core.Select
import de.p7s1.qa.sevenfacette.extension.select
import org.openqa.selenium.By
import java.lang.RuntimeException


abstract class Page(private val browser: Browser) : SearchContext by browser {

    open val url: String? = null
    open val isAt: Browser.() -> Boolean = { true }
    abstract fun isAtPage(): Boolean

    fun refreshPage() {
        browser.refresh()
    }

    fun refreshPageWithConfirmation() {
        browser.refresh()
        browser.alert.accept()
    }

    fun navigateBack() {
        browser.back()
    }

    protected fun pauseExecution(milliSec: Int) {
        try {
            Thread.sleep(milliSec.toLong())
        } catch (e: InterruptedException) {
            // Logging
            throw RuntimeException("An error ocurred while sleeping: Error[{}]", e.cause)
        }
    }

    protected fun refreshPageUntilElementIsVisible(refreshTimes: Int, locator: By): Boolean {
        var shouldTryAgain = true
        var repeatTimes = 0
        while (shouldTryAgain && repeatTimes < refreshTimes) {
            shouldTryAgain = browser.all(locator).isNotEmpty()
            if (shouldTryAgain) { // didn`t found yet -> refresh
                // logging....
                refreshPage()
                repeatTimes++
            } else {
                return true // found don`t repeat
            }
        }
        return false
    }

    val currentUrl: String
        get() = browser.currentUrl

    fun select(cssLocator: String): Select {
        return browser.select(cssLocator)
    }

    fun select(locator: By): Select {
        return browser.select(locator)
    }

    fun <T : Page> page(pageClass: PageClass<T>): T {
        return browser.at(pageClass)
    }
}
