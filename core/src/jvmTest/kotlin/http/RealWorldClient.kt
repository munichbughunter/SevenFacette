package http

import de.p7s1.qa.sevenfacette.http.GenericHttpClient
import de.p7s1.qa.sevenfacette.http.HttpClientFactory
import de.p7s1.qa.sevenfacette.http.HttpHeader
import de.p7s1.qa.sevenfacette.http.HttpResponse


class RealWorldClient {

    private val client: GenericHttpClient = HttpClientFactory.createClient("realWorld")

    fun getAllArticles(): HttpResponse? =
        client.get("articles", HttpHeader())
}