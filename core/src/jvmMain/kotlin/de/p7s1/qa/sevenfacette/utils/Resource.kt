package de.p7s1.qa.sevenfacette.utils

import java.io.File
import java.nio.file.Paths

typealias FileObj = File

class Resource {

    companion object {
        private val USER_DIR = "user.dir"

        fun get(path: String): FileObj {
            val url = this.getFullPath(path)
            return File(url)
        }

        fun getFullPath(path: String): String {
            return Paths.get(System.getProperty(USER_DIR), path).toString()
        }
    }
}
