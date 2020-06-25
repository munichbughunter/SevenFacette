package de.p7s1.qa.sevenfacette.driver

import de.p7s1.qa.sevenfacette.config.ConfigurationSetup
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedCondition
import org.openqa.selenium.support.ui.WebDriverWait

/**
 * TODO: Add Description
 *
 * @author Patrick Döring
 */
internal class BrowserImpl(
        override val configurationSetup: ConfigurationSetup,
        override val driver: WebDriver = configurationSetup.driverFactory()) : Browser, WebDriver by driver {

    override val js = object : JavaScriptExecutor {
        override fun execute(vararg args: Any, async: Boolean, script: () -> String): Any? {
            if (driver is JavascriptExecutor) {
                return when (async) {
                    false -> driver.executeScript(script(), *args)
                    else -> driver.executeAsyncScript(script(), *args)
                }
            }

            throw UnsupportedOperationException()
        }
    }
}
