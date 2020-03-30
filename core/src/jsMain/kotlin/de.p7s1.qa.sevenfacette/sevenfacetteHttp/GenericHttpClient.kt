package de.p7s1.qa.sevenfacette.sevenfacetteHttp

import io.ktor.client.HttpClient

actual open class GenericHttpClient {
    actual var url: Url
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}

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

    actual fun postGraphQl(path: String, content: GraphQlContent, headers: HttpHeader, contentIsJson: Boolean): HttpResponse? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    actual fun putGraphQl(path: String, content: GraphQlContent, headers: HttpHeader, contentIsJson: Boolean): HttpResponse? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
