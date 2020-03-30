package de.p7s1.qa.sevenfacette.sevenfacetteHttp

import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.features.HttpSend
import io.ktor.client.features.auth.Auth
import io.ktor.client.features.cookies.AcceptAllCookiesStorage
import io.ktor.client.features.cookies.HttpCookies
import io.ktor.client.features.json.JacksonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.request
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.content.ByteArrayContent
import io.ktor.http.content.PartData
import io.ktor.http.content.TextContent
import io.ktor.http.userAgent
import io.ktor.util.KtorExperimentalAPI
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.apache.http.conn.ssl.NoopHostnameVerifier
import org.apache.http.conn.ssl.TrustAllStrategy
import org.apache.http.ssl.SSLContextBuilder
import java.net.InetSocketAddress
import java.net.Proxy

actual open class GenericHttpClient actual constructor() {

    actual var url = Url()
    private var authentication: Authentication? = null
    private var httpProxy: Proxy? = null
    private lateinit var client: HttpClient

    fun setAuthentication(authentication: Authentication): GenericHttpClient {
        this.authentication = authentication
        return this
    }

    fun setProxy(host: String, port: Int): GenericHttpClient {
        this.httpProxy = if (host == null) Proxy(Proxy.Type.HTTP, InetSocketAddress(port)) else Proxy(Proxy.Type.HTTP, InetSocketAddress(host, port))
        return this
    }

    @KtorExperimentalAPI
    fun build() {
        this.client =  HttpClient(Apache) {
            expectSuccess = false

            install(HttpCookies) {
                storage = AcceptAllCookiesStorage()
            }

            install(JsonFeature) {
                serializer = JacksonSerializer()
            }

            install(HttpSend){
                maxSendCount = 2
            }

            if(authentication != null) {
                install(Auth){
                   providers.add(AuthenticationMapper.map(authentication))
                }
            }

            engine {
                socketTimeout = 10_000
                connectTimeout = 10_000
                connectionRequestTimeout = 20_000

                customizeClient { // Trust all certificates
                    setSSLContext(
                            SSLContextBuilder
                                    .create()
                                    .loadTrustMaterial(TrustAllStrategy())
                                    .build()
                    )
                    setSSLHostnameVerifier(NoopHostnameVerifier())

                }

                if(httpProxy != null) {
                    proxy = httpProxy
                }
            }
        }
    }

    actual fun url(url: Url): GenericHttpClient {
        this.url = url
        return this
    }

    actual fun post(path: String, content: String, headers: HttpHeader): HttpResponse? {
        return this.executeRequest(HttpMethod.Post, path, content, headers)
    }

    actual fun postByteArray(path: String, content: ByteArray, headers: HttpHeader): HttpResponse? {
        return this.executeRequest(HttpMethod.Post, path, content, headers)
    }

    actual fun postMultiPart(path: String, content: MultipartBody, header: HttpHeader): HttpResponse? {
        return this.executeRequest(HttpMethod.Post, path, content, header)
    }

    actual fun put(path: String, content: String, headers: HttpHeader): HttpResponse? {
        return this.executeRequest(HttpMethod.Put, path, content, headers)
    }

    actual fun putByteArray(path: String, content: ByteArray, headers: HttpHeader): HttpResponse? {
        return this.executeRequest(HttpMethod.Put, path, content, headers)
    }

    actual fun putMultiPart(path: String, content: MultipartBody, headers: HttpHeader): HttpResponse? {
        return this.executeRequest(HttpMethod.Put, path, content, headers)
    }

    actual fun delete(path: String, headers: HttpHeader): HttpResponse? {
        return this.executeRequest<Unit>(HttpMethod.Delete, path, null, headers)
    }

    actual fun get(path: String, headers: HttpHeader): HttpResponse? {
        return this.executeRequest<Unit>(HttpMethod.Get, path, null, headers)
    }

    actual fun postGraphQl(path: String, content: GraphQlContent, headers: HttpHeader, contentIsJson: Boolean): HttpResponse? {
        return this.executeRequest(HttpMethod.Post, path, content, headers)
    }

    actual fun putGraphQl(path: String, content: GraphQlContent, headers: HttpHeader, contentIsJson: Boolean): HttpResponse? {
        return this.executeRequest(HttpMethod.Put, path, content, headers)
    }

    private inline fun <reified T> executeRequest(useMethod: HttpMethod, usePath: String, useContent: T?, useHeaders: HttpHeader): HttpResponse? {
        var facetteResponse: HttpResponse? = null
        val fullPath = this.url.path(usePath).create()

        println("Sending a ${useMethod.value} request to $fullPath")

        // SSLContextBuilder.create()

        runBlocking {
            launch {
                try {
                    facetteResponse = HttpResponse(client.request {

                        url(fullPath)
                        println(fullPath)

                        method = useMethod
                        println(useMethod)

                        if(useContent != null) {
                            body = getBody(useContent)
                        }
                        userAgent("SevenFacette")

                        useHeaders.header.forEach {
                            headers.append(it.first, it.second)
                        }
                    })
                } catch (e: Exception) {
                    println(e.message)
                }
            }.join()
        }

        if(facetteResponse == null) throw Exception("No response received")
        return facetteResponse
    }

    private inline fun <reified T> getBody(content: T): Any {
        return when(T::class) {
            String::class, Unit::class -> TextContent(content as String, ContentType.Application.Json)
            ByteArray::class -> ByteArrayContent(content as ByteArray)
            MultipartBody::class -> mutableListOf<PartData>()
            GraphQlContent::class -> TextContent((content as GraphQlContent).query, ContentType.Application.GraphQl)
            else -> throw Error("Content not supported")
        }
    }
}
