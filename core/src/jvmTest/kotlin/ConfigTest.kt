import de.p7s1.qa.sevenfacette.config.ConfigReader
import de.p7s1.qa.sevenfacette.config.types.FacetteConfig
import org.junit.Test


class ConfigTest {

    @Test
    fun checkConfigInMain() {
        val cReader = ConfigReader()
        val config = cReader.readConfig()

        assert(config.http?.clients?.size == 2)
        assert(config.custom.isNotEmpty())
    }

    @Test
    fun checkConfigObject() {
        System.setProperty("saslUser", "Test User 123")
        FacetteConfig.update()

        assert(FacetteConfig.http?.clients?.size == 2)
        assert(FacetteConfig.custom?.size == 1)
        assert(FacetteConfig.kafka?.getKafkaConsumer("testtopic1")?.saslUsername == "Test User 123")
    }

    @Test
    fun checkConfigObjectDefaultReplace() {
        System.clearProperty("saslUser")
        FacetteConfig.update()

        assert(FacetteConfig.http?.clients?.size == 2)
        assert(FacetteConfig.custom?.size == 1)
        assert(FacetteConfig.kafka?.getKafkaConsumer("testtopic1")?.saslUsername == "Default User")
        assert(FacetteConfig.kafka?.consumer?.size == 2)
    }
}


