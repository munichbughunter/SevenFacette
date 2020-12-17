import de.p7s1.qa.sevenfacette.config.ConfigReader
import de.p7s1.qa.sevenfacette.config.types.FacetteConfig
import org.junit.Test


class ConfigTest {

    @Test
    fun checkConfigInMain() {
//        val cReader = ConfigReader()
//        val config = cReader.readConfig()
//
//        assert(config.sevenFacette?.http?.clients?.size == 2)
//        { println("Actual client size == ${config.sevenFacette?.http?.clients?.size}") }
//        assert(config.sevenFacette?.custom != null)
//        { println("Actual custom size == ${config.sevenFacette?.custom?.size}") }
    }

    @Test
    fun checkConfigObject() {
//        System.setProperty("saslUser", "Test User 123")
//        System.setProperty("saslPass", "Test Pass 123")
//        FacetteConfig.update()
//
//        assert(FacetteConfig.http?.clients?.size == 2)
//        { println("Actual client size == ${FacetteConfig.http?.clients?.size}") }
//        assert(FacetteConfig.custom?.size == 1)
//        { println("Actual custom size == ${FacetteConfig.custom?.size}") }
//        assert(FacetteConfig.kafka?.getKafkaConsumer("testtopic1")?.saslUsername == "Test User 123")
//        { println("Actual kafka user == ${FacetteConfig.kafka?.getKafkaConsumer("testtopic1")?.saslUsername}") }
//        assert(FacetteConfig.kafka?.getKafkaConsumer("testtopic1")?.saslPassword == "Test Pass 123")
//        { println("Actual kafka user == ${FacetteConfig.kafka?.getKafkaConsumer("testtopic1")?.saslUsername}") }
    }

    @Test
    fun checkConfigObjectDefaultReplace() {
//        System.clearProperty("saslUser")
//        FacetteConfig.update()
//
//        assert(FacetteConfig.http?.clients?.size == 2)
//        { println("Actual client size == ${FacetteConfig.http?.clients?.size}") }
//        assert(FacetteConfig.custom?.size == 1)
//        { println("Actual custom size == ${FacetteConfig.custom?.size}")
//            throw Error("SIZE == ${FacetteConfig.custom?.size}")}
//        assert(FacetteConfig.kafka?.getKafkaConsumer("testtopic1")?.saslUsername == "Default User")
//        { println("Actual kafka user == ${FacetteConfig.kafka?.getKafkaConsumer("testtopic1")?.saslUsername}") }
//        assert(FacetteConfig.kafka?.consumer?.size == 2)
//        { println("Actual kafka consumer size == ${FacetteConfig.kafka?.consumer?.size}") }
    }
}
