import de.p7s1.qa.sevenfacette.config.ConfigReader
import org.junit.Test

class ConfigTest {

    @Test
    fun checkConfigInMain() {
        System.clearProperty("FACETTE_CONFIG")
        val cReader = ConfigReader()
        val config = cReader.readConfig()
        println(config.toString())
        assert(config.httpClients.size == 2)
    }

    @Test
    fun multiTest() {
        System.setProperty("FACETTE_CONFIG", "facetteConfigMultiFile.yml")
        val cReader = ConfigReader()
        val config = cReader.readConfig()
        println(config.toString())
        assert(config.httpClients.size == 2)
    }
}


