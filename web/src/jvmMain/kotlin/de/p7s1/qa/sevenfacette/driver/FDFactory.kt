package de.p7s1.qa.sevenfacette.driver

import de.p7s1.qa.sevenfacette.driver.FDFactory.Driver.CHROME
import de.p7s1.qa.sevenfacette.driver.FDFactory.Driver.FIREFOX
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.remote.RemoteWebDriver

/**
 * TODO: Add Description
 *
 * @author Patrick Döring
 */
class FDFactory {

    enum class Driver {
        CHROME, FIREFOX
    }

    companion object {
        @JvmStatic
        fun driver(driver: Driver): RemoteWebDriver {
            return when (driver) {
                CHROME -> {
                    ChromeDriver(ChromeOptions().addArguments(commonArguments()))
                }
                FIREFOX -> {
                    ChromeDriver(ChromeOptions().addArguments(commonArguments()))
                }
                else -> throw java.lang.IllegalArgumentException("No \"$driver\" driver available.")
            }
        }

        private fun commonArguments(): List<String> {
            return listOf(
                    "--headless",
                    "--window-size=1200,800")
        }
    }
}
