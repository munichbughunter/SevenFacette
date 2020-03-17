package de.p7s1.qa.sevenfacette.sevenfacetteHttp

import de.p7s1.qa.sevenfacette.config.RestServiceAuth
import io.ktor.client.HttpClient

actual open class GenericHttpClient {
    actual val client: HttpClient
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    actual var auth: RestServiceAuth?
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}
    actual var url: Url
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}

    actual fun auth(auth: RestServiceAuth): GenericHttpClient {
        TODO("not implemented")
    }

    actual fun url(url: Url): GenericHttpClient {
        TODO("not implemented")
    }

    actual fun post(path: String, content: String, headers: HttpHeader): HttpResponse? {
        TODO("not implemented")
    }

    actual fun postByteArray(path: String, content: ByteArray, headers: HttpHeader): HttpResponse? {
        TODO("not implemented")
    }

    actual fun put(path: String, content: String, headers: HttpHeader): HttpResponse? {
        TODO("not implemented")
    }

    actual fun putByteArray(path: String, content: ByteArray, headers: HttpHeader): HttpResponse? {
        TODO("not implemented")
    }

    actual fun get(path: String, headers: HttpHeader): HttpResponse? {
        TODO("not implemented")
    }

    actual fun delete(path: String, headers: HttpHeader): HttpResponse? {
        TODO("not implemented")
    }

    actual fun postMultiPart(path: String, content: MultipartBody, header: HttpHeader): HttpResponse? {
        TODO("not implemented")
    }

    actual fun putMultiPart(path: String, content: MultipartBody, headers: HttpHeader): HttpResponse? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
