package de.p7s1.qa.sevenfacette.sevenfacetteHttp

import io.ktor.client.request.forms.formData
import io.ktor.http.content.PartData
import kotlin.jvm.JvmStatic

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

    fun create(): List<PartData> = formData {
        multipartData.forEach {
            when(it.value!!::class) {
                String::class ->  append(it.name, it.value as String)
                ByteArray::class -> append(it.name, it.value as ByteArray)
                else -> println("Contenttype ${it.value::class} currently not implemented")
            }
        }
    }
}
