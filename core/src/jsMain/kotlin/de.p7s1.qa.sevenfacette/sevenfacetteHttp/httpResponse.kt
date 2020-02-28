package de.p7s1.qa.sevenfacette.sevenfacetteHttp

import io.ktor.client.statement.HttpResponse

actual class httpResponse actual constructor(response: HttpResponse) {
    actual val body: String
        get() = TODO("not implemented yet")

    actual val status: Int
        get() = TODO("not implemented yet")

    actual val headers: Map<String, List<String>>
        get() = TODO("not implemented yet")
}
