package de.p7s1.qa.sevenfacette.sevenfacetteHttp

import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.readText
import io.ktor.util.toMap
import kotlinx.coroutines.runBlocking

/**
 * JVM specific implementation of the http response
 *
 * @constructor the constructor receives the Ktor response
 *
 * @author Florian Pilz
 */
actual class HttpResponse actual constructor(response: HttpResponse) {
    actual val body: String
    actual val status: Int
    actual val headers: Map<String, List<String>>

    /**
     * On initialization the Ktor response is used to fill the classes properties.
     * The body is returned in an asynchronous way so it is inside a runBlocking block
     */
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
