package de.p7s1.qa.sevenfacette.config

/**
 * TODO: Add Description
 *
 * @author Patrick Döring
 */
class ConfigurationBuilder : ConfigurationSetupBuilder() {
    var setups: Map<String, ConfigurationSetup> = mapOf()

    fun setup(block: ConfigurationSetupBuilder.() -> Unit): ConfigurationSetup = ConfigurationSetupBuilder().apply {
        block()
    }.build()

    override fun build(): Configuration = Configuration(
            autoQuit, driverFactory, waitForSleepTimeInMilliseconds, waitForTimeOutTimeInSeconds, setups)
}
