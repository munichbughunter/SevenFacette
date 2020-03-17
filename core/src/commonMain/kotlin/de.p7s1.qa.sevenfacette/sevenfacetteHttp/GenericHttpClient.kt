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

    fun post(path: String, content: String, headers: HttpHeader): HttpResponse?
    fun postByteArray(path: String, content: ByteArray, headers: HttpHeader): HttpResponse?
    fun postMultiPart(path: String, content: MultipartBody, header: HttpHeader): HttpResponse?

    fun put(path: String, content: String, headers: HttpHeader): HttpResponse?
    fun putByteArray(path: String, content: ByteArray, headers: HttpHeader): HttpResponse?
    fun putMultiPart(path: String, content: MultipartBody, headers: HttpHeader): HttpResponse?

    fun get(path: String, headers: HttpHeader): HttpResponse?

    fun delete(path: String, headers: HttpHeader): HttpResponse?
}
