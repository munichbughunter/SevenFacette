package de.p7s1.qa.sevenfacette.utils

import java.io.File
import java.nio.file.Paths

actual typealias FileObj = File

actual class Resource actual constructor() {
    actual companion object {
        actual fun get(path: String): FileObj {
            val url = this.getFullPath(path)
            return File(url)
        }

        actual fun getFullPath(path: String): String {
            // TODO: use constant
            return Paths.get(System.getProperty("user.dir"), path).toString()
        }
    }
}
