package de.p7s1.qa.sevenfacette.core


import de.p7s1.qa.sevenfacette.config.ConfigReader
import de.p7s1.qa.sevenfacette.config.types.FacetteConfig
import de.p7s1.qa.sevenfacette.config.types.WebConfig
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


val driverFactory = FDFactory()
// Hier wird die Config geladen bzw. gelesen...
//var configuration: WebConfig = loadConfig(WebConfig::class)

var facetteConfiguration: WebConfig = driverFactory.createConfig()

fun getDriver(): WebDriver {
    return driverFactory.getDriver()
}

class FDFactory {
    private val driverContainer: MutableMap<Long, WebDriver> = ConcurrentHashMap(4)

    private val CHROME = "chrome"
    private val FIREFOX = "firefox"

    private lateinit var config: WebConfig

    fun createConfig(): WebConfig {
        config = ConfigReader.getSeleniumConfig("web")!!
        return config
    }

    fun createDriver(): WebDriver {
        createConfig()
        //val browser = FacetteConfig.web!!.browserName
        val browser = config.browserName
        //if (configuration.remoteUrl().isNotBlank()) {
        //if (FacetteConfig.web!!.remoteUrl.isNotBlank()) {
        if (config.remoteUrl.isNotBlank()) {
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
        //val capabilities = getCapabilities()
        // return ChromeDriver(capabilities.merge(getOptions()))
        return ChromeDriver(ChromeOptions())
    }

    private fun createFireFoxDriver(): WebDriver {
        firefoxdriver().setup()
        // return FirefoxDriver(getCapabilities())
        return FirefoxDriver(FirefoxOptions())
    }

    private fun createRemoteDriver(browser: String): WebDriver {
        //val remoteUrl = FacetteConfig.web!!.remoteUrl
        val remoteUrl = config.remoteUrl
        //val remoteUrl = configuration.remoteUrl()

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
        //newDriver.autoClose(FacetteConfig.web!!.autoClose)
        newDriver.autoClose(config.autoClose)
        //newDriver.autoClose(configuration.autoClose())
        return setWebDriver(newDriver)
    }

    private fun getOptions(): DesiredCapabilities {
        val options = ChromeOptions()

        //FacetteConfig.web!!.chromeArgs.let { options.addArguments(FacetteConfig.web!!.chromeArgs) }
        config.chromeArgs.let { options.addArguments(config.chromeArgs) }
        //if (!FacetteConfig.web!!.chromeBin.isNullOrEmpty()) options.addArguments(FacetteConfig.web!!.chromeBin)
        if (!config.chromeBin.isNullOrEmpty()) options.addArguments(config.chromeBin)
        val capabilities = DesiredCapabilities()
        capabilities.setCapability(ChromeOptions.CAPABILITY, options)

        return capabilities
    }

    private fun getCapabilities(): DesiredCapabilities {
        //val capabilities = configuration.capabilities()
        //val capabilities = FacetteConfig.web!!.capabilities
        val capabilities = config.capabilities

        val map = HashMap<String, Any>()
        if (capabilities != null) {
            capabilities
                    .map { it.split("=") }
                    .forEach { map[it[0]] = convert(it[1]) }
        }

        return DesiredCapabilities(map)
    }

    private fun convert(value: String): Any {
        if (value == "true" || value == "false")
            return value.toBoolean()
        return value
    }
}




fun getListener(): FEventListener {
    // WORKAROUND!
    return AbstractFEventListener()

    //val listenerClass = configuration.listenerClass()
    //val listenerClass = FacetteConfig.web!!.listenerClass

    //return if (listenerClass.isBlank()) {
    //    AbstractFEventListener()
    //} else {
    //    Class.forName(listenerClass).newInstance() as FEventListener
    //}
}
