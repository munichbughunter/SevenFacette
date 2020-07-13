package de.p7s1.qa.sevenfacette.config.types

import kotlinx.serialization.Serializable

@Serializable
data class WebConfig(
        var browserName: String = "chrome",
        var timeout: Int = 4000,
        var pollingInterval: Double = 0.1,
        var autoClose: Boolean = true,
        var screenSize: List<Int> = listOf(1920, 1080),
        var baseUrl: String = "",
        /**
         * For use <headless> chrome options, set property "facette.startMaximized=false"
         * var startMaximized: Boolean = true,
         * */
        var chromeArgs: List<String>? = null,

        /**
         * Chrome binary property example: -Dfacette.chrome.bin="path/to/your/chrome/bin"
         * */
        var chromeBin: String = "",
        var reportDir: String = "",

        /**
         * Highlight Element style
         * */
        var highlightBorder: Boolean = true,
        var highlightStyle: String = "",
        var highlightSize: String = "",
        var highlightColor: String = "",
        var capabilities: List<String>? = null,
        var remoteUrl: String = "",
        var listenerClass: String = ""
)
