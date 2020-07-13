package de.p7s1.qa.sevenfacette.core


import de.p7s1.qa.sevenfacette.extension.autoClose
import de.p7s1.qa.sevenfacette.extension.isAlive
import io.github.bonigarcia.wdm.WebDriverManager.chromedriver
import io.github.bonigarcia.wdm.WebDriverManager.firefoxdriver
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.LocalFileDetector
import org.openqa.selenium.remote.RemoteWebDriver
import java.net.URI
import java.util.concurrent.ConcurrentHashMap


class FDFactory {
    private val driverContainer: MutableMap<Long, WebDriver> = ConcurrentHashMap(4)

    private val CHROME = "chrome"
    private val FIREFOX = "firefox"

    private fun createDriver(): WebDriver {
        //val browser = configuration.browserName()
        val browser = configuration.browserName
        //if (configuration.remoteUrl().isNotBlank()) {
        if (configuration.remoteUrl.isNotBlank()) {
            return createRemoteDriver(browser)
        }
        when (browser) {
            CHROME -> return createChromeDriver()
            FIREFOX -> return createFireFoxDriver()

            else -> throw IllegalArgumentException("$browser browserName doesn't support!")
        }
    }

    private fun createChromeDriver(): WebDriver {
        chromedriver().setup()
        // val capabilities = getCapabilities()
        // return ChromeDriver(capabilities.merge(getOptions()))
        return ChromeDriver(ChromeOptions())
    }

    private fun createFireFoxDriver(): WebDriver {
        firefoxdriver().setup()
        // return FirefoxDriver(getCapabilities())
        return FirefoxDriver(FirefoxOptions())
    }

    private fun createRemoteDriver(browser: String): WebDriver {
        //val remoteUrl = configuration.remoteUrl()
        val remoteUrl = configuration.remoteUrl

        var capabilities = DesiredCapabilities()
        when (browser) {
            CHROME -> capabilities = DesiredCapabilities.chrome().merge(getOptions())
            FIREFOX -> capabilities = DesiredCapabilities.firefox()
        }
        try {
            val driver = RemoteWebDriver(URI.create(remoteUrl).toURL(), capabilities
                    .merge(getCapabilities()))
            driver.fileDetector = LocalFileDetector()
            return driver
        } catch (ex: Exception) {
            System.err.println("""
                ====== Error to start RemoteWebdriver session ======
                $ex
                ====================================================
            """)
            throw ex
        }
    }

    fun setWebDriver(webDriver: WebDriver): WebDriver {
        driverContainer.put(Thread.currentThread().id, webDriver)
        return webDriver
    }

    fun getDriver(): WebDriver {
        val driver = driverContainer[Thread.currentThread().id]
        if (driver != null && driver.isAlive()) {
            return driver
        }
        val newDriver = createDriver()
        //newDriver.autoClose(configuration.autoClose())
        newDriver.autoClose(configuration.autoClose)
        return setWebDriver(newDriver)
    }

    private fun getOptions(): DesiredCapabilities {
        val options = ChromeOptions()
        if (configuration.chromeArgs!!.isNotEmpty()) options.addArguments(configuration.chromeArgs)
        if (!configuration.chromeBin!!.isNullOrEmpty()) options.setBinary(configuration.chromeBin)
        if (configuration.chromeExtensions!!.isNotEmpty()) options.addExtensions(configuration.chromeExtensions)

        val capabilities = DesiredCapabilities()
        capabilities.setCapability(ChromeOptions.CAPABILITY, options)

        return capabilities
    }

    private fun getCapabilities(): DesiredCapabilities {
        //val capabilities = configuration.capabilities()
        val capabilities = configuration.capabilities

        val map = HashMap<String, Any>()
        if (capabilities != null) {
            capabilities
                    .map { it.split("=") }
                    .forEach { map[it[0]] = convert(it[1]) }
        }

       return DesiredCapabilities(map)
        //return DesiredCapabilities(capabilities)
    }

    private fun convert(value: String): Any {
        if (value == "true" || value == "false")
            return value.toBoolean()
        return value
    }
}

val driverFactory = FDFactory()

//var configuration: FConfig = loadConfig(FConfig::class)
var configuration: FDConfig = loadConfig(FDConfig::class)

fun getDriver(): WebDriver {
    return driverFactory.getDriver()
}


fun getListener(): FEventListener {
    //val listenerClass = configuration.listenerClass()
    val listenerClass = configuration.listenerClass
    return if (listenerClass.isBlank()) {
        AbstractFEventListener()
    } else {
        Class.forName(listenerClass).newInstance() as FEventListener
    }
}
