package de.p7s1.qa.sevenfacette.config.types

import kotlinx.serialization.Serializable

@Serializable
data class ApplicationConfig(
        val resources: List<ResourceConfig>
)