package de.p7s1.qa.sevenfacette.config

import de.p7s1.qa.sevenfacette.config.types.SevenFacetteConfig

/**
 * TODO: Add Description
 *
 * @author Patrick Döring
 */
expect class ConfigReader {
    fun readConfig(): SevenFacetteConfig
}
