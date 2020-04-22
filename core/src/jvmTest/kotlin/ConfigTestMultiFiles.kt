import de.p7s1.qa.sevenfacette.config.ConfigReader
import de.p7s1.qa.sevenfacette.config.types.FacetteConfig
import org.junit.Before
import org.junit.Test

class ConfigTestMultiFiles {

    @Before
    fun setConfig() {
        System.setProperty("FACETTE_CONFIG", "facetteConfigMultiFile.yml")
    }

    @Test
    fun multiTest() {
        val cReader = ConfigReader()
        val config = cReader.readConfig()
        assert(config.sevenFacette?.http?.clients?.size == 2)
        assert(config.sevenFacette?.custom.isNullOrEmpty())
    }

    @Test
    fun checkConfigObjectMultiFiles() {
        assert(FacetteConfig.http?.clients?.size == 2)
        assert(FacetteConfig.custom?.isEmpty() ?: false)
    }
}


