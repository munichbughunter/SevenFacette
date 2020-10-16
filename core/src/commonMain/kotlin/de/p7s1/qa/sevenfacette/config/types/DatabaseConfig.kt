package de.p7s1.qa.sevenfacette.config.types

import kotlinx.serialization.Serializable

@Serializable
data class DatabaseConfig(
        val url: String,
        val driver: String,
        val user: String? = null,
        val password: String? = null,
        var autoCommit: Boolean = true,
        var stopOnError: Boolean = false
)