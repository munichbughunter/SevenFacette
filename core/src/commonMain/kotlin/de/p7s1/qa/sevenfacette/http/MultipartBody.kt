package de.p7s1.qa.sevenfacette.http

import de.p7s1.qa.sevenfacette.utils.Logger
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import kotlin.js.JsName

/**
 * Multipart body which can be sent via GenericHttpClient
 *
 * @property multipartData list of Multipartdata
 */
class MultipartBody {
    private var logger: Logger = Logger()
    val multipartData = mutableListOf<MultiPartData<*>>()

    /**
     * Adds string content to multipart body
     *
     * @param name name of multipart body
     * @param content string content of multipart body
     * @return this
     */
    @JsName("addStringPart")
    fun addStringPart(name: String, content: String): MultipartBody {
        logger.debug("Adding string content with name: $name to multipart body")
        if(name == null ||content == null)
            throw Exception("Multipart body needs key and value") // needed for JS
        multipartData.add(MultiPartData(name, content, null, null))
        return this
    }

    /**
     * Adds bytearray content to multipart body
     *
     * @param name name of multipart body
     * @param content bytearray content of multipart body
     * @return this
     *
     * @author Florian Pilz
     */
    @JsName("addByteArrayPart")
    fun addByteArrayPart(name: String, content: ByteArray): MultipartBody {
        logger.debug("Adding byte array content with name: $name to multipart body")
        if(name == null ||content == null)
            throw Exception("Multipart body needs key and value") // needed for JS
        multipartData.add(MultiPartData(name, content, null, null))
        return this
    }

    /**
     * Adds file item bytearray content to multipart body
     *
     * @param name name of multipart body
     * @param fileName name of the file
     * @param content bytearray content of multipart body
     * @param contenttype contenttype of multipart body
     * @return this
     *
     * @author Patrick Döring
     */
    @JsName("addFileItemPart")
    fun addFileItemPart(name: String, fileName: String, content: ByteArray, contenttype: CONTENTTYPES): MultipartBody {
        logger.debug("Adding file item with name: $fileName to multipart body")
        if(name == null ||content == null)
            throw Exception("Multipart body needs key and value") // needed for JS
        multipartData.add(MultiPartData(name, content, fileName, contenttype.name))
        return this
    }

    /**
     * Creates Ktor multipart body
     *
     * @return MultiPartFormDataContent by Ktor with multipart body parts of property multipartdata
     */
    @JsName("create")
    fun create(): MultiPartFormDataContent = MultiPartFormDataContent (
            formData {
                multipartData.forEach {
                    if (it.fileName.isNullOrEmpty()) {
                        when(it.value!!::class) {
                            String::class ->  append(it.name, it.value as String)
                            ByteArray::class -> append(it.name, it.value as ByteArray)
                            else -> logger.error("Content type ${it.value::class} currently not implemented")
                        }
                    } else {
                        append(it.name, it.value as ByteArray, Headers.build {
                            append(HttpHeaders.ContentType, it.contentType.toString())
                            append(HttpHeaders.ContentDisposition, " filename=${it.fileName}")
                        })
                    }
                }
            }
    )
}
