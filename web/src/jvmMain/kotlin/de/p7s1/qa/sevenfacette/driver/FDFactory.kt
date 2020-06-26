package de.p7s1.qa.sevenfacette.driver

import de.p7s1.qa.sevenfacette.driver.FDFactory.Driver.*
import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.remote.RemoteWebDriver
import java.net.URL

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
        fun driver(driver: Driver, gridUrl: String? = null): RemoteWebDriver {
            return when (driver) {
                CHROME -> {
                    if (gridUrl.isNullOrEmpty()) {
                        WebDriverManager.chromedriver().setup()
                        ChromeDriver(ChromeOptions().addArguments(commonArguments()))
                    } else {
                        RemoteWebDriver(URL("$gridUrl"), ChromeOptions().addArguments(commonArguments()))
                    }
                }
                FIREFOX -> {
                    if (gridUrl.isNullOrEmpty()) {
                        WebDriverManager.firefoxdriver().setup()
                        FirefoxDriver(FirefoxOptions().addArguments(commonArguments()))
                    } else {
                        RemoteWebDriver(URL("$gridUrl"), FirefoxOptions().addArguments(commonArguments()))
                    }
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
