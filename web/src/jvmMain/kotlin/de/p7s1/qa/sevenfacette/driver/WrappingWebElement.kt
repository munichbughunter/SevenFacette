package de.p7s1.qa.sevenfacette.driver

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsDriver;
import org.openqa.selenium.WrapsElement;
import java.util.stream.Collectors

/**
 * TODO: Add Description
 *
 * @author Patrick Döring
 */
internal class WrappingWebElement(private val driver: WebDriver?, private val element: WebElement?) : WebElement, WrapsDriver, WrapsElement {



    override fun getWrappedDriver(): WebDriver? {
        return driver
    }

    override fun getWrappedElement(): WebElement? {
        return element
    }

    override fun click() {
        element!!.click()
    }

    override fun submit() {
        element!!.submit()
    }

    override fun sendKeys(vararg keysToSend: CharSequence?) {
        element!!.sendKeys(*keysToSend)
    }

    override fun clear() {
        element!!.clear()
    }

    override fun getTagName(): String {
        return element!!.tagName
    }

    override fun getAttribute(name: String?): String {
        return element!!.getAttribute(name)
    }

    override fun isSelected(): Boolean {
        return element!!.isSelected
    }

    override fun isEnabled(): Boolean {
        return element!!.isEnabled
    }

    override fun getText(): String {
        return element!!.text
    }

    override fun findElements(by: By?): List<WebElement> {
        return element!!.findElements(by).stream() //
                .map { element: WebElement? -> WrappingWebElement(driver, element) } //
                .collect(Collectors.toList())
    }

    override fun findElement(by: By?): WebElement {
        return WrappingWebElement(driver, element!!.findElement(by))
    }

    override fun isDisplayed(): Boolean {
        return element!!.isDisplayed
    }

    override fun getLocation(): Point {
        return element!!.location
    }

    override fun getSize(): Dimension {
        return element!!.size
    }

    override fun getRect(): Rectangle {
        return element!!.rect
    }

    override fun getCssValue(propertyName: String?): String {
        return element!!.getCssValue(propertyName)
    }

    @Throws(WebDriverException::class)
    override fun <X> getScreenshotAs(target: OutputType<X>): X {
        return element!!.getScreenshotAs(target)
    }

    override fun toString(): String {
        return element.toString()
    }

    companion object {
        fun wrap(driver: WebDriver?, element: WebElement?): WebElement {
            return WrappingWebElement(driver, element)
        }
    }
}
