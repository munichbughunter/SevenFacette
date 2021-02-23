package de.p7s1.qa.sevenfacette.http

import de.p7s1.qa.sevenfacette.config.types.HttpClientConfig
import de.p7s1.qa.sevenfacette.http.auth.AuthenticationFactory
import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.features.*
import io.ktor.client.features.auth.*
import io.ktor.client.features.cookies.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.promise
import kotlin.js.Promise

actual class GenericHttpClient {
    private lateinit var client: HttpClient
    private lateinit var url: Url

    @KtorExperimentalAPI
    @JsName("setClient")
    fun setClient(config: HttpClientConfig, factory: HttpClientEngine): GenericHttpClient {
        println("CREATING CLIENT")
        this.client = HttpClient(factory) {
            expectSuccess = false

            install(HttpCookies) {
                storage = AcceptAllCookiesStorage()
            }

            install(JsonFeature) {
                serializer = KotlinxSerializer()
            }

            install(HttpSend){
                maxSendCount = 2
            }

            if(config.authentication != null) {
                install(Auth) {
                    providers.add(AuthenticationFactory(config.authentication!!).getAuthentication())
                }
            }
        }
        client.engineConfig

        this.url = config.url!!

        return this
    }

    /**
     * Sends string content
     *
     * @param path path to be added to base URL
     * @param content string content to be sent
     * @param headers headers to be added to requests
     * @see HttpHeader
     * @return HttpResponse - null if no response is received
     */
    @JsName("post")
    suspend fun post(path: String, content: String, contentType: CONTENTTYPES = CONTENTTYPES.APPLICATION_JSON, headers: HttpHeader? = null): Promise<HttpResponse> =
            executeRequest(this.client, HttpMethod.Post, this.url, path, getBody(content, contentType), headers)

    /**
     * Sends byte array content
     *
     * @param path path to be added to base URL
     * @param content byte array content to be sent
     * @param headers headers to be added to requests
     * @see HttpHeader
     * @return HttpResponse - null if no response is received
     */
    @JsName("postByteArray")
    suspend fun post(path: String, content: ByteArray, headers: HttpHeader? = null): Promise<HttpResponse> =
            executeRequest(this.client, HttpMethod.Post, this.url, path, getBody(content), headers)

    /**
     * Sends multipart content
     * @see MultipartBody
     *
     * @param path path to be added to base URL
     * @param content multipart content to be sent
     * @param headers headers to be added to requests
     * @see HttpHeader
     * @return HttpResponse - null if no response is received
     */
    @JsName("postMultipart")
    suspend fun post(path: String, content: MultipartBody, headers: HttpHeader? = null): Promise<HttpResponse> =
            executeRequest(this.client, HttpMethod.Post, this.url, path, getBody(content), headers)

    /**
     * Sends string content
     *
     * @param path path to be added to base URL
     * @param content string content to be sent
     * @param headers headers to be added to requests
     * @see HttpHeader
     * @return HttpResponse - null if no response is received
     */
    @JsName("patch")
    suspend fun patch(path: String, content: String, contentType: CONTENTTYPES = CONTENTTYPES.APPLICATION_JSON, headers: HttpHeader? = null): Promise<HttpResponse> =
            executeRequest(this.client, HttpMethod.Patch, this.url, path, getBody(content, contentType), headers)

    /**
     * Sends byte array content
     *
     * @param path path to be added to base URL
     * @param content byte array content to be sent
     * @param headers headers to be added to requests
     * @see HttpHeader
     * @return HttpResponse - null if no response is received
     */
    @JsName("patchByteArray")
    suspend fun patch(path: String, content: ByteArray, headers: HttpHeader? = null): Promise<HttpResponse> =
            executeRequest(this.client, HttpMethod.Patch, this.url, path, getBody(content), headers)

    /**
     * Sends multipart content
     * @see MultipartBody
     *
     * @param path path to be added to base URL
     * @param content multipart content to be sent
     * @param headers headers to be added to requests
     * @see HttpHeader
     * @return HttpResponse - null if no response is received
     */
    @JsName("patchMultipart")
    suspend fun patch(path: String, content: MultipartBody, headers: HttpHeader? = null): Promise<HttpResponse> =
            executeRequest(this.client, HttpMethod.Patch, this.url, path, getBody(content), headers)

    /**
     * Sends string content
     *
     * @param path path to be added to base URL
     * @param content string content to be sent
     * @param headers headers to be added to requests
     * @see HttpHeader
     * @return HttpResponse - null if no response is received
     */
    @JsName("put")
    suspend fun put(path: String, content: String, contentType: CONTENTTYPES = CONTENTTYPES.APPLICATION_JSON, headers: HttpHeader? = null): Promise<HttpResponse> =
            executeRequest(this.client, HttpMethod.Put, this.url, path, getBody(content, contentType), headers)

    /**
     * Sends byte array content
     *
     * @param path path to be added to base URL
     * @param content byte array content to be sent
     * @param headers headers to be added to requests
     * @see HttpHeader
     * @return HttpResponse - null if no response is received
     */
    @JsName("putByteArray")
    suspend fun put(path: String, content: ByteArray, headers: HttpHeader? = null): Promise<HttpResponse> =
            executeRequest(this.client, HttpMethod.Put, this.url, path, getBody(content), headers)

    /**
     * Sends multipart content
     * @see MultipartBody
     *
     * @param path path to be added to base URL
     * @param content multipart content to be sent
     * @param headers headers to be added to requests
     * @see HttpHeader
     * @return HttpResponse - null if no response is received
     */
    @JsName("putMultipart")
    suspend fun put(path: String, content: MultipartBody, headers: HttpHeader? = null): Promise<HttpResponse> =
            executeRequest(this.client, HttpMethod.Put, this.url, path, getBody(content), headers)

    /**
     * Send delete request
     *
     * @param path path to be added to base URL
     * @param headers headers to be added to requests
     * @see HttpHeader
     * @return HttpResponse - null if no response is received
     */
    @JsName("delete")
    suspend fun delete(path: String, headers: HttpHeader? = null): Promise<HttpResponse> =
            executeRequest(this.client, HttpMethod.Delete, this.url, path, null, headers)

    /**
     * Send delete request
     *
     * @param path path to be added to base URL
     * @param headers headers to be added to requests
     * @see HttpHeader
     * @return HttpResponse - null if no response is received
     */
    @JsName("get")
    suspend fun get(path: String, headers: HttpHeader? = null): Promise<HttpResponse> =
            executeRequest(this.client, HttpMethod.Get, this.url, path, null, headers)

    /**
     * Send head request
     *
     * @param path path to be added to base URL
     * @param headers headers to be added to requests
     * @see HttpHeader
     * @return HttpResponse - null if no response is received
     */
    @JsName("head")
    suspend fun head(path: String, headers: HttpHeader? = null): Promise<HttpResponse> =
        executeRequest(this.client, HttpMethod.Head, this.url, path, null, headers)

    /**
     * Simple factory method to create the needed Ktor body type
     *
     * @param T type of content. Can be null, string, byte array or graphQL
     * @see GraphQlContent
     * @param content the content to be transformed
     *
     * @return the corresponding Ktpr body type
     */
    private inline fun <reified T> getBody(content: T, contentType: CONTENTTYPES? = null): Any {
        return when(T::class) {
            String::class, Unit::class -> TextContent(content as String, parseContentType(contentType))
            ByteArray::class -> ByteArrayContent(content as ByteArray)
            MultipartBody::class -> (content as MultipartBody).create()
            else -> throw Error("Content not supported")
        }
    }

    private fun parseContentType(contentType: CONTENTTYPES?): io.ktor.http.ContentType =
            io.ktor.http.ContentType(contentType?.contentType?.contentType ?: "application", contentType?.contentType?.contentSubtype ?: "*")

    suspend fun executeRequest(
            client: HttpClient,
            useMethod: HttpMethod,
            useUrl: Url,
            usePath: String,
            useBody: Any?,
            useHeaders: HttpHeader?
    ): Promise<HttpResponse> {
        val fullPath = useUrl.path(usePath).create()

        println("Sending a ${useMethod.value} request to $fullPath with ${if(useBody == null) "no" else ""} content")

        var usedBody: Any? = null
        usedBody = useBody
        println("Body == $usedBody")

        return GlobalScope.promise(context = Dispatchers.Default) {
            val response = client.request<io.ktor.client.statement.HttpResponse> {
                url(fullPath)

                method = useMethod

                if (useBody != null) {
                    body = useBody
                }

                userAgent("SevenFacette")

                if(useHeaders != null) {
                    useHeaders.header.forEach {
                        headers.append(it.first, it.second)
                    }
                }
            }
            return@promise HttpResponse(response)
        }
    }
}
