package de.p7s1.qa.sevenfacette.http

object ContentTypes {
    object Application {
        val any = ContentType("application", "*")
        val atom = ContentType("application", "atom+xml")
    }
}

data class ContentType(val contentType: String, val contentSubtype: String) {
    fun parse(): io.ktor.http.ContentType =
            io.ktor.http.ContentType(contentType, contentSubtype)
}
