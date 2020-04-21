import de.p7s1.qa.sevenfacette.config.ConfigReader
import de.p7s1.qa.sevenfacette.config.types.FacetteConfig
import org.junit.Test

class ConfigTest {

    @Test
    fun checkConfigInMain() {
        System.clearProperty("FACETTE_CONFIG")
        val cReader = ConfigReader()
        val config = cReader.readConfig()
        assert(config.http?.clients?.size == 2)
        assert(config.custom.isEmpty())
    }

    @Test
    fun multiTest() {
        System.setProperty("FACETTE_CONFIG", "facetteConfigMultiFile.yml")
        val cReader = ConfigReader()
        val config = cReader.readConfig()
        assert(config.http?.clients?.size == 2)
        assert(config.custom.isEmpty())
    }

    @Test
    fun checkConfigObject() {
        System.clearProperty("FACETTE_CONFIG")
        System.setProperty("saslUser", "Test User 123")
        assert(FacetteConfig.http?.clients?.size == 2)
        assert(FacetteConfig.custom.size == 1)
    }

    @Test
    fun checkConfigObjectMultiFiles() {
        System.setProperty("FACETTE_CONFIG", "facetteConfigMultiFile.yml")
        assert(FacetteConfig.http?.clients?.size == 2)
        assert(FacetteConfig.custom.size == 1)
    }
}


