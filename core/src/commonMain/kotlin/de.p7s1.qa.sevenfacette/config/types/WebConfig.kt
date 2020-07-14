package de.p7s1.qa.sevenfacette.config.types

import kotlinx.serialization.Serializable

/**
 * For use <headless> chrome options, set property "facette.startMaximized=false"
 * var startMaximized: Boolean = true,
 * */
/**
 * Chrome binary property example: -Dfacette.chrome.bin="path/to/your/chrome/bin"
 * */
/**
 * Highlight Element style
 * */
@Serializable
data class WebConfig(
        var autoClose: Boolean = true,
        var baseUrl: String = "",
        var browserName: String = "chrome",
        var capabilities: List<String>? = null,
        var chromeArgs: List<String> = listOf("--no-sandbox"),
        var chromeBin: String = "",
        var highlightBorder: Boolean = true,
        var highlightColor: String = "",
        var highlightSize: String = "",
        var highlightStyle: String = "",
        var listenerClass: String = "",
        var pollingInterval: Double = 0.1,
        var remoteUrl: String = "",
        var reportDir: String = "",
        var screenSize: List<Int> = listOf(1920, 1080),
        var startMaximized: Boolean = true,
        var timeout: Int = 4000
)
