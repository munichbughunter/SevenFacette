package de.p7s1.qa.sevenfacette.config

import de.p7s1.qa.sevenfacette.utils.Files
import de.p7s1.qa.sevenfacette.utils.KSystem
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

val IMPORT_REGEX = Regex("@[Ii]mport\\([-_\\w]+.(yml|yaml|json)\\)")
val SYSTEM_PROP_REGEX = Regex("\\[\\[[-_\\w|\\s]+\\]\\]")

/**
 * String extension that removes the last character. If the files are read a trailing break is added which adds empty lines to the config file.
 *
 * @return string
 */
fun String.removeTrailingBreak(): String = this.substring(0, this.length - 1)

/**
 * Extracts the filename of the "@Import(filename)" placeholder
 *
 * @param text Import-placeholder
 * @return String filename included in placeholder
 */
fun extractFileName(text: String) = text.replace("@import(", "", true).replace(")", "")

fun replaceEnvironmentVariables(origin: String): String {
    var result = origin

    SYSTEM_PROP_REGEX.findAll(origin).forEach {
        val envVarName = extractEnvVarName(it.groupValues[0])
        val replace = if(!KSystem.getProperty(envVarName[0]).isNullOrEmpty()) {
            KSystem.getProperty(envVarName[0]) ?: ""
        } else if (!KSystem.getEnv(envVarName[0]).isNullOrEmpty()) {
            KSystem.getEnv(envVarName[0]) ?: ""
        } else if(envVarName.size == 2) {
            logger.info { "No value found for environment variable ${envVarName[0]}. Using fallback value!" }
            envVarName[1]
        } else {
            logger.error { "No value found for environment variable ${envVarName[0]}" }
            ""
        }
        result = result.replace(it.groupValues[0], replace)
    }
    return result
}

fun extractEnvVarName(text: String): List<String> = text
        .replace("[[", "")
        .replace("]]", "")
        .split("||")
        .map { it.trim() }

/**
 * Recursive function which searches for import-placeholders and replaces it with the corresponding file content.
 *
 * @param fileName Start filename
 * @return String Merged content of all files
 */
fun replaceImports(fileName: String): String {
    var configContent = Files.getRessourceText(fileName)?.removeTrailingBreak() ?: ""
    val imports = IMPORT_REGEX.findAll(configContent)
    if(imports.count() == 0) return configContent

    imports.forEach {
        val newFile = extractFileName(it.groupValues[0])
        val newFileContent = Files.getRessourceText(newFile)?.removeTrailingBreak() ?: ""

        configContent = if(newFileContent.contains(IMPORT_REGEX)) {
            configContent.replace(it.groupValues[0], replaceImports(newFile), true)
        } else {
            configContent.replace(it.groupValues[0], newFileContent, true)
        }
    }
    return configContent
}
