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

/**
 * JVM specific implementation of the generic rest client
 *
 * @author Florian Pilz
 */
actual open class GenericHttpClient actual constructor() {

    /**
     * JVM specific implementation of URL
     */
    actual var url = Url()

    /**
     * Lazy loading of socket timeout for all requests
     * If system environment SOCKET_TIMEOUT is set this will be used. If not default timeout 10_000 will be used
     */
    private val socketTimeout: Int by lazy {
        if(System.getenv("SOCKET_TIMEOUT").isNullOrEmpty()) {
            return@lazy 10_000
        } else {
            return@lazy System.getenv("SOCKET_TIMEOUT").toInt()
        }
    }

    /**
     * Lazy loading of connection timeout for all requests
     * If system environment CONNECTION_TIMEOUT is set this will be used. If not default timeout 10_000 will be used
     */
    private val connectTimeout: Int by lazy {
        if(System.getenv("CONNECTION_TIMEOUT").isNullOrEmpty()) {
            return@lazy 10_000
        } else {
            return@lazy System.getenv("CONNECTION_TIMEOUT").toInt()
        }
    }

    /**
     * Lazy loading of connection request timeout for all requests
     * If system environment CONNECTION_REQUEST_TIMEOUT is set this will be used. If not default timeout 10_000 will be used
     */
    private val connectionRequestTimeout: Int by lazy {
        if(System.getenv("CONNECTION_REQUEST_TIMEOUT").isNullOrEmpty()) {
            return@lazy 20_000
        } else {
            return@lazy System.getenv("CONNECTION_REQUEST_TIMEOUT").toInt()
        }
    }

    private var authentication: Authentication? = null
    private var httpProxy: Proxy? = null
    private lateinit var client: HttpClient

    /**
     * JVM specific implementation of set authentication
     * Adds authentication to http requests. If no authentication is provided no authentication is added to the requests.
     * Authentication must be added before function build is executed.
     * @see Authentication
     *
     * @param authentication authentication
     * @return this
     */
    actual fun setAuthentication(authentication: Authentication): GenericHttpClient {
        this.authentication = authentication
        return this
    }

    /**
     * JVM specific implementation of set proxy
     * Adds proxy to http requests. If no proxy is provided no proxy is added to the requests.
     * Proxy must be added before function build is executed.
     * @see HttpProxy
     *
     * @param host string host of proxy. Can be null. In this case only the port is used as proxy.
     * @param port string port of proxy
     * @return this
     */
    actual fun setProxy(host: String?, port: Int): GenericHttpClient {
        this.httpProxy = if (host == null) Proxy(Proxy.Type.HTTP, InetSocketAddress(port)) else Proxy(Proxy.Type.HTTP, InetSocketAddress(host, port))
        return this
    }

    /**
     * JVM specific implementation of build
     * Initializes the private Ktor client which is used for communication via http
     */
    @KtorExperimentalAPI
    actual fun build() {
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
                socketTimeout = socketTimeout
                connectTimeout = connectTimeout
                connectionRequestTimeout = connectionRequestTimeout

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

    /**
     * JVM specific implementation of url
     * Sets URL for requests. Must be provided before function build is executed.
     *
     * @param url String base URL for http requests
     * @see Url
     * @return this
     */
    actual fun url(url: Url): GenericHttpClient {
        this.url = url
        return this
    }

    /**
     * JVM specific implementation of port
     * Sends string content
     *
     * @param path path to be added to base URL
     * @param content string content to be sent
     * @param headers headers to be added to requests
     * @see HttpHeader
     * @return HttpResponse - null if no response is received
     */
    actual fun post(path: String, content: String, headers: HttpHeader): HttpResponse? {
        return this.executeRequest(HttpMethod.Post, path, content, headers)
    }

    /**
     * JVM specific implementation of port
     * Sends byte array content
     *
     * @param path path to be added to base URL
     * @param content byte array content to be sent
     * @param headers headers to be added to requests
     * @see HttpHeader
     * @return HttpResponse - null if no response is received
     */
    actual fun postByteArray(path: String, content: ByteArray, headers: HttpHeader): HttpResponse? {
        return this.executeRequest(HttpMethod.Post, path, content, headers)
    }

    /**
     * JVM specific implementation of port
     * Sends multipart content
     * @see MultipartBody
     *
     * @param path path to be added to base URL
     * @param content multipart content to be sent
     * @param headers headers to be added to requests
     * @see HttpHeader
     * @return HttpResponse - null if no response is received
     */
    actual fun postMultiPart(path: String, content: MultipartBody, header: HttpHeader): HttpResponse? {
        return this.executeRequest(HttpMethod.Post, path, content, header)
    }

    /**
     * JVM specific implementation of post
     * Sends graphQL content
     * @see GraphQlContent
     *
     * @param path path to be added to base URL
     * @param content multipart content to be sent
     * @param headers headers to be added to requests
     * @see HttpHeader
     * @return HttpResponse - null if no response is received
     */
    actual fun postGraphQl(path: String, content: GraphQlContent, headers: HttpHeader, contentIsJson: Boolean): HttpResponse? {
        return this.executeRequest(HttpMethod.Post, path, content, headers)
    }

    /**
     * JVM specific implementation of put
     * Sends string content
     *
     * @param path path to be added to base URL
     * @param content string content to be sent
     * @param headers headers to be added to requests
     * @see HttpHeader
     * @return HttpResponse - null if no response is received
     */
    actual fun put(path: String, content: String, headers: HttpHeader): HttpResponse? {
        return this.executeRequest(HttpMethod.Put, path, content, headers)
    }

    /**
     * JVM specific implementation of put
     * Sends byte array content
     *
     * @param path path to be added to base URL
     * @param content byte array content to be sent
     * @param headers headers to be added to requests
     * @see HttpHeader
     * @return HttpResponse - null if no response is received
     */
    actual fun putByteArray(path: String, content: ByteArray, headers: HttpHeader): HttpResponse? {
        return this.executeRequest(HttpMethod.Put, path, content, headers)
    }

    /**
     * JVM specific implementation of put
     * Sends multipart content
     * @see MultipartBody
     *
     * @param path path to be added to base URL
     * @param content multipart content to be sent
     * @param headers headers to be added to requests
     * @see HttpHeader
     * @return HttpResponse - null if no response is received
     */
    actual fun putMultiPart(path: String, content: MultipartBody, headers: HttpHeader): HttpResponse? {
        return this.executeRequest(HttpMethod.Put, path, content, headers)
    }

    /**
     * JVM specific implementation of put
     * Sends graphQL content
     * @see GraphQlContent
     *
     * @param path path to be added to base URL
     * @param content multipart content to be sent
     * @param headers headers to be added to requests
     * @see HttpHeader
     * @return HttpResponse - null if no response is received
     */
    actual fun putGraphQl(path: String, content: GraphQlContent, headers: HttpHeader, contentIsJson: Boolean): HttpResponse? {
        return this.executeRequest(HttpMethod.Put, path, content, headers)
    }

    /**
     * JVM specific implementation of delete
     * Send delete request
     *
     * @param path path to be added to base URL
     * @param headers headers to be added to requests
     * @see HttpHeader
     * @return HttpResponse - null if no response is received
     */
    actual fun delete(path: String, headers: HttpHeader): HttpResponse? {
        return this.executeRequest<Unit>(HttpMethod.Delete, path, null, headers)
    }

    /**
     * JVM specific implementation of get
     * Send delete request
     *
     * @param path path to be added to base URL
     * @param headers headers to be added to requests
     * @see HttpHeader
     * @return HttpResponse - null if no response is received
     */
    actual fun get(path: String, headers: HttpHeader): HttpResponse? {
        return this.executeRequest<Unit>(HttpMethod.Get, path, null, headers)
    }

    /**
     * This function takes method, path, content and header and sends it via the Ktor client to the path.
     * The function starts a blocking coroutine to send the request.
     *
     * @param useMethod this is the provided method (Get, Post, Put, Delete)
     * @param usePath the path the requests should be sent to
     * @param useContent the content type to be sent. Can be null, string, byte array or graphQL
     * @see GraphQlContent
     * @param useHeaders the headers to be added to the request
     * @see HttpHeader
     *
     * @return null if no request is received, HttpResponse for successful requests
     * @see HttpResponse
     */
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

    /**
     * Simple factory method to create the needed Ktor body type
     *
     * @param T type of content. Can be null, string, byte array or graphQL
     * @see GraphQlContent
     * @param content the content to be transformed
     *
     * @return the corresponding Ktpr body type
     */
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
