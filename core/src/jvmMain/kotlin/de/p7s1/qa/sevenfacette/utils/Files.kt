package de.p7s1.qa.sevenfacette.utils

import java.io.File

actual class Files {
    actual fun getAsByteArray(path: String): ByteArray = File(path).readBytes()
    actual fun getAsText(path: String): String = File(path).readText()
    fun getResourceStream(path: String): List<String> = Files::class.java.getResourceAsStream(path).bufferedReader().readLines()
}
