package de.p7s1.qa.sevenfacette.driver

import de.p7s1.qa.sevenfacette.driver.FDFactory.Driver.*
import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.remote.RemoteWebDriver
import java.net.URL

/**
 * TODO: Add Description
 *
 * @author Patrick Döring
 */
class FDFactory {

    enum class Driver {
        CHROME, FIREFOX, REMOTE
    }

    companion object {
        @JvmStatic
        fun driver(driver: Driver, gridUrl: String? = null): RemoteWebDriver {
            return when (driver) {
                CHROME -> {
                    WebDriverManager.chromedriver().setup()
                    ChromeDriver(ChromeOptions().addArguments(commonArguments()))
                }
                FIREFOX -> {
                    ChromeDriver(ChromeOptions().addArguments(commonArguments()))
                }
                REMOTE -> {
                    RemoteWebDriver(URL(gridUrl.toString()), ChromeOptions().addArguments(commonArguments()))
                }
                else -> throw java.lang.IllegalArgumentException("No \"$driver\" driver available.")
            }
        }

        private fun commonArguments(): List<String> {
            return listOf(
                    "--start-maximized")
        }
    }
}
