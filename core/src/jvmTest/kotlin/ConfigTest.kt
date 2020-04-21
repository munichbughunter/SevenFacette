import de.p7s1.qa.sevenfacette.config.ConfigReader
import de.p7s1.qa.sevenfacette.config.FacetteConfig
import org.junit.Test

class ConfigTest {

    @Test
    fun checkConfigInMain() {
        System.clearProperty("FACETTE_CONFIG")
        val cReader = ConfigReader()
        val config = cReader.readConfig()
        println(config.toString())
        assert(config.httpClients.size == 2)
        assert(config.custom.size == 0)
    }

    @Test
    fun multiTest() {
        System.setProperty("FACETTE_CONFIG", "facetteConfigMultiFile.yml")
        val cReader = ConfigReader()
        val config = cReader.readConfig()
        println(config.toString())
        assert(config.httpClients.size == 2)
        assert(config.custom.size == 0)
    }

    @Test
    fun checkConfigObject() {
        System.clearProperty("FACETTE_CONFIG")
        assert(FacetteConfig.httpClients.size == 2)
        assert(FacetteConfig.custom.isEmpty())
        println(FacetteConfig.httpClients)
    }

    @Test
    fun checkConfigObjectMultiFiles() {
        System.setProperty("FACETTE_CONFIG", "facetteConfigMultiFile.yml")
        assert(FacetteConfig.httpClients.size == 2)
        assert(FacetteConfig.custom.isEmpty())
    }
}


