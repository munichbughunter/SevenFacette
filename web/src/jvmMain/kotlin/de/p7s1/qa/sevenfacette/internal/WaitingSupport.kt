package de.p7s1.qa.sevenfacette.internal

import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedCondition
import org.openqa.selenium.support.ui.WebDriverWait


/**
 * TODO: Add Description
 *
 * @author Patrick Döring
 */
interface WaitingSupport {
    val driver: WebDriver

    val configurationSetup : ConfigurationSetup

    fun <T> waitFor(timeOutInSeconds: Long = configurationSetup.waitForTimeOutTimeInSeconds,
                    sleepInMillis: Long = configurationSetup.waitForSleepTimeInMilliseconds,
                    isTrue: () -> ExpectedCondition<T>): T {
        return WebDriverWait(driver, timeOutInSeconds, sleepInMillis).until(isTrue())
    }
}
