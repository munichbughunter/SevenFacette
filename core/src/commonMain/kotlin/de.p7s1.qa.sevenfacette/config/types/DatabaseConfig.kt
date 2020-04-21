package de.p7s1.qa.sevenfacette.config.types

import kotlinx.serialization.Serializable

@Serializable
data class DatabaseConfig(
        val name: String,
        val url: String,
        val driver: String
)
