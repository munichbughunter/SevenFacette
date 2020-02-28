package de.p7s1.qa.sevenfacette.sevenfacetteHttp

class httpHeader {
    val header = mutableListOf<Pair<String, String>>()

    fun add(key: String, value: String): httpHeader {
        header.add(Pair(key, value))
        return this
    }
}
