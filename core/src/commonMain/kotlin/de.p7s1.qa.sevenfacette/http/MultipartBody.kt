package de.p7s1.qa.sevenfacette.http

import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData

/**
 * Multipart body which can be sent via GenericHttpClient
 *
 * @property multipartData list of Multipartdata
 */
class MultipartBody {
    val multipartData = mutableListOf<MultiPartData<*>>()

    /**
     * Adds string content to multipart body
     *
     * @param name name of multipart body
     * @param content string content of multipart body
     * @return this
     */
    fun addStringPart(name: String, content: String): MultipartBody {
        multipartData.add(MultiPartData(name, content))
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
    fun addByteArrayPart(name: String, content: ByteArray): MultipartBody {
        multipartData.add(MultiPartData(name, content))
        return this
    }

    /**
     * Creates Ktor multipart body
     *
     * @return MultiPartFormDataContent by Ktor with multipart body parts of property multipartdata
     */
    fun create(): MultiPartFormDataContent = MultiPartFormDataContent (
            formData {
                multipartData.forEach {
                    when(it.value!!::class) {
                        String::class ->  append(it.name, it.value as String)
                        ByteArray::class -> append(it.name, it.value as ByteArray)
                        else -> println("Contenttype ${it.value::class} currently not implemented")
                    }
                }
            }
    )
}
