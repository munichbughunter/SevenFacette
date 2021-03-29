package http

import de.p7s1.qa.sevenfacette.http.GenericHttpClient
import de.p7s1.qa.sevenfacette.http.HttpClientFactory
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Testingclass for [GenericHttpClient].
 *
 * Testcases:
 *
 * @author Stella Bastug
 */
class GenericHttpClientTest {

    @Test
    fun setClientTest() {}

    @Test
    fun postTest() {}

    @Test
    fun putTest() {}

    @Test
    fun deleteTest() {}

    @Test
    fun getTest() {
        System.setProperty("FACETTE_CONFIG", "facetteConfig.yml")
        val httpClient = HttpClientFactory.createClient("holidayRestClient")
        val response = httpClient.get("namedays?country=de&month=1&day=9", null)
        assertEquals(200, response?.status)
    }
}
