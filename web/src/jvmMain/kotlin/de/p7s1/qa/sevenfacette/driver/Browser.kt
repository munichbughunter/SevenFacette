package de.p7s1.qa.sevenfacette.driver


import com.assertthat.selenium_shutterbug.core.Shutterbug
import com.assertthat.selenium_shutterbug.utils.web.ScrollStrategy.WHOLE_PAGE
import de.p7s1.qa.sevenfacette.config.types.FacetteConfig
import de.p7s1.qa.sevenfacette.config.types.WebConfig
import de.p7s1.qa.sevenfacette.core.*
import de.p7s1.qa.sevenfacette.extension.isAlive
import de.p7s1.qa.sevenfacette.extension.logs
import de.p7s1.qa.sevenfacette.extension.saveScreenshot
import org.openqa.selenium.Alert
import org.openqa.selenium.By
import org.openqa.selenium.Dimension
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.logging.LogEntries
import org.openqa.selenium.logging.LogType
import java.awt.image.BufferedImage
import java.io.File

class Browser(val driver: WebDriver = getDriver(),
              val listener: FEventListener = getListener()) : SearchContext, Navigable {

    init {
        listener.onStart()
    }

    //var config: FConfig = loadConfig(FConfig::class)
    var config: WebConfig = FacetteConfig.web!!

    //var baseUrl: String by baseUrl()
    var baseUrl: String = FacetteConfig.web!!.baseUrl

    //var timeout: Int by timeout()
    var timeout: Int = FacetteConfig.web!!.timeout

    //var poolingInterval: Double by poolingInterval()
    var poolingInterval: Double = FacetteConfig.web!!.pollingInterval

    //var screenSize: List<Int> by screenSize()
    var screenSize: List<Int> = FacetteConfig.web!!.screenSize

    val actions = Actions(driver)

    val currentUrl: String get() = driver.currentUrl

    val js: JsExecutor by lazy { JsExecutor(driver) }

    override fun open(url: String) {
        listener.beforeNavigation(url, driver)
        if (screenSize.isNotEmpty()) {
            driver.manage().window().size = Dimension(screenSize[0], screenSize[1])
        }

        if (isAbsoluteUrl(url)) {
            driver.navigate().to(url)
        } else {
            driver.navigate().to(baseUrl + url)
        }
        listener.afterNavigation(url, driver)
    }

    private fun isAbsoluteUrl(url: String): Boolean {
        return (url.startsWith("http://") || url.startsWith("https://"))
    }

    fun to(url: String) {
        open(url)
    }

    fun to(url: String, block: Browser.() -> Unit) {
        to(url)
        this.apply(block)
    }

    fun interact(block: Actions.() -> Unit) {
        this.actions.apply(block).build().perform()
    }

    override fun <T : Page> to(pageClass: (Browser) -> T): T {
        val page = pageClass(this)
        page.url?.let { open(it) }
        return page
    }

    fun <T : Page> at(pageClass: PageClass<T>): T {
        val page = pageClass(this)
        assert(page.isAt(this))
        return page
    }

    override fun <T : Page> at(pageClass: PageClass<T>, closure: T.() -> Unit): T {
        val page = pageClass(this)
        page.closure()
        return page
    }

    override fun element(by: By): FElement {
        listener.beforeElementLocation(by, driver)
        return FElement(by, driver).apply {
            waitTimeout = timeout
            waitPoolingInterval = poolingInterval
            eventListener = listener
        }
    }

    override fun all(by: By): FElementCollection {
        listener.beforeElementLocation(by, driver)
        return FElementCollection(by, driver).apply {
            waitTimeout = timeout
            waitPoolingInterval = poolingInterval
        }
    }

    fun takeScreenshot(saveTo: String = "${System.getProperty("user.dir")}/build/reports/screen_${System.currentTimeMillis()}.png"): File {
        val file = driver.saveScreenshot(saveTo)
        screenshots.put(Thread.currentThread().id, file)
        return file
    }

    fun shootPage(saveTo: String, imageName: String) {
        Shutterbug.shootPage(driver, WHOLE_PAGE, 500, true).withName(imageName).save(saveTo)
    }

    fun shootElement(saveTo: String, imageName: String, element: WebElement) {
        Shutterbug.shootElement(driver, element).withName(imageName).save(saveTo)
    }

    fun compareImage(expectedImage: BufferedImage, saveTo: String, deviation: Double) : Boolean {
        return Shutterbug.shootPage(driver, WHOLE_PAGE, 500, true).equalsWithDiff(expectedImage, saveTo, deviation)
    }

    override fun back(): Browser {
        driver.navigate().back()
        return this
    }

    override fun forward(): Browser {
        driver.navigate().forward()
        return this
    }

    override fun refresh(): Browser {
        driver.navigate().refresh()
        return this
    }

    fun scrollTo(element: FElement): FElement {
        js.execute(element.webElement) { "arguments[0].scrollIntoView();" }
        return element
    }

    /**
     * Supported log types
     * @see LogType
     */
    fun logs(logType: String): LogEntries {
        return driver.logs(logType)
    }

    override fun quit() {
        listener.beforeQuit()
        driver.quit()
    }

    val alert: Alert
        get() = driver.switchTo().alert()

    val title: String
        get() = driver.title

    val isAlive: Boolean
        get() = driver.isAlive()

    override fun toFrame(frame: FElement): Browser {
        driver.switchTo().frame(frame.webElement)
        return this
    }

    override fun toFrame(cssLocator: String): Browser {
        return toFrame(element(cssLocator))
    }
}
