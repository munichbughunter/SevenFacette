package de.p7s1.qa.sevenfacette.driver

import de.p7s1.qa.sevenfacette.conditions.ElementCondition
import de.p7s1.qa.sevenfacette.conditions.visible
import de.p7s1.qa.sevenfacette.extension.classes
import de.p7s1.qa.sevenfacette.locators.ElementLocator
import de.p7s1.qa.sevenfacette.locators.InnerListWebElementLocator
import de.p7s1.qa.sevenfacette.locators.InnerWebElementLocator
import de.p7s1.qa.sevenfacette.locators.WebElementLocator
import org.openqa.selenium.*
import org.openqa.selenium.interactions.Actions
import java.io.File


class FElement(locator: ElementLocator<WebElement>,
               driver: WebDriver) : Element<WebElement>(locator, driver) {

    constructor(locator: By, driver: WebDriver) :
            this(WebElementLocator(locator, driver), driver)

    val webElement: WebElement
        get() = locator.find()

    val text: String
        get() = webElement.text

    val tagName: String
        get() = webElement.tagName

    val isEnabled: Boolean
        get() = webElement.isEnabled

    val isDisplayed: Boolean
        get() = webElement.isDisplayed

    val isSelected: Boolean
        get() = webElement.isSelected

    val location: Point
        get() = webElement.location

    val classes: List<String>
        get() = webElement.classes

    fun attr(name: String): String {
        return webElement.getAttribute(name)
    }

    fun click() {
        execute { click() }
    }

    fun doubleClick() {
        val actions = Actions(driver)
        actions.doubleClick(webElement).perform()
    }

    fun clear() {
        execute { clear() }
    }

    fun sendKeys(vararg keysToSend: CharSequence) {
        execute { sendKeys(*keysToSend) }
    }

    fun uploadFile(name: String) {
        val resource = Thread.currentThread().contextClassLoader.getResource(name)
        this.setValue(File(resource.toURI()).canonicalPath)
    }

    fun pressEnter() {
        sendKeys(Keys.ENTER)
    }

    fun children(locator: String = "*"): FElementCollection {
        return this.all(locator)
    }

    fun firstChild(): FElement {
        return this.element(":first-child")
    }

    fun lastChild(): FElement {
        return this.element(":last-child")
    }

    fun parent(): FElement {
        return this.element(By.xpath(".."))
    }

    infix fun should(condition: ElementCondition) {
        super.should(condition)
    }

    infix fun shouldNot(condition: ElementCondition) {
        super.shouldNot(condition)
    }

    fun waitUntil(condition: ElementCondition, timeout: Int = this.waitTimeout): FElement {
        this.should(condition, timeout)
        return this
    }

    fun setValue(value: String): FElement {
        return execute {
            clear()
            sendKeys(value)
        }
    }

    fun execute(commands: WebElement.() -> Unit): FElement {
        super.shouldBe(visible)
        webElement.commands()
        return this
    }



    override fun toString(): String {
        return locator.description
    }

    fun element(byCss: String): FElement {
        return element(By.cssSelector(byCss))
    }

    fun element(by: By): FElement {
        return FElement(InnerWebElementLocator(by, this), driver)
    }

    fun all(byCss: String): FElementCollection {
        return all(By.cssSelector(byCss))
    }

    fun all(by: By): FElementCollection {
        return FElementCollection(InnerListWebElementLocator(by, this), driver)
    }
}
