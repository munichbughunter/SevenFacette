package de.p7s1.qa.sevenfacette.config

import de.p7s1.qa.sevenfacette.config.types.SevenFacetteConfig

/**
 * Class to read the config yaml or json file.
 *
 * @return mapped FacetteConfigDataClass
 *
 * @author Florian Pilz
 */
expect class ConfigReader {
    fun readConfig(): SevenFacetteConfig
}
