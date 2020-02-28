package de.p7s1.qa.sevenfacette.utils

expect class FileObj

expect class Resource () {
    companion object {
        fun getFullPath(path: String): String
        fun get(path: String): FileObj
    }
}
