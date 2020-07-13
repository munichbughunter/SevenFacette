package de.p7s1.qa.sevenfacette.core

//import com.automation.remarks.kirk.Browser
//import com.automation.remarks.kirk.KElement
//import com.automation.remarks.kirk.Page

import de.p7s1.qa.sevenfacette.driver.Browser
import de.p7s1.qa.sevenfacette.driver.FElement
import de.p7s1.qa.sevenfacette.driver.Page

interface Navigable {

    fun open(url: String)

    fun <T : Page> to(pageClass: PageClass<T>): T

    fun <T : Page> to(pageClass: PageClass<T>, block: T.() -> Unit) {
        val page = to(pageClass)
        page.block()
    }

    fun <T : Page> at(pageClass: PageClass<T>, closure: T.() -> Unit = {}): T

    fun back(): Browser

    fun forward(): Browser

    fun refresh(): Browser

    fun quit()

    fun toFrame(cssLocator: String): Browser

    fun toFrame(frame: FElement): Browser
}
