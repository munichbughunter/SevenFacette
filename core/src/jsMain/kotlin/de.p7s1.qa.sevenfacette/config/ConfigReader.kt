package de.p7s1.qa.sevenfacette.config

import de.p7s1.qa.sevenfacette.http.config.authenticationModule
import kotlinx.serialization.json.Json
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}
actual class ConfigReader {

    actual fun readConfig(): FacetteConfig {
        val config = getConfigFile()
        var result = FacetteConfig()

        if(config != null) {
            result = Json(context = authenticationModule)
                    .parse(
                            FacetteConfig.serializer(),
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
