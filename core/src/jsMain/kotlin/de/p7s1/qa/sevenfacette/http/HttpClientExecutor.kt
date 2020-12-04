package de.p7s1.qa.sevenfacette.http

import io.ktor.client.*
import io.ktor.http.*

actual class HttpClientExecutor {
    actual companion object {
        actual fun executeRequest(
            client: HttpClient,
            useMethod: HttpMethod,
            useUrl: Url,
            usePath: String,
            useBody: Any?,
            useHeaders: HttpHeader?
        ): HttpResponse? {
            TODO("Not yet implemented")
        }
    }
}