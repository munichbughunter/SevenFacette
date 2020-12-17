package de.p7s1.qa.sevenfacette.http

import de.p7s1.qa.sevenfacette.config.types.HttpClientConfig
import de.p7s1.qa.sevenfacette.http.auth.AuthenticationFactory
import de.p7s1.qa.sevenfacette.utils.DateTime
import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.engine.js.*
import io.ktor.client.features.*
import io.ktor.client.features.auth.*
import io.ktor.client.features.cookies.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.util.*
import kotlinx.coroutines.*
import kotlin.coroutines.Continuation
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.startCoroutine
import kotlin.js.Promise

@JsName("testCreateHttpClient")
fun testCreateHttpClient(configHttp: HttpClientConfig): GenericTestHttpClient {
    val js = Js.create {
        //proxy = if(configHttp.proxy?.host != null) null else ProxyBuilder.socks(configHttp.proxy?.host!!, configHttp.proxy!!.port)
    }
println("TEST CREATE HTTP CLIENT")
    return GenericTestHttpClient().setClient(configHttp, js)
}

class GenericTestHttpClient {

    private lateinit var client: HttpClient
    private lateinit var url: Url

    @KtorExperimentalAPI
    @JsName("setClient")
    fun setClient(config: HttpClientConfig, factory: HttpClientEngine):GenericTestHttpClient {
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

        println("CREATED CLIENT")

        return this
    }

    /**
     * JS specific implementation of get
     * Send delete request
     *
     * @param path path to be added to base URL
     * @param headers headers to be added to requests
     * @see HttpHeader
     * @return HttpResponse - null if no response is received
     */
    @JsName("get")
    suspend fun get(path: String, headers: HttpHeader? = null): Promise<HttpTestResponse>
    {
        return HttpTestClientExecutor.executeRequest(this.client, HttpMethod.Get, this.url, path, null, headers)
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
}

data class HttpTestResponse(
        val body : String = "Hallo",
        val Status : Int = 0
)


class HttpTestClientExecutor {
    companion object {
        suspend fun executeRequest(
                client: HttpClient,
                useMethod: HttpMethod,
                useUrl: Url,
                usePath: String,
                useBody: Any?,
                useHeaders: HttpHeader?
        ): Promise<HttpTestResponse> {
            var facetteResponse: HttpTestResponse? = null
            val fullPath = useUrl.path(usePath).create()

            println("Sending a ${useMethod.value} request to $fullPath with ${if(useBody == null) "no" else ""} content")

            var usedBody: Any? = null
            usedBody = useBody
            println("Body == $usedBody")

            // Doku für JS: https://youtrack.jetbrains.com/issue/KT-22228


            return GlobalScope.promise(context = Dispatchers.Default) {
                println("HELLO FROM THE SCOPE")
                delay(5000)
                println("HELLO FROM THE SCOPE - AGAIN")
                return@promise HttpTestResponse()
            }
        }
    }
}