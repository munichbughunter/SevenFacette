package de.p7s1.qa.sevenfacette.sevenfacetteHttp

import io.ktor.client.statement.HttpResponse

expect class HttpResponse (response: HttpResponse) {
    val body: String
    val status: Int
    val headers: Map<String, List<String>>
}
