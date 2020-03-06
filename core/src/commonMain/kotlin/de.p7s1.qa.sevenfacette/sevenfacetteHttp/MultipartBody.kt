package de.p7s1.qa.sevenfacette.sevenfacetteHttp

import io.ktor.client.request.forms.formData
import io.ktor.http.content.PartData
import kotlin.jvm.JvmStatic

class MultipartBody {
    companion object {
        val multipartData = mutableListOf<MultiPartData<*>>()

        @JvmStatic
        inline fun <reified T> add(name: String, content: T) {
            multipartData.add(MultiPartData(name, content))
        }

        @JvmStatic
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
}
