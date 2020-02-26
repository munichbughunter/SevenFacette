package de.p7s1.qa.sevenfacette.http

import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.request.get
import io.ktor.client.statement.HttpStatement
import kotlinx.coroutines.runBlocking

actual open class GenericHttpClient {

    val client = HttpClient(Apache)

    actual fun post(url: String, content: String, header: List<Pair<String, String>>) {

    }

    actual fun postByteArray(url: String, content: ByteArray, header: List<Pair<String, String>>) {

    }

    actual fun put(url: String, content: String, header: List<Pair<String, String>>) {

    }

    actual fun putByteArray(url: String, content: ByteArray, header: List<Pair<String, String>>) {

    }

    actual fun get(url: String) {
        runBlocking {
            return@runBlocking client.use { client -> client.get<HttpStatement>(url) }
        }

    }

    actual suspend fun suspendedGet(url: String) {


        runBlocking {
            return@runBlocking client.use { client -> client.get<HttpStatement>("") }
        }
    }

}
