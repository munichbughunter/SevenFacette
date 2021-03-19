package de.p7s1.qa.sevenfacette.utils

import kotlin.jvm.JvmStatic

/**
 * Create target specific type file
 * This project is created with kotlin multiplatform. The commons part does not know any file type.
 * So to be able to create functions with file parameters this Files class is created.
 *
 * @author Florian Pilz
 */
expect class Files {
    companion object {
        /**
         * Create target specific byte array file
         *
         * @param path path of files
         * @return bytearray content of file
         */
        fun getAsByteArray(path: String): ByteArray

        /**
         * Create target specific string file
         *
         * @param path path of files
         * @return string content of file
         */
        fun getAsText(path: String): String

        fun getResource(fileName: String): String?

        fun getResourceText(fileName: String): String?
    }
}
