package de.p7s1.qa.sevenfacette.http

import io.ktor.client.HttpClient

actual class GenericHttpClient {
    actual fun setClient(url: Url, client: HttpClient): GenericHttpClient {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    actual fun post(path: String, content: String, headers: HttpHeader): HttpResponse? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    actual fun postByteArray(path: String, content: ByteArray, headers: HttpHeader): HttpResponse? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    actual fun postMultiPart(path: String, content: MultipartBody, header: HttpHeader): HttpResponse? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    actual fun postGraphQl(path: String, content: GraphQlContent, headers: HttpHeader, contentIsJson: Boolean): HttpResponse? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    actual fun put(path: String, content: String, headers: HttpHeader): HttpResponse? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    actual fun putByteArray(path: String, content: ByteArray, headers: HttpHeader): HttpResponse? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    actual fun putMultiPart(path: String, content: MultipartBody, headers: HttpHeader): HttpResponse? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    actual fun putGraphQl(path: String, content: GraphQlContent, headers: HttpHeader, contentIsJson: Boolean): HttpResponse? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    actual fun get(path: String, headers: HttpHeader): HttpResponse? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    actual fun delete(path: String, headers: HttpHeader): HttpResponse? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
