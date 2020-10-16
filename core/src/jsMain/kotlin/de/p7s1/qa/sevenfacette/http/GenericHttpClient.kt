package de.p7s1.qa.sevenfacette.http

import io.ktor.client.HttpClient

actual class GenericHttpClient {
    actual fun setClient(url: Url, client: HttpClient): GenericHttpClient {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    @JsName("post")
    actual fun post(path: String, content: String, contentType: CONTENTTYPES, headers: HttpHeader): HttpResponse? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    @JsName("postByteArray")
    actual fun post(path: String, content: ByteArray, headers: HttpHeader): HttpResponse? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    @JsName("postMultiPart")
    actual fun post(path: String, content: MultipartBody, header: HttpHeader): HttpResponse? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    actual fun put(path: String, content: String, contentType: CONTENTTYPES, headers: HttpHeader): HttpResponse? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    @JsName("putByteArray")
    actual fun put(path: String, content: ByteArray, headers: HttpHeader): HttpResponse? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    @JsName("putMultiPart")
    actual fun put(path: String, content: MultipartBody, headers: HttpHeader): HttpResponse? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    actual fun get(path: String, headers: HttpHeader): HttpResponse? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    actual fun delete(path: String, headers: HttpHeader): HttpResponse? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
