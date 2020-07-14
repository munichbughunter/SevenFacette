package de.p7s1.qa.sevenfacette.driver

import de.p7s1.qa.sevenfacette.config.types.FacetteConfig
import de.p7s1.qa.sevenfacette.core.PageClass
//import de.p7s1.qa.sevenfacette.core.configuration
import de.p7s1.qa.sevenfacette.core.getDriver
import org.openqa.selenium.WebDriver

class FDriver {

    companion object {

        @JvmStatic
        val browser
            get() = Browser()

        @JvmStatic
        fun open(url: String) {
            browser.to(url)
        }

        @JvmStatic
        fun <T : Page> open(pageClass: PageClass<T>): T {
            return browser.to(pageClass)
        }

        @JvmStatic
        fun <T : Page> open(pageClass: PageClass<T>, block: T.() -> Unit) {
            open(pageClass).block()
        }

        @JvmStatic
        fun <T : Page> at(pageClass: PageClass<T>): T {
            return pageClass(browser)
        }

        @JvmStatic
        fun <T : Page> at(pageClass: PageClass<T>, block: T.() -> Unit) {
            at(pageClass).block()
        }

        @JvmStatic
        fun closeBrowser(){
            browser.quit()
        }

        fun drive(driver: WebDriver = getDriver(), block: Browser.() -> Unit): Browser {
            val browser = Browser(driver).apply { FacetteConfig.web!! }
            browser.block()
            return browser
        }
    }
}
