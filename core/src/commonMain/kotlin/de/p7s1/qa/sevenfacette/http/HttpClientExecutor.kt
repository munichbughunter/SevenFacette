package de.p7s1.qa.sevenfacette.http

import io.ktor.client.*
import io.ktor.http.*

expect class HttpClientExecutor {
    companion object {
        fun executeRequest(client: HttpClient,
                          useMethod: HttpMethod,
                          useUrl: Url,
                          usePath: String,
                          useBody: Any?,
                          useHeaders: HttpHeader?): HttpResponse?
    }
}
