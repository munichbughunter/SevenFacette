package de.p7s1.qa.sevenfacette.config

import org.openqa.selenium.WebDriver

/**
 * TODO: Add Description
 *
 * @author Patrick Döring
 */
open class ConfigurationSetupBuilder {

    var autoQuit: Boolean = ConfigurationSetup.Default.autoQuit

    var driverFactory: () -> WebDriver = ConfigurationSetup.Default.driverFactory

    var waitForSleepTimeInMilliseconds: Long = ConfigurationSetup.Default.waitForSleepTimeInMilliseconds

    var waitForTimeOutTimeInSeconds: Long = ConfigurationSetup.Default.waitForTimeOutTimeInSeconds

    /**
     * Creates a new configuration setup.
     *
     * @return a new configuration setup using the options provided to the builder.
     */
    open fun build(): ConfigurationSetup = Configuration(
            autoQuit, driverFactory, waitForSleepTimeInMilliseconds, waitForTimeOutTimeInSeconds)
}
