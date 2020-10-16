package de.p7s1.qa.sevenfacette.config.types

import kotlinx.serialization.Serializable

/**
 * Overall type for configurations. The config yaml files will be mapped to this type.
 * It enables the "sevenFacette"-key  in the config-file.
 */
@Serializable
data class SevenFacetteConfig(val sevenFacette: FacetteConfigDataClass? = null)
