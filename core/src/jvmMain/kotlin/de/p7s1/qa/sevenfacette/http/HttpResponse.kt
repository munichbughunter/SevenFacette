package de.p7s1.qa.sevenfacette.http

import io.ktor.client.statement.*
import io.ktor.util.*
import kotlinx.coroutines.runBlocking

/**
 * JVM specific implementation of the http response
 *
 * @constructor the constructor receives the Ktor response
 *
 * @author Florian Pilz
 */
actual class HttpResponse actual constructor(response: io.ktor.client.statement.HttpResponse) {
    val body: String = runBlocking {
        return@runBlocking response.readText()
    }.toString()
    val status: Int = response.status.value
    val headers: Map<String, List<String>> = response.headers.toMap()
}
