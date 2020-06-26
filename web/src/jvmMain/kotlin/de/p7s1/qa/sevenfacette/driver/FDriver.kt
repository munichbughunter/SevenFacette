package de.p7s1.qa.sevenfacette.driver

import org.openqa.selenium.*
import org.openqa.selenium.WebDriver.*
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.interactions.HasInputDevices
import org.openqa.selenium.interactions.Interactive
import org.openqa.selenium.interactions.Keyboard
import org.openqa.selenium.interactions.Mouse
import org.openqa.selenium.internal.*
import org.openqa.selenium.internal.WrapsDriver
import org.openqa.selenium.remote.RemoteWebDriver
import java.util.stream.Collectors


/**
 * TODO: Add Description
 *
 * @author Patrick Döring
 */
class FDriver(private val wrappedDriver: RemoteWebDriver) : WebDriver, JavascriptExecutor, FindsById, FindsByClassName, FindsByLinkText, FindsByName, FindsByCssSelector, FindsByTagName, FindsByXPath, HasInputDevices, HasCapabilities, Interactive, TakesScreenshot, WrapsDriver {

    //private val lastExpectedState: RootElement? = null
    //private val lastActualState: RootElement? = null
    //private val warningConsumer: Consumer<QualifiedElementWarning>? = null
    /*
    override fun findElement(by: ByBestMatchToRetestId): WebElement {
        checkNotNull(lastExpectedState) {
            ("You must use the " + RecheckWebImpl::class.java.getSimpleName()
                    + " and first check the state before being able to use the retest ID locator.")
        }
        val searchedFor: Element = by.findElement(lastExpectedState, lastActualState)
        val element = wrappedDriver.findElement(By.xpath(searchedFor.getIdentifyingAttributes().getPath()))
        return wrap(element)
    }

     */

    // ToDo: Refactor
    override fun findElement(webElement: By?): WebElement? {
        return wrappedDriver.findElement(webElement)
    }


    protected fun wrap(element: WebElement?): WebElement {
        return WrappingWebElement.wrap(this, element)
    }

    private fun wrap(elements: List<WebElement>): List<WebElement> {
        return elements.stream() //
                .map { element: WebElement? -> this.wrap(element) } //
                .collect(Collectors.toList())
    }


/*
    fun findElementByRetestId(retestId: String?): WebElement {
        return findElement(ByBestMatchToRetestId(retestId))
    }

    override fun findElement(by: By?): WebElement {
        return if (by is ByBestMatchToRetestId) {
            findElement(by as ByBestMatchToRetestId?)
        } else try {
            wrap(wrappedDriver.findElement(by))
        } catch (e: NoSuchElementException) {
            val matchedOld: WebElement = TestHealer.findElement(by, this) ?: throw e
            wrap(matchedOld)
        }
    }


 */
    override fun findElements(by: By?): List<WebElement> {
        return wrap(wrappedDriver.findElements(by))
    }

    override fun findElementById(using: String?): WebElement {
        return wrap(wrappedDriver.findElementById(using))
    }

    override fun findElementsById(using: String?): List<WebElement> {
        return wrap(wrappedDriver.findElementsById(using))
    }

    override fun findElementByClassName(using: String?): WebElement {
        return wrap(wrappedDriver.findElementByClassName(using))
    }

    override fun findElementsByClassName(using: String?): List<WebElement> {
        return wrap(wrappedDriver.findElementsByClassName(using))
    }

    override fun findElementByLinkText(using: String?): WebElement {
        return wrap(wrappedDriver.findElementByLinkText(using))
    }

    override fun findElementsByLinkText(using: String?): List<WebElement> {
        return wrap(wrappedDriver.findElementsByLinkText(using))
    }

    override fun findElementByPartialLinkText(using: String?): WebElement {
        return wrap(wrappedDriver.findElementByPartialLinkText(using))
    }

    override fun findElementsByPartialLinkText(using: String?): List<WebElement> {
        return wrap(wrappedDriver.findElementsByPartialLinkText(using))
    }

    override fun findElementByName(using: String?): WebElement {
        return wrap(wrappedDriver.findElementByName(using))
    }

    override fun findElementsByName(using: String?): List<WebElement> {
        return wrap(wrappedDriver.findElementsByName(using))
    }

    override fun findElementByCssSelector(using: String?): WebElement {
        return wrap(wrappedDriver.findElementByCssSelector(using))
    }

    override fun findElementsByCssSelector(using: String?): List<WebElement> {
        return wrap(wrappedDriver.findElementsByCssSelector(using))
    }

    override fun findElementByTagName(using: String?): WebElement {
        return wrap(wrappedDriver.findElementByTagName(using))
    }

    override fun findElementsByTagName(using: String?): List<WebElement> {
        return wrap(wrappedDriver.findElementsByTagName(using))
    }

    override fun findElementByXPath(using: String?): WebElement {
        return wrap(wrappedDriver.findElementByXPath(using))
    }

    override fun findElementsByXPath(using: String?): List<WebElement> {
        return wrap(wrappedDriver.findElementsByXPath(using))
    }

    override fun close() {
        wrappedDriver.close()
    }

    override fun get(url: String?) {
        wrappedDriver[url]
    }

    override fun getCurrentUrl(): String {
        return wrappedDriver.currentUrl
    }

    override fun getPageSource(): String {
        return wrappedDriver.pageSource
    }

    override fun getTitle(): String {
        return wrappedDriver.title
    }

    override fun getWindowHandle(): String {
        return wrappedDriver.windowHandle
    }

    override fun getWindowHandles(): Set<String> {
        return wrappedDriver.windowHandles
    }

    override fun manage(): Options {
        return wrappedDriver.manage()
    }

    override fun navigate(): Navigation {
        return wrappedDriver.navigate()
    }

    override fun quit() {
        wrappedDriver.quit()
    }

    override fun switchTo(): TargetLocator {
        return wrappedDriver.switchTo()
    }

    override fun executeScript(script: String?, vararg args: Any?): Any {
        return wrappedDriver.executeScript(script, *args)
    }

    override fun executeAsyncScript(script: String?, vararg args: Any?): Any {
        return wrappedDriver.executeAsyncScript(script, *args)
    }

    override fun <X> getScreenshotAs(target: OutputType<X>?): X {
        return wrappedDriver.getScreenshotAs(target)
    }

    override fun getKeyboard(): Keyboard {
        return wrappedDriver.keyboard
    }

    override fun getMouse(): Mouse {
        return wrappedDriver.mouse
    }

    override fun getCapabilities(): Capabilities {
        return wrappedDriver.capabilities
    }

    override fun perform(actions: Collection<Sequence?>?) {
        wrappedDriver.perform(actions)
    }

    override fun resetInputState() {
        wrappedDriver.resetInputState()
    }

    override fun getWrappedDriver(): WebDriver? {
        return wrappedDriver
        /*
        return if (SeleniumWrapperUtil.isWrapper(WrapperOf.DRIVER, wrappedDriver)) {
            SeleniumWrapperUtil.getWrapped(WrapperOf.DRIVER, wrappedDriver)
        } else wrappedDriver

         */
    }

    /**
     * Skip checks for actions performed on this web driver. Alias for [.getWrappedDriver].
     *
     * @return the [WebDriver] wrapped by this instance
     */
    fun skipCheck(): WebDriver? {
        return getWrappedDriver()
    }

}
