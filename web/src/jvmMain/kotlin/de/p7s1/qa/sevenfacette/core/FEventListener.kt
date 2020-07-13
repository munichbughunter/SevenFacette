package de.p7s1.qa.sevenfacette.core

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

interface FEventListener {
    fun onStart()
    fun beforeQuit()
    fun beforeNavigation(url: String, driver: WebDriver)
    fun afterNavigation(url: String, driver: WebDriver)
    fun beforeElementLocation(by: By, driver: WebDriver)
    fun onFail(exception: Exception)
}
