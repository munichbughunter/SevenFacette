package de.p7s1.qa.sevenfacette.config

import de.p7s1.qa.sevenfacette.config.types.SevenFacetteConfig
import kotlinx.serialization.json.Json
import mu.KotlinLogging

/**
 * Actual implementation of the Config reader class.
 * This class reads the configuration json and mapps them to the FacetteConfigDataClass
 */
private val logger = KotlinLogging.logger {}
actual class ConfigReader {

    actual fun readConfig(): SevenFacetteConfig {
        val config = getConfigFile()
        var result = SevenFacetteConfig()

        if(config != null) {
            result = Json
                    .parse(
                            SevenFacetteConfig.serializer(),
                            config
                    )
        } else {
            logger.error {"No configuration file found"}
        }
        return result
    }

    // This needs to be adopted to Javascript
    private fun getConfigFile(): String? {
        return null
    }
}
