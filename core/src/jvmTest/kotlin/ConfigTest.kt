import de.p7s1.qa.sevenfacette.config.ConfigReader
import org.junit.Test

class ConfigTest {

    @Test
    fun checkConfigInMain() {
        val cReader = ConfigReader()
        val config = cReader.readConfig()
        assert(config.httpClients.size == 2)
    }
}


