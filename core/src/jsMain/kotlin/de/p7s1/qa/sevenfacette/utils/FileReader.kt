package de.p7s1.qa.sevenfacette.utils

import process;

class FileReader {
    @JsName("readFileAsString")
    fun readFileAsString(path: String): String =
        Files.getAsText(path)

    @JsName("readFileAsByteArray")
    fun readFileAsByteArray(path: String): ByteArray =
        Files.getAsByteArray(path)

    @JsName("getPath")
    fun getPath(folder: String): String =
        if (folder[0] == '/') process.cwd() + folder else process.cwd() + "/" + folder
}