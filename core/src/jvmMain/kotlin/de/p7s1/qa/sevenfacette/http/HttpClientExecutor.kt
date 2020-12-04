package de.p7s1.qa.sevenfacette.http

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

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
            var facetteResponse: HttpResponse? = null
            val fullPath = useUrl.path(usePath).create()

            println("Sending a ${useMethod.value} request to $fullPath with ${if(useBody == null) "no" else ""} content")

            var usedBody: Any? = null
            usedBody = useBody
            println("Body == $usedBody")

            runBlocking {
                launch {
                    try {

                        facetteResponse = HttpResponse(client.request {

                            url(fullPath)

                            method = useMethod

                            if (usedBody != null) {
                                body = usedBody
                            }

                            userAgent("SevenFacette")

                            if(useHeaders != null) {
                                useHeaders.header.forEach {
                                    headers.append(it.first, it.second)
                                }
                            }
                        })
                    } catch (e: Exception) {
                        println(e.message)
                    }
                }.join()
            }

            if(facetteResponse == null) throw Exception("No response received")
            println("Response http status == ${facetteResponse?.status}")
            println("Response headers == ${facetteResponse?.headers}")
            println("Response body == ${facetteResponse?.body}")
            return facetteResponse
        }
    }
}