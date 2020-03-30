package de.p7s1.qa.sevenfacette.sevenfacetteHttp

import io.ktor.client.HttpClient

expect open class GenericHttpClient() {

    var url: Url
        private set

    fun url(url: Url): GenericHttpClient

    fun post(path: String, content: String, headers: HttpHeader): HttpResponse?
    fun postByteArray(path: String, content: ByteArray, headers: HttpHeader): HttpResponse?
    fun postMultiPart(path: String, content: MultipartBody, header: HttpHeader): HttpResponse?
    fun postGraphQl(path: String, content: GraphQlContent, headers: HttpHeader, contentIsJson: Boolean = false): HttpResponse?

    fun put(path: String, content: String, headers: HttpHeader): HttpResponse?
    fun putByteArray(path: String, content: ByteArray, headers: HttpHeader): HttpResponse?
    fun putMultiPart(path: String, content: MultipartBody, headers: HttpHeader): HttpResponse?
    fun putGraphQl(path: String, content: GraphQlContent, headers: HttpHeader, contentIsJson: Boolean = false): HttpResponse?

    fun get(path: String, headers: HttpHeader): HttpResponse?

    fun delete(path: String, headers: HttpHeader): HttpResponse?
}
