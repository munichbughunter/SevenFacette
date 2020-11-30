package de.p7s1.qa.sevenfacette.http

import io.ktor.client.HttpClient
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.http.content.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.js.JsName

/**
 * Class for http requests which needs to be implemented in js- and jvm-target
 * Documentation for functions is described in the corresponding target implementations.
 *
 * @author Florian Pilz
 */
class GenericHttpClient() {

    private lateinit var client: HttpClient
    private lateinit var url: Url

    @JsName("setClient")
    fun setClient(url: Url, client: HttpClient):GenericHttpClient {
        //logger.info { "SET CLIENT" }
        this.client = client
        this.url = url
        return this
    }

    /**
     * JS specific implementation of port
     * Sends string content
     *
     * @param path path to be added to base URL
     * @param content string content to be sent
     * @param headers headers to be added to requests
     * @see HttpHeader
     * @return HttpResponse - null if no response is received
     */
    @JsName("post")
    fun post(path: String, content: String, contentType: CONTENTTYPES, headers: HttpHeader): HttpResponse? =
            this.executeRequest(HttpMethod.Post, path, content, contentType, headers)

    /**
     * JS specific implementation of port
     * Sends byte array content
     *
     * @param path path to be added to base URL
     * @param content byte array content to be sent
     * @param headers headers to be added to requests
     * @see HttpHeader
     * @return HttpResponse - null if no response is received
     */
    @JsName("postByteArray")
    fun post(path: String, content: ByteArray, headers: HttpHeader): HttpResponse? =
            this.executeRequest(HttpMethod.Post, path, content, useHeaders = headers)

    /**
     * JS specific implementation of port
     * Sends multipart content
     * @see MultipartBody
     *
     * @param path path to be added to base URL
     * @param content multipart content to be sent
     * @param headers headers to be added to requests
     * @see HttpHeader
     * @return HttpResponse - null if no response is received
     */
    @JsName("postMultipartBody")
    fun post(path: String, content: MultipartBody, header: HttpHeader): HttpResponse? =
            this.executeRequest(HttpMethod.Post, path, content, useHeaders = header)

    /**
     * JS specific implementation of put
     * Sends string content
     *
     * @param path path to be added to base URL
     * @param content string content to be sent
     * @param headers headers to be added to requests
     * @see HttpHeader
     * @return HttpResponse - null if no response is received
     */
    @JsName("put")
    fun put(path: String, content: String, contentType: CONTENTTYPES, headers: HttpHeader): HttpResponse? =
            this.executeRequest(HttpMethod.Put, path, content, contentType, headers)

    /**
     * JS specific implementation of put
     * Sends byte array content
     *
     * @param path path to be added to base URL
     * @param content byte array content to be sent
     * @param headers headers to be added to requests
     * @see HttpHeader
     * @return HttpResponse - null if no response is received
     */
    @JsName("putByteArray")
    fun put(path: String, content: ByteArray, headers: HttpHeader): HttpResponse? =
            this.executeRequest(HttpMethod.Put, path, content, useHeaders = headers)

    /**
     * JS specific implementation of put
     * Sends multipart content
     * @see MultipartBody
     *
     * @param path path to be added to base URL
     * @param content multipart content to be sent
     * @param headers headers to be added to requests
     * @see HttpHeader
     * @return HttpResponse - null if no response is received
     */
    @JsName("putMultipartBody")
    fun put(path: String, content: MultipartBody, headers: HttpHeader): HttpResponse? =
            this.executeRequest(HttpMethod.Put, path, content, useHeaders = headers)

    /**
     * JS specific implementation of delete
     * Send delete request
     *
     * @param path path to be added to base URL
     * @param headers headers to be added to requests
     * @see HttpHeader
     * @return HttpResponse - null if no response is received
     */
    @JsName("delete")
    fun delete(path: String, headers: HttpHeader): HttpResponse? =
            this.executeRequest<Unit>(HttpMethod.Delete, path, useHeaders = headers)

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
    fun get(path: String, headers: HttpHeader): HttpResponse? =
            this.executeRequest<Unit>(HttpMethod.Get, path, useHeaders = headers)

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
    private inline fun <reified T> executeRequest(useMethod: HttpMethod, usePath: String, useContent: T? = null, contentType: CONTENTTYPES? = null, useHeaders: HttpHeader): HttpResponse? {
        var facetteResponse: HttpResponse? = null
        val fullPath = this.url .path(usePath).create()

        //logger.info("Sending a ${useMethod.value} request to $fullPath with ${if(useContent == null) "no" else ""} content")

        var usedBody: Any? = null
        if (useContent != null) {
            usedBody = getBody(useContent, contentType)
            //logger.info("Body == $usedBody")
        } else {
            //logger.info("With no body")
        }

        GlobalScope.launch {
            try {

                facetteResponse = HttpResponse(client.request {

                    url(fullPath)

                    method = useMethod

                    if (usedBody != null) {
                        body = usedBody
                    }
                    userAgent("SevenFacette")

                    useHeaders.header.forEach {
                        headers.append(it.first, it.second)
                    }
                })
            } catch (e: Exception) {
                //logger.error(e.message)
            }
        }.onJoin

        if(facetteResponse == null) throw Exception("No response received")
        //logger.info { "Response http status == ${facetteResponse?.status}" }
        //logger.info { "Response headers == ${facetteResponse?.headers}" }
        //logger.info { "Response body == ${facetteResponse?.body}" }
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
    private inline fun <reified T> getBody(content: T, contentType: CONTENTTYPES?): Any {
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
