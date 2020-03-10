package de.p7s1.qa.sevenfacette.sevenfacetteHttp

import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.http.content.PartData

class MultipartBody {
    val multipartData = mutableListOf<MultiPartData<*>>()

    fun addStringPart(name: String, content: String): MultipartBody {
        multipartData.add(MultiPartData(name, content))
        return this
    }

    fun addByteArrayPart(name: String, content: ByteArray): MultipartBody {
        multipartData.add(MultiPartData(name, content))
        return this
    }

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
