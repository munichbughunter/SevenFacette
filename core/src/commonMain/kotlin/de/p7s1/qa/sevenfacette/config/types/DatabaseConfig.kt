package de.p7s1.qa.sevenfacette.config.types

import kotlinx.serialization.Serializable

@Serializable
data class DatabaseConfig(
        val url: String,
        val driver: String,
        val user: String? = null,
        val password: String? = null,
        @Deprecated(message = "This property will be deleted in version 2.0.0")
        var autoCommit: Boolean = true,
        var stopOnError: Boolean = false
)
