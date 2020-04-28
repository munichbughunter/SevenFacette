package de.p7s1.qa.sevenfacette.config

import com.charleskorn.kaml.Yaml
import de.p7s1.qa.sevenfacette.config.types.SevenFacetteConfig
import mu.KotlinLogging

/**
 * Class to read the config yaml file(s).
 * If an environment variable or system property FACETTE_CONFIG is provided this file will be used.
 * The configuration yaml in the resource root folder will be used.
 *
 * @author Florian Pilz
 */
private val logger = KotlinLogging.logger {}
actual class ConfigReader {

    /**
     * Reads the configuration.
     *
     * @return FacetteConfigDataClass
     */
    actual fun readConfig(): SevenFacetteConfig {
        val config = replaceEnvironmentVariables(replaceImports(getConfigFileName().toString()))
        var result = SevenFacetteConfig()
        if(config != "") {
            result = Yaml().parse(SevenFacetteConfig.serializer(), config)
        }
        return result
    }
}
