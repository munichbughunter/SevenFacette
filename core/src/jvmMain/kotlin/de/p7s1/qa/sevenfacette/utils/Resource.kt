package de.p7s1.qa.sevenfacette.utils

import java.io.File
import java.nio.file.Paths

typealias FileObj = File

class Resource {
    companion object {
        fun get(path: String): FileObj {
            val url = this.getFullPath(path)
            return File(url)
        }

        fun getFullPath(path: String): String {
            // TODO: use constant
            return Paths.get(System.getProperty("user.dir"), path).toString()
        }
    }
}
