package de.p7s1.qa.sevenfacette.http

expect open class GenericHttpClient {

    //suspend inline fun <reified T>get(url: String) = client.get<T>(url)

    fun post(url: String, content: String, header: List<Pair<String, String>>)
    fun postByteArray(url: String, content: ByteArray, header: List<Pair<String, String>>)

    fun put(url: String, content: String, header: List<Pair<String, String>>)
    fun putByteArray(url: String, content: ByteArray, header: List<Pair<String, String>>)

    //suspend inline fun <reified T> get(url: String): T
    fun get(url: String)

    suspend fun suspendedGet(url: String)
    //suspend fun get(url: String)
}
