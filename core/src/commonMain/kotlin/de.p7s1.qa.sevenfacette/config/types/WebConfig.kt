package de.p7s1.qa.sevenfacette.config.types

import kotlinx.serialization.Serializable

@Serializable
data class WebConfig(
        var timeout: Int = 0,
        var startMaximized: Boolean = true,
        var autoClose: Boolean = true,
        var screenSize: List<Int> = listOf(1920, 1080),
        var pollingInterval: Double = 0.1,
        var baseUrl: String? = null,
        var remoteUrl: String = "",
        var browserName: String = "chrome",
        val capabilities: Map<String, String>? = null
)
