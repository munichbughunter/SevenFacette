package de.p7s1.qa.sevenfacette.config

import org.openqa.selenium.WebDriver

/**
 * TODO: Add Description
 *
 * @author Patrick Döring
 */
class DriverConfig(
        override val autoQuit: Boolean = DriverConfigSetupSetup.Default.autoQuit,
        override val driverFactory: () -> WebDriver = DriverConfigSetupSetup.Default.driverFactory,
        override val waitForSleepTimeInMilliseconds: Long = DriverConfigSetupSetup.Default.waitForSleepTimeInMilliseconds,
        override val waitForTimeOutTimeInSeconds: Long = DriverConfigSetupSetup.Default.waitForTimeOutTimeInSeconds,
        val setups: Map<String, DriverConfigSetupSetup> = emptyMap()) : DriverConfigSetupSetup {



}

