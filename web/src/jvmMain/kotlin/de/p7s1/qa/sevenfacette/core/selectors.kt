package de.p7s1.qa.sevenfacette.core

import org.openqa.selenium.By

fun byXpath(xpath: String): By {
    return By.xpath(xpath)
}

val XPATH_EXPRESSION_PATTERN = Regex(".*\\/\\/.*\$")

fun isXpath(locator: String): Boolean {
    return XPATH_EXPRESSION_PATTERN.matches(locator)
}
