package de.p7s1.qa.sevenfacette.sevenfacetteHttp

import de.p7s1.qa.sevenfacette.config.RestServiceAuth
import io.ktor.client.HttpClient

expect open class GenericHttpClient() {

    val client: HttpClient
    var auth: RestServiceAuth?
        private set
    var url: Url
        private set

    fun auth(auth: RestServiceAuth): GenericHttpClient
    fun url(url: Url): GenericHttpClient

    fun post(path: String, content: String, headers: httpHeader): httpResponse
    fun postByteArray(path: String, content: ByteArray, headers: httpHeader): httpResponse

    fun put(path: String, content: String, headers: httpHeader): httpResponse
    fun putByteArray(path: String, content: ByteArray, headers: httpHeader): httpResponse

    fun get(path: String, headers: httpHeader): httpResponse

    fun delete(path: String, headers: httpHeader): httpResponse
}
