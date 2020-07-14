package de.p7s1.qa.sevenfacette.core


import de.p7s1.qa.sevenfacette.driver.FElement
import de.p7s1.qa.sevenfacette.driver.FElementCollection
import org.openqa.selenium.By

interface SearchContext {

    fun element(cssLocator: String) = element(By.cssSelector(cssLocator))

    fun element(by: By): FElement

    fun all(cssLocator: String) = all(By.cssSelector(cssLocator))

    fun all(by: By): FElementCollection
}
