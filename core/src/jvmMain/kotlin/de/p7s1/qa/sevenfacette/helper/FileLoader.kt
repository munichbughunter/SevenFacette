package de.p7s1.qa.sevenfacette.helper

import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.UncheckedIOException

class FileLoader {

    fun loadInputStreamFromResource(folder: String, fileName: String): InputStream {
        return getInputStream(folder, fileName)
    }


    @Throws(IOException::class)
    fun getResourceFiles(path: String, fileName: String): List<String> = getResourceAsStream(path+fileName).use{
        return if(it == null) emptyList()
        else BufferedReader(InputStreamReader(it)).readLines()
    }

    private fun getResourceAsStream(resource: String): InputStream? =
            Thread.currentThread().contextClassLoader.getResourceAsStream(resource)
                    ?: resource::class.java.getResourceAsStream(resource)

    private fun getInputStream(folder: String, fileName: String): InputStream {
        return try {
            File(folder + fileName).inputStream()
        } catch (e: IOException) {
            throw UncheckedIOException(e)
        }
    }
}