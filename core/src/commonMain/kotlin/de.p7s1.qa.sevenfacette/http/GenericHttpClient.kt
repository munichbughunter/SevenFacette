package de.p7s1.qa.sevenfacette.http

import io.ktor.util.KtorExperimentalAPI

/**
 * Class for http requests which needs to be implemented in js- and jvm-target
 * Documentation for functions is described in the corresponding target implementations.
 *
 * @author Florian Pilz
 */
expect open class GenericHttpClient() {

    fun setUrl(url: Url): GenericHttpClient
    fun setAuthentication(authentication: Authentication): GenericHttpClient
    fun setProxy(host: String?, port: Int): GenericHttpClient
    fun build()

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
