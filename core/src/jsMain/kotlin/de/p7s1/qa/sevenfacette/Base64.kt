package de.p7s1.qa.sevenfacette

actual object Base64Factory {
    actual fun createEncoder(): Base64Encoder = JsBase64Encoder
}

object JsBase64Encoder : Base64Encoder {
    override fun encode(src: ByteArray): ByteArray {
        val buffer = js("Buffer").from(src)
        val string = buffer.toString("base64") as String
        return ByteArray(string.length) { string[it].toByte() }
    }
}

fun sayHello() = "REFACTORED PROJECT: Function sayHello: Here I am, rocking like a hurricane - I am the greatest"
