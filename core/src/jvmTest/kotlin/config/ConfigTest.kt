package config

import de.p7s1.qa.sevenfacette.config.ConfigReader
import de.p7s1.qa.sevenfacette.config.types.FacetteConfig
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull


class ConfigTest {

    @Test
    fun checkKafkaConfig() {
        val config = ConfigReader.readConfig().sevenFacette?.kafka

        assertEquals("development-kafka.de:9192", config?.bootstrapServer,
            "Kafka bootstrap server is not correct")

        assertEquals(2, config?.consumer?.size,
            "Number of consumers size is not correct")

        val testtopic1 = ConfigReader.getKafkaConsumer("testtopic1")

        assertNotNull(testtopic1,
            "testtopic1 is not available")
        assertEquals(false, testtopic1.useSASLAuthentication,
            "UseSasl of consumer testtopic1 is not correct")
        assertEquals(null, testtopic1.saslPassword,
            "Sasl password of consumer testtopic1 is not correct")
        assertEquals(null, testtopic1.saslUsername,
            "Sasl username of consumer testtopic1 is not correct")
        assertEquals("latest", testtopic1.autoOffset,
            "Autooffset of consumer testtopic1 is not correct")

        val topic2 = ConfigReader.getKafkaConsumer("topic2")

        assertNotNull(topic2,
            "topic2 is not available")
        assertEquals(false, topic2.useSASLAuthentication,
            "UseSasl of consumer topic2 is not correct")
        assertEquals(null, topic2.saslPassword,
            "Sasl password of consumer topic2 is not correct")
        assertEquals(null, topic2.saslUsername,
            "Sasl username of consumer topic2 is not correct")
        assertEquals("earliest", topic2.autoOffset,
            "Autooffset of consumer topic2 is not correct")
        assertEquals("development-kafka.de:9191", topic2.bootstrapServer,
            "Bootstrapserver of consumer topic2 is not correct")

        assertEquals(1, config?.producer?.size,
            "Number of producers is not correct")

        val testProducer1 = ConfigReader.getKafkaProducer("testProducer1")

        assertNotNull(testProducer1,
            "testProducer1 is not available")
        assertEquals(true, testProducer1.useSASLAuthentication,
            "Use sasl of producer testProducer1 is not correct")
        assertEquals("myProducerUserPass", testProducer1.saslPassword,
            "Sasl password of producer testProducer1 is not correct")
        assertEquals("myProducerUserName", testProducer1.saslUsername,
            "Sasl username of producer testProducer1 is not correct")
    }

    @Test
    fun checkDatabaseConfig() {
        val config = ConfigReader.readConfig().sevenFacette?.database

        assertEquals(2, config?.size,
            "Number of databases is not correct")

        val pvvDatabase = ConfigReader.getDatabase("pvv")

        assertEquals("jdbc:sqli://localhost:1529/abc:INFORMIXSERVER=info_server", pvvDatabase?.url,
            "Url of database 0 is not correct")
        assertEquals("com.informix.jdbc.IfxDriver", pvvDatabase?.driver,
            "Driver of database 0 is not correct")

        val postgresDb = ConfigReader.getDatabase("postgres")
        assertEquals("jdbc:postgresql://dev.svc.kube.local:5432/dev", postgresDb?.url,
            "Url of database 1 is not correct")
        assertEquals("org.postgresql.Driver", postgresDb?.driver,
            "Driver of database 1 is not correct")
    }

    @Test
    fun checkHttpClientConfig() {
        val config = ConfigReader.readConfig().sevenFacette?.http

        assertEquals(2, config?.clients?.size,
            "Number of http clients is not correct")

        val restfulBookerClient = ConfigReader.getHttpConfig("restfulBooker")
        assertNotNull(restfulBookerClient,
            "RestfulBookerClient does not exist")
        assertEquals(5000, restfulBookerClient.connectionTimeout,
            "ConnectionTimeout of restfullBookerClient is not correct")
        assertEquals(4000, restfulBookerClient.connectionRequestTimeout,
            "ConnectionRequestTimeout of restfullBookerClient is not correct")
        assertEquals(3000, restfulBookerClient.socketTimeout,
            "SocketTimeout of restfullBookerClient is not correct")
        assertEquals("localhost", restfulBookerClient.url?.baseUrl,
            "BaseUrl of restfullBookerClient is not correct")
        assertEquals(3001, restfulBookerClient.url?.port,
            "Url port of restfullBookerClient is not correct")
        assertEquals("http", restfulBookerClient.url?.protocol,
            "Url protocol of restfullBookerClient is not correct")
        assertEquals("localhost", restfulBookerClient.proxy?.host,
            "Proxy host of restfullBookerClient is not correct")
        assertEquals(8080, restfulBookerClient.proxy?.port,
            "Proxy port of restfullBookerClient is not correct")
        assertEquals(3, restfulBookerClient.authentication?.size ?: 0,
            "Authentication size of restfullBookerClient is not correct")

        val testClient = ConfigReader.getHttpConfig("testClient")
        assertNotNull(testClient,
            "TestClient does not exist")
        assertEquals(3000, testClient.connectionTimeout,
            "ConnectionTimeout of testClient is not correct")
        assertEquals(5000, testClient.connectionRequestTimeout,
            "ConnectionRequestTimeout of testClient is not correct")
        assertEquals(7000, testClient.socketTimeout,
            "SocketTimeout of testClient is not correct")
        assertEquals("some.url", testClient.url?.baseUrl,
            "BaseUrl of testClient is not correct")
        assertEquals(-1, testClient.url?.port,
            "Url port of testClient is not correct")
        assertEquals("https", testClient.url?.protocol,
            "Url protocol of testClient is not correct")
        assertEquals(null, testClient.proxy?.host,
            "Proxy host of testClient is not correct")
        assertEquals(null, testClient.proxy?.port,
            "Proxy port of testClient is not correct")
        assertEquals(0, testClient.authentication?.size ?: 0,
            "Authentication size of testClient is not correct")
    }
}


