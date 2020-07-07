package de.p7s1.qa.sevenfacette.config

import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver

const val DEFAULT_SLEEP_TIME_IN_MILLISECONDS = 1000L
const val DEFAULT_TIME_OUT_TIME_IN_SECONDS = 10L

/**
 * TODO: Add Description
 *
 * @author Patrick Döring
 */
interface DriverConfigSetupSetup {
    val autoQuit: Boolean
    val driverFactory: () -> WebDriver
    val waitForSleepTimeInMilliseconds: Long
    val waitForTimeOutTimeInSeconds: Long

    companion object {
        internal val Default = DriverConfig(
                true,
                ::ChromeDriver,
                DEFAULT_SLEEP_TIME_IN_MILLISECONDS,
                DEFAULT_TIME_OUT_TIME_IN_SECONDS)
    }
}
