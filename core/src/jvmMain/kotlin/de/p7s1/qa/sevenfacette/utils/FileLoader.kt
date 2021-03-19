package de.p7s1.qa.sevenfacette.utils

import java.io.File
import java.io.IOException
import java.io.InputStream
import java.nio.file.Files
import java.util.*

actual class FileLoader {

    private var logger: Logger = Logger()

    fun loadFileFromResourceAsString(folder: String, fileName: String): String? {
        val file = getFile(folder, fileName)
        return getFileContentAsString(file)
    }

    fun loadFileFromResourceAsStream(folder: String, fileName: String): InputStream? {
        return loadFileContentAsInputStream(folder + fileName)
    }

    @Throws(IOException::class)
    private fun loadFileContentAsInputStream(resource: String): InputStream? =
            Thread.currentThread().contextClassLoader.getResourceAsStream(resource)
                    ?: resource::class.java.getResourceAsStream(resource)

    fun getFile(folder: String, fileName: String): File {
        return File(Objects.requireNonNull(FileLoader::class.java
                .classLoader
                .getResource("$folder/$fileName")).file)
    }

    private fun getFileContentAsString(file: File?): String? {
        val fileString: String
        fileString = try {
            String(Files.readAllBytes(file!!.toPath()))
        } catch (e: IOException) {
            logger.error("File not found: ${file!!.toPath()}")
            logger.error(e)
            throw RuntimeException(e)
        }
        return fileString
    }
}
