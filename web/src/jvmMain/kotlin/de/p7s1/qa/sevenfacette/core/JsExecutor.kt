package de.p7s1.qa.sevenfacette.core

import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver

class JsExecutor(private val driver: WebDriver) {

    @JvmOverloads
    fun execute(vararg args: Any, async: Boolean = false, script: () -> String): Any? {
        if (driver is JavascriptExecutor) {
            return when (async) {
                false -> driver.executeScript(script(), *args)
                else -> driver.executeAsyncScript(script(), *args)
            }
        }
        throw UnsupportedOperationException()
    }
}
