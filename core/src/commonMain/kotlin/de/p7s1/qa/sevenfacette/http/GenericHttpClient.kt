package de.p7s1.qa.sevenfacette.http

import io.ktor.client.HttpClient

/**
 * Class for http requests which needs to be implemented in js- and jvm-target
 * Documentation for functions is described in the corresponding target implementations.
 *
 * @author Florian Pilz
 */
expect class GenericHttpClient() {

    fun setClient(url: Url, client: HttpClient): GenericHttpClient

    fun post(path: String, content: String, contentType: CONTENTTYPES, headers: HttpHeader): HttpResponse?
    fun post(path: String, content: ByteArray, headers: HttpHeader): HttpResponse?
    fun post(path: String, content: MultipartBody, header: HttpHeader): HttpResponse?

    fun put(path: String, content: String, contentType: CONTENTTYPES, headers: HttpHeader): HttpResponse?
    fun put(path: String, content: ByteArray, headers: HttpHeader): HttpResponse?
    fun put(path: String, content: MultipartBody, headers: HttpHeader): HttpResponse?

    fun get(path: String, headers: HttpHeader): HttpResponse?

    fun delete(path: String, headers: HttpHeader): HttpResponse?
}
