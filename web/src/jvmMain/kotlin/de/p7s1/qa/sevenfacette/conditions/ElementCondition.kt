package de.p7s1.qa.sevenfacette.conditions

import de.p7s1.qa.sevenfacette.extension.classes
import org.openqa.selenium.WebElement

abstract class ElementCondition : BaseCondition<WebElement>()

class Text(val text: String) : ElementCondition() {

    override fun matches(item: WebElement): Boolean {
        return item.text == text
    }

    override fun description(item: WebElement): Description {
        return Description(item.text, text)
    }
}

class Visibility : ElementCondition() {
    override fun matches(item: WebElement): Boolean {
        return item.isDisplayed
    }

    override fun description(item: WebElement): Description {
        return Description("invisible", "visible", diff = false)
    }
}

class AttributeValue(val attr: String, val expect: String) : ElementCondition() {
    override fun matches(item: WebElement): Boolean {
        return item.getAttribute(attr) == expect
    }

    override fun description(item: WebElement): Description {
        return Description(item.getAttribute(attr), expect)
    }

    override fun toString(): String {
        return "attribute {$attr}"
    }
}

class CssClassValue(val cssClass: String) : ElementCondition() {
    override fun matches(item: WebElement): Boolean {
        return item.classes.contains(cssClass)
    }

    override fun description(item: WebElement): Description {
        return Description(item.classes, cssClass, diff = false)
    }

    override fun toString(): String {
        return "css class value {$cssClass}"
    }
}

class Clickable : ElementCondition() {
    override fun description(item: WebElement): Description {
        return Description("not clickable", "clickable", diff = false)
    }

    override fun matches(item: WebElement): Boolean {
        item.click()
        return true
    }
}
