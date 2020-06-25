package de.p7s1.qa.sevenfacette.config

import org.openqa.selenium.WebDriver

/**
 * TODO: Add Description
 *
 * @author Patrick Döring
 */
data class Configuration(
        override val autoQuit: Boolean = ConfigurationSetup.Default.autoQuit,
        override val driverFactory: () -> WebDriver = ConfigurationSetup.Default.driverFactory,
        override val waitForSleepTimeInMilliseconds: Long = ConfigurationSetup.Default.waitForSleepTimeInMilliseconds,
        override val waitForTimeOutTimeInSeconds: Long = ConfigurationSetup.Default.waitForTimeOutTimeInSeconds,
        val setups: Map<String, ConfigurationSetup> = emptyMap()) : ConfigurationSetup
