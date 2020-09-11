import de.p7s1.qa.sevenfacette.config.ConfigReader
import de.p7s1.qa.sevenfacette.config.types.FacetteConfig
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull


class ConfigTest {

    @Test
    fun checkKafkaConfig() {
        val config = ConfigReader().readConfig().sevenFacette?.kafka

        assertEquals("development-kafka.de:9192", config?.bootstrap)

        assertEquals(2, config?.consumer?.size, "Number of consumers size is not correct")
        assertEquals("testtopic1", config?.consumer?.get(0)?.name, "Name of consumer 0 is not correct")
        assertEquals(false, config?.consumer?.get(0)?.useSASLAuthentication, "UseSasl of consumer 0 is not correct")
        assertEquals(null, config?.consumer?.get(0)?.saslPassword, "Sasl password of consumer 0 is not correct")
        assertEquals(null, config?.consumer?.get(0)?.saslUsername, "Sasl username of consumer 0 is not correct")

        assertEquals("topic2", config?.consumer?.get(1)?.name, "Name of consumer 1 is not correct")
        assertEquals(false, config?.consumer?.get(1)?.useSASLAuthentication, "UseSasl of consumer 1 is not correct")
        assertEquals(null, config?.consumer?.get(1)?.saslPassword, "Sasl password of consumer 1 is not correct")
        assertEquals(null, config?.consumer?.get(1)?.saslUsername, "Sasl username of consumer 1 is not correct")

        assertEquals(1, config?.producer?.size, "Number of producers is not correct")
        assertEquals("testProducer1", config?.producer?.get(0)?.name, "Name of producer 0 is not correct")
        assertEquals(true, config?.producer?.get(0)?.useSASLAuthentication, "Use sasl of producer 0 is not correct")
        assertEquals("myProducerUserPass", config?.producer?.get(0)?.saslPassword, "Sasl password of producer 0 is not correct")
        assertEquals("myProducerUserName", config?.producer?.get(0)?.saslUsername, "Sasl username of producer 0 is not correct")
    }

    @Test
    fun checkDatabaseConfig() {
        val config = ConfigReader().readConfig().sevenFacette?.database

        assertEquals(2, config?.size, "Number of databases is not correct")

        assertEquals("pvv", config?.get(0)?.name, "Name of database 0 is not correct")
        assertEquals("pvv", config?.get(0)?.url, "Url of database 0 is not correct")
        assertEquals("pvv", config?.get(0)?.driver, "Driver of database 0 is not correct")

        assertEquals("pvv", config?.get(1)?.name, "Name of database 1 is not correct")
        assertEquals("pvv", config?.get(1)?.url, "Url of database 1 is not correct")
        assertEquals("pvv", config?.get(1)?.driver, "Driver of database 1 is not correct")
    }

    @Test
    fun checkHttpClientConfig() {
        val config = ConfigReader().readConfig().sevenFacette?.http

        assertEquals(2, config?.clients?.size, "Number of http clients is not correct")

        val restfulBookerClient = config?.getClient("restfulBooker")
        assertNotNull(restfulBookerClient, "RestfulBookerClient does not exist")
        assertEquals(5000, restfulBookerClient.connectionTimeout, "ConnectionTimeout of restfullBookerClient is not correct")
        assertEquals(4000, restfulBookerClient.connectionRequestTimeout, "ConnectionRequestTimeout of restfullBookerClient is not correct")
        assertEquals(3000, restfulBookerClient.socketTimeout, "SocketTimeout of restfullBookerClient is not correct")
        assertEquals("localhost", restfulBookerClient.url?.baseUrl, "BaseUrl of restfullBookerClient is not correct")
        assertEquals(3001, restfulBookerClient.url?.port, "Url port of restfullBookerClient is not correct")
        assertEquals("http", restfulBookerClient.url?.protocol, "Url protocol of restfullBookerClient is not correct")
        assertEquals("localhost", restfulBookerClient.proxy?.host, "Proxy host of restfullBookerClient is not correct")
        assertEquals(8080, restfulBookerClient.proxy?.port, "Proxy port of restfullBookerClient is not correct")
        assertEquals(3, restfulBookerClient.authentication?.size ?: 0, "Authentication size of restfullBookerClient is not correct")

        val testClient = config.getClient("testClient")
        assertNotNull(testClient, "TestClient does not exist")
        assertEquals(3000, testClient.connectionTimeout, "ConnectionTimeout of testClient is not correct")
        assertEquals(5000, testClient.connectionRequestTimeout, "ConnectionRequestTimeout of testClient is not correct")
        assertEquals(7000, testClient.socketTimeout, "SocketTimeout of testClient is not correct")
        assertEquals("some.url", testClient.url?.baseUrl, "BaseUrl of testClient is not correct")
        assertEquals(-1, testClient.url?.port, "Url port of testClient is not correct")
        assertEquals("https", testClient.url?.protocol, "Url protocol of testClient is not correct")
        assertEquals(null, testClient.proxy?.host, "Proxy host of testClient is not correct")
        assertEquals(null, testClient.proxy?.port, "Proxy port of testClient is not correct")
        assertEquals(0, testClient.authentication?.size ?: 0, "Authentication size of testClient is not correct")
    }

    @Test
    fun checkConfigInMain() {
        val cReader = ConfigReader()
        val config = cReader.readConfig()

        assert(config.sevenFacette?.http?.clients?.size == 2)
            { println("Actual client size == ${config.sevenFacette?.http?.clients?.size}") }
        assert(config.sevenFacette?.custom != null)
            { println("Actual custom size == ${config.sevenFacette?.custom?.size}") }
    }

    @Test
    fun checkConfigObject() {
        System.setProperty("saslUser", "Test User 123")
        System.setProperty("saslPass", "Test Pass 123")
        FacetteConfig.update()

        assert(FacetteConfig.http?.clients?.size == 2)
            { println("Actual client size == ${FacetteConfig.http?.clients?.size}") }
        assert(FacetteConfig.custom?.size == 1)
            { println("Actual custom size == ${FacetteConfig.custom?.size}") }
        assert(FacetteConfig.kafka?.getKafkaConsumer("testtopic1")?.saslUsername == "Test User 123")
            { println("Actual kafka user == ${FacetteConfig.kafka?.getKafkaConsumer("testtopic1")?.saslUsername}") }
        assert(FacetteConfig.kafka?.getKafkaConsumer("testtopic1")?.saslPassword == "Test Pass 123")
            { println("Actual kafka user == ${FacetteConfig.kafka?.getKafkaConsumer("testtopic1")?.saslUsername}") }
    }

    @Test
    fun checkConfigObjectDefaultReplace() {
        System.clearProperty("saslUser")
        FacetteConfig.update()

        assert(FacetteConfig.http?.clients?.size == 2)
            { println("Actual client size == ${FacetteConfig.http?.clients?.size}") }
        assert(FacetteConfig.custom?.size == 1)
            { println("Actual custom size == ${FacetteConfig.custom?.size}")
                throw Error("SIZE == ${FacetteConfig.custom?.size}")}
        assert(FacetteConfig.kafka?.getKafkaConsumer("testtopic1")?.saslUsername == "Default User")
            { println("Actual kafka user == ${FacetteConfig.kafka?.getKafkaConsumer("testtopic1")?.saslUsername}") }
        assert(FacetteConfig.kafka?.consumer?.size == 2)
            { println("Actual kafka consumer size == ${FacetteConfig.kafka?.consumer?.size}") }
    }
}


