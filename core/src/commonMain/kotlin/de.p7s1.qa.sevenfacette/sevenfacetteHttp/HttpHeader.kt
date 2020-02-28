package de.p7s1.qa.sevenfacette.sevenfacetteHttp

class HttpHeader {
    val header = mutableListOf<Pair<String, String>>()

    fun add(key: String, value: String): HttpHeader {
        header.add(Pair(key, value))
        return this
    }
}
