package de.p7s1.qa.sevenfacette.http

import de.p7s1.qa.sevenfacette.kafka.KRecord
import de.p7s1.qa.sevenfacette.screenplay.Ability
import io.ktor.util.KtorExperimentalAPI
import kotlin.js.Promise

/**
 * TODO: Add Description
 *
 * @author Patrick Döring
 */
@KtorExperimentalAPI
@ExperimentalJsExport
@JsName("HttpClientAbility")
open class HttpClientAbility(private val client: GenericHttpClient) {

    private var abilities = mutableListOf<Abilities>()
    private var httpAbility = client
        get() {
            return field
        }
        set(value) {
            field = value
        }

    @JsName("withConfiguration")
    fun withConfiguration(name: String) : Array<Abilities> {
        httpAbility = createHttpClient(name)
        abilities.add(Abilities(name, httpAbility))
        return abilities.toTypedArray()
    }

    @JsName("post")
    suspend fun post(path: String, content: String, contentType: CONTENTTYPES = CONTENTTYPES.APPLICATION_JSON, headers: HttpHeader? = null): Promise<HttpResponse> =
        client.post(path, content, contentType, headers)

    @JsName("postByteArray")
    suspend fun post(path: String, content: ByteArray, headers: HttpHeader? = null): Promise<HttpResponse> =
        client.post(path, content, headers)

    @JsName("postMultipart")
    suspend fun post(path: String, content: MultipartBody, headers: HttpHeader? = null): Promise<HttpResponse> =
        client.post(path, content, headers)

    @JsName("patch")
    suspend fun patch(path: String, content: String, contentType: CONTENTTYPES = CONTENTTYPES.APPLICATION_JSON, headers: HttpHeader? = null): Promise<HttpResponse> =
        client.patch(path, content, contentType, headers)

    @JsName("patchByteArray")
    suspend fun patch(path: String, content: ByteArray, headers: HttpHeader? = null): Promise<HttpResponse> =
        client.patch(path, content, headers)

    @JsName("patchMultipart")
    suspend fun patch(path: String, content: MultipartBody, headers: HttpHeader? = null): Promise<HttpResponse> =
        client.patch(path, content, headers)

    @JsName("put")
    suspend fun put(path: String, content: String, contentType: CONTENTTYPES = CONTENTTYPES.APPLICATION_JSON, headers: HttpHeader? = null): Promise<HttpResponse> =
        client.put(path, content, contentType, headers)

    @JsName("putByteArray")
    suspend fun put(path: String, content: ByteArray, headers: HttpHeader? = null): Promise<HttpResponse> =
        client.put(path, content, headers)

    @JsName("putMultipart")
    suspend fun put(path: String, content: MultipartBody, headers: HttpHeader? = null): Promise<HttpResponse> =
        client.put(path, content, headers)

    @JsName("delete")
    suspend fun delete(path: String, headers: HttpHeader? = null): Promise<HttpResponse> =
        client.delete(path, headers)

    @JsName("get")
    suspend fun get(path: String, headers: HttpHeader? = null): Promise<HttpResponse> =
        client.get(path, headers)

    @JsName("head")
    suspend fun head(path: String, headers: HttpHeader? = null): Promise<HttpResponse> =
        client.head(path, headers)
}
