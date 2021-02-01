package de.p7s1.qa.sevenfacette.http

import io.ktor.client.statement.*
import io.ktor.util.*
import kotlinx.coroutines.runBlocking
import java.nio.charset.MalformedInputException

/**
 * JVM specific implementation of the http response
 *
 * @constructor the constructor receives the Ktor response
 *
 * @author Florian Pilz
 */
actual class HttpResponse actual constructor(response: io.ktor.client.statement.HttpResponse) {
    val body: String = runBlocking {
        try {
            return@runBlocking response.readText()
        } catch (e: MalformedInputException) {
            return@runBlocking response.readBytes()
        }
    }.toString()
    val status: Int = response.status.value
    val headers: Map<String, List<String>> = response.headers.toMap()
}
