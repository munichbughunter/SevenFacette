package de.p7s1.qa.sevenfacette.utils

expect class Files {
    fun getAsByteArray(path: String): ByteArray
    fun getAsText(path: String): String
}
