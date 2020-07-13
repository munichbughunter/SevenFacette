package de.p7s1.qa.sevenfacette.config

import org.aeonbits.owner.Config
import org.aeonbits.owner.Config.*
import java.io.File

@Sources("classpath:facette.properties")
interface FConfig : Config {

    @Key("facette.browser")
    @DefaultValue("chrome")
    fun browserName(): String

    @Key("facette.timeout")
    @DefaultValue("4000")
    fun timeout(): Int

    @Key("facette.poolingInterval")
    @DefaultValue("0.1")
    fun poolingInterval(): Double

    @Key("facette.autoClose")
    @DefaultValue("true")
    fun autoClose(): Boolean

    @DefaultValue("")
    @Key("facette.screenSize")
    fun screenSize(): List<Int>

    @Key("facette.baseUrl")
    fun baseUrl(): String?

    /**
     * For use <headless> chrome options, set property "facette.startMaximized=false"
     * */
    @Separator(",")
    @DefaultValue("")
    @Key("facette.chrome.args")
    fun chromeArgs(): List<String>

    /**
     * Chrome binary property example: -Dfacette.chrome.bin="path/to/your/chrome/bin"
     * */
    @Key("facette.chrome.binary")
    fun chromeBin(): String

    @Separator(",")
    @DefaultValue("")
    @Key("facette.chrome.extensions")
    fun chromeExtensions(): List<File>

    @Key("facette.report.dir")
    fun reportDir(): String

    /**
     * Highlight Element style
     * */
    @Key("facette.highlight.border")
    @DefaultValue("true")
    fun highlightBorder(): Boolean

    @Key("facette.highlight.style")
    @DefaultValue("dotted")
    fun highlightStyle(): String

    @Key("facette.highlight.size")
    @DefaultValue("2px")
    fun highlightSize(): String

    @Key("facette.highlight.color")
    @DefaultValue("red")
    fun highlightColor(): String

    @Separator(",")
    @DefaultValue("")
    @Key("facette.desired.capabilities")
    fun capabilities(): List<String>

    @Key("facette.remote.url")
    @DefaultValue("")
    fun remoteUrl(): String

    @Key("facette.listener")
    @DefaultValue("")
    fun listenerClass(): String
}
