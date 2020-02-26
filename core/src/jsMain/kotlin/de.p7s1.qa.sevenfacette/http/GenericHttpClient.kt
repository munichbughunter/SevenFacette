package de.p7s1.qa.sevenfacette.http

actual open class GenericHttpClient {

    actual fun post(url: String, content: String, header: List<Pair<String, String>>) {
    }

    actual fun postByteArray(url: String, content: ByteArray, header: List<Pair<String, String>>) {
    }

    actual fun put(url: String, content: String,  header: List<Pair<String, String>>) {
    }

    actual fun putByteArray(url: String, content: ByteArray, header: List<Pair<String, String>>) {
    }

    actual fun get(url: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    actual suspend fun suspendedGet(url: String) {
    }


}
