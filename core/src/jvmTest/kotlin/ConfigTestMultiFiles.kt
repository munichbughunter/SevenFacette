import de.p7s1.qa.sevenfacette.config.ConfigReader
import de.p7s1.qa.sevenfacette.config.types.FacetteConfig
import org.junit.After
import org.junit.Before
import org.junit.Test

class ConfigTestMultiFiles {

    @Before
    fun setConfig() {
        System.setProperty("FACETTE_CONFIG", "facetteConfigMultiFile.yml")
        FacetteConfig.update()
    }

    @After
    fun clearConfig() {
        System.clearProperty("FACETTE_CONFIG")
        FacetteConfig.update()
    }

    @Test
    fun multiTest() {
//        val cReader = ConfigReader()
//        val config = cReader.readConfig()
//        assert(config.sevenFacette?.http?.clients?.size == 2)
//        { println("Actual client size == ${config.sevenFacette?.http?.clients?.size}") }
//        assert(config.sevenFacette?.custom?.size == 4)
//        { println("Actual custom size == ${config.sevenFacette?.custom?.size}") }
//        assert(config.sevenFacette?.custom?.get("testImport1") == "imported Value")
//        assert(config.sevenFacette?.custom?.get("test3") == "imported Value for Field test3")
    }

    @Test
    fun checkConfigObjectMultiFiles() {
        assert(FacetteConfig.http?.clients?.size == 2)
        { println("Actual client size == ${FacetteConfig.http?.clients?.size}") }
        assert(FacetteConfig.custom?.size == 4)
        { println("Actual custom size == ${FacetteConfig.custom?.size}") }
        assert(FacetteConfig.custom?.get("testImport1") == "imported Value")
        assert(FacetteConfig.custom?.get("test3") == "imported Value for Field test3")
    }
}
