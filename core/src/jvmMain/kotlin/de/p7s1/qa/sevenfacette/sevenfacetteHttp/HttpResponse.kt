package de.p7s1.qa.sevenfacette.sevenfacetteHttp

import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.readText
import io.ktor.util.toMap
import kotlinx.coroutines.runBlocking

actual class HttpResponse actual constructor(response: HttpResponse) {
    actual val body: String
    actual val status: Int
    actual val headers: Map<String, List<String>>

    init {
        var bodyTemp: String = ""
        runBlocking {
            bodyTemp = response.readText()
        }
        this.body = bodyTemp
        this.status = response.status.value
        this.headers = response.headers.toMap()
    }
}
