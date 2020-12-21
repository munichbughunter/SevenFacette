package de.p7s1.qa.sevenfacette.utils

import process

actual class FileLoader {
    @JsName("readFileAsString")
    fun readFileAsString(path: String): String =
            Files.getAsText(path)

    @JsName("readFileAsByteArray")
    fun readFileAsByteArray(path: String): ByteArray =
            Files.getAsByteArray(path)

    @JsName("readRelativeFileAsString")
    fun readRelativeAsString(relativePath: String): String =
            readFileAsString(getPath(relativePath))

    @JsName("readRelativeAsByteArray")
    fun readRelativeAsByteArray(relativePath: String): ByteArray =
            readFileAsByteArray(getPath(relativePath))

    @JsName("getPath")
    fun getPath(folder: String): String =
            if (folder[0] == '/') process.cwd() + folder else process.cwd() + "/" + folder
}
