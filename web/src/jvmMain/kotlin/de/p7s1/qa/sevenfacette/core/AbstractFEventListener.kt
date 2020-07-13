package de.p7s1.qa.sevenfacette.core

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

open class AbstractFEventListener : FEventListener {
    override fun beforeQuit() {}

    override fun onFail(exception: Exception) {}

    override fun afterNavigation(url: String, driver: WebDriver) {}

    override fun beforeElementLocation(by: By, driver: WebDriver) {}

    override fun beforeNavigation(url: String, driver: WebDriver) {}

    override fun onStart() {}
}
