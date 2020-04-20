package de.p7s1.qa.sevenfacette.config

import kotlinx.serialization.json.Json
import mu.KotlinLogging

/**
 * Actual implementation of the Config reader class.
 * This class reads the configuration json and mapps them to the FacetteConfigDataClass
 */
private val logger = KotlinLogging.logger {}
actual class ConfigReader {

    actual fun readConfig(): FacetteConfigDataClass {
        val config = getConfigFile()
        var result = FacetteConfigDataClass()

        if(config != null) {
            result = Json
                    .parse(
                            FacetteConfigDataClass.serializer(),
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
