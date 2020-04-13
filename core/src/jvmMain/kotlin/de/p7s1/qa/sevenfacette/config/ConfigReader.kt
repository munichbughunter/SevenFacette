package de.p7s1.qa.sevenfacette.config

import com.charleskorn.kaml.Yaml
import de.p7s1.qa.sevenfacette.http.config.authenticationModule
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}
actual class ConfigReader {
    actual fun readConfig(): FacetteConfig {
        val config = getConfigFile()
        var result = FacetteConfig()

        if(config != null) {
            result = Yaml(context = authenticationModule)
                    .parse(
                            FacetteConfig.serializer(),
                            config
                    )
        }
        return result
    }

    /**
     * This function uses the env variable provided by the user for the config file or a default file
     */
    private fun getConfigFile(): String? {
        return if(!System.getenv("FACETTE_CONFIG").isNullOrEmpty()) {
            logger.info("Use environment variable ${System.getenv("FACETTE_CONFIG")} for configuration")
            javaClass.classLoader.getResource(System.getenv("FACETTE_CONFIG"))?.readText()
        } else if(javaClass.classLoader.getResource("facetteConfig.yml") != null) {
            logger.info("Use facetteConfig.yml for configuration")
            javaClass.classLoader.getResource("facetteConfig.yml")?.readText()
        } else if(javaClass.classLoader.getResource("facetteConfig.yaml") != null) {
            logger.info("Use facetteConfig.yml for configuration")
            javaClass.classLoader.getResource("facetteConfig.yaml")?.readText()
        } else {
            logger.error("No configuration file found")
            ""
        }
    }
}
