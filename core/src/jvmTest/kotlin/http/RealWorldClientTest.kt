package http

import de.p7s1.qa.sevenfacette.http.HttpResponse
import org.junit.Before
import org.junit.Test

class RealWorldClientTest {
    @Before
    fun setConfigFile() {
        System.setProperty("FACETTE_CONFIG", "realWorldFacetteConfig.yml")
    }

    //@Test
    fun getAllArticles() {
        val response: HttpResponse? = RealWorldClient().getAllArticles()
        println(response?.body)
        println(response?.status)
    }
}