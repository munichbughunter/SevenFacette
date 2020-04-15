package de.p7s1.qa.sevenfacette.config

import com.charleskorn.kaml.Yaml
import de.p7s1.qa.sevenfacette.http.config.authenticationModule
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}
actual class ConfigReader {
    private val IMPORT_REGEX = Regex("@[Ii]mport\\([\\-\\w]+.(yml|yaml|json)\\)")

    actual fun readConfig(): FacetteConfig {
        val config = replaceImports(getConfigFileName().toString())
        var result = FacetteConfig()
        if(config != "") {
            result = Yaml(context = authenticationModule).parse(FacetteConfig.serializer(), config)
        }
        return result
    }

    /**
     * This function uses the env variable provided by the user for the config file or a default file
     */
    private fun getConfigFileName(): String? {
        return if(!System.getenv("FACETTE_CONFIG").isNullOrEmpty()) {
            logger.info("Use environment variable ${System.getenv("FACETTE_CONFIG")} for configuration")
            System.getenv("FACETTE_CONFIG")
        } else if(!System.getProperty("FACETTE_CONFIG").isNullOrEmpty()) {
            logger.info("Use system property ${System.getenv("FACETTE_CONFIG")} for configuration")
            System.getProperty("FACETTE_CONFIG")
        } else if(javaClass.classLoader.getResource("facetteConfig.yml") != null) {
            logger.info("Use facetteConfig.yml for configuration")
            "facetteConfig.yml"
        } else if(javaClass.classLoader.getResource("facetteConfig.yaml") != null) {
            logger.info("Use facetteConfig.yml for configuration")
            "facetteConfig.yaml"
        } else {
            logger.error("No configuration file found")
            null
        }
    }

    /**
     * Reads the content of a file and returns it as string
     *
     * @param fileName URL of file which text should be returned
     * @return string
     */
    private fun readFile(fileName: String): String? = javaClass.classLoader.getResource(fileName)?.readText()

    /**
     * String extension that removes the last character. If the files are read a trailing break is added which adds empty lines to the config file.
     *
     * @return string
     */
    private fun String.removeTrailingBreak(): String = this.substring(0, this.length - 1)

    /**
     * Extracts the filename of the "@Import(filename)" placeholder
     *
     * @param text Import-placeholder
     * @return String filename included in placeholder
     */
    private fun extractFileName(text: String) = text.replace("@import(", "", true).replace(")", "")

    /**
     * Recursive function which searches for import-placeholders and replaces it with the corresponding file content.
     *
     * @param fileName Start filename
     * @return String Merged content of all files
     */
    private fun replaceImports(fileName: String): String {
        var ymlContent = readFile(fileName)?.removeTrailingBreak() ?: ""
        val imports = IMPORT_REGEX.findAll(ymlContent)
        if(imports.count() == 0) return ymlContent

        imports.forEach {
            val newFile = extractFileName(it.groupValues[0])
            val newFileContent = readFile(newFile)?.removeTrailingBreak() ?: ""

            ymlContent = if(newFileContent.contains(IMPORT_REGEX)) {
                ymlContent.replace(it.groupValues[0], replaceImports(newFile), true)
            } else {
                ymlContent.replace(it.groupValues[0], newFileContent, true)
            }
        }
        return ymlContent
    }
}
