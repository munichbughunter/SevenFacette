package de.p7s1.qa.sevenfacette.utils

import java.io.File
import java.net.URL

/**
 * JVM specific implementation of Files
 * This project is created with kotlin multiplatform. Kotlin common does not contain a Files object.
 * So this implementation is created to use Files as parameters for functions created in commons target.
 *
 * @author Florian Pilz
 */
actual class Files {

    actual companion object {
        /**
         * JVM specific implementation
         * Returns byte array content provided file
         *
         * @param path string path of file
         *
         * @return content of file as byte array
         */
        @JvmStatic
        actual fun getAsByteArray(path: String): ByteArray = File(path).readBytes()

        /**
         * JVM specific implementation
         * Returns string content of provided file
         *
         * @param path string path of file
         *
         * @return content of file as string
         */
        @JvmStatic
        actual fun getAsText(path: String): String = File(path).readText()

        /**
         * Returns list of strings per line of provided file
         *
         * @param path string path of file
         *
         * @return content of file as list of strings
         */
        @JvmStatic
        fun getResourceStream(path: String): List<String> = Files::class.java.getResourceAsStream(path).bufferedReader().readLines()

        @JvmStatic
        actual fun getResource(fileName: String): String? = this::class.java.classLoader.getResource(fileName)?.toString()

        @JvmStatic
        actual fun getRessourceText(fileName: String): String? = URL(getResource(fileName)).readText()
    }
}
