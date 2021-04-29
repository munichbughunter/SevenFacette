package de.p7s1.qa.sevenfacette.http

import de.p7s1.qa.sevenfacette.http.CONTENTTYPES.APPLICATION_JSON
import de.p7s1.qa.sevenfacette.screenplay.Ability

/**
 * Http client ability to execute post, put, patch, delete, get, head requests.
 *
 * @author Patrick Döring
 */
class HttpClientAbility(private val client: GenericHttpClient) : Ability {

    override fun name() : String {
        return abilityName
    }

    companion object {
        var abilityName : String = ""
        fun withConfiguration(name: String) : HttpClientAbility {
            abilityName = name
            return HttpClientAbility(HttpClientFactory.createClient(name))
        }
    }

    fun post(
        path: String,
        content: String,
        contentType: CONTENTTYPES = CONTENTTYPES.APPLICATION_JSON,
        headers: HttpHeader? = null
    ): HttpResponse? {
        return client.post(path, content, contentType, headers)
    }

    fun post(
        path: String,
        content: ByteArray,
        headers: HttpHeader? = null
    ) : HttpResponse? {
        return client.post(path, content, headers)
    }

    fun post(
        path: String,
        content: MultipartBody,
        headers: HttpHeader? = null
    ) : HttpResponse? {
        return client.post(path, content, headers)
    }

    fun put(
        path: String,
        content: String,
        contentType: CONTENTTYPES = CONTENTTYPES.APPLICATION_JSON,
        headers: HttpHeader? = null
    ) : HttpResponse? {
        return client.put(path, content, contentType, headers)
    }

    fun put(
        path: String,
        content: ByteArray,
        headers: HttpHeader? = null
    ) : HttpResponse? {
        return client.put(path, content, headers)
    }

    fun put(
        path: String,
        content: MultipartBody,
        headers: HttpHeader? = null
    ) : HttpResponse? {
        return client.put(path, content, headers)
    }

    fun patch(
        path: String,
        content: String,
        contentType: CONTENTTYPES = APPLICATION_JSON,
        headers: HttpHeader? = null
    ) : HttpResponse? {
        return client.patch(path, content, contentType, headers)
    }

    fun patch(
        path: String,
        content: ByteArray,
        headers: HttpHeader? = null
    ) : HttpResponse? {
        return client.patch(path, content, headers)
    }

    fun patch(
        path: String,
        content: MultipartBody,
        headers: HttpHeader? = null
    ) : HttpResponse? {
        return client.patch(path, content, headers)
    }

    fun delete(
        path: String,
        headers: HttpHeader? = null
    ) : HttpResponse? {
        return client.delete(path, headers)
    }

    fun get(
        path: String,
        headers: HttpHeader? = null
    ) : HttpResponse? {
        return client.get(path, headers)
    }

    fun head(
        path: String,
        headers: HttpHeader? = null
    ) : HttpResponse? {
        return client.head(path, headers)
    }
}
