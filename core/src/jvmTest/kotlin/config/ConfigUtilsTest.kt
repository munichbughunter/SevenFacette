package config

import de.p7s1.qa.sevenfacette.config.replaceEnvironmentVariables
import de.p7s1.qa.sevenfacette.config.types.FacetteConfig
import org.junit.Before
import org.junit.Test

class ConfigUtilsTest {

    @Before
    fun setConfig() {
        FacetteConfig.update()
    }

    // No property found, no fallback
    @Test
    fun checkReplaceNoReplace() {
        System.clearProperty("world")
        val text = "Hello [[world]]"
        val result = replaceEnvironmentVariables(text)
        println(result)
        assert(result == "Hello ") // no replacement
    }

    // Property found
    @Test
    fun checkReplacementWithSystemProperty() {
        System.setProperty("world", "Test User")
        val text = "Hello [[world]]"
        val result = replaceEnvironmentVariables(text)
        assert(result == "Hello Test User")
    }

    // Property not found, replacement available
    @Test
    fun checkReplacementWithFallback() {
        System.clearProperty("world")
        val text = "Hello [[world || Fallback User]]"
        val result = replaceEnvironmentVariables(text)
        assert(result == "Hello Fallback User")
    }
}
