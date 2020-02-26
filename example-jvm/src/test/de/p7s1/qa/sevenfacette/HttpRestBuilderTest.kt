package de.p7s1.qa.sevenfacette

import de.p7s1.qa.sevenfacette.http.HttpRestBuilder
import io.kotlintest.specs.AnnotationSpec
import io.kotlintest.specs.Test

internal class HttpRestBuilderTest : AnnotationSpec() {


    fun getBaseURI() {
    }

    fun getPath() {
    }

    fun getBody() {
    }


    fun getHeader() {
    }


    fun getContentType() {
    }

    @Test
    fun buildRequest() {
        val response = HttpRestBuilder("https://httpbin.org/get").executeGet()
        println(response)
    }
}
