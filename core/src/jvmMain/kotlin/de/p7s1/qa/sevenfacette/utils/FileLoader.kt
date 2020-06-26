package de.p7s1.qa.sevenfacette.utils

import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.net.URL
import java.nio.file.Files
import java.util.Objects
import javax.imageio.ImageIO

class FileLoader {

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

    private fun getFile(folder: String, fileName: String): File? {
        return File(Objects.requireNonNull<URL>(FileLoader::class.java
                .classLoader
                .getResource("$folder/$fileName")).file)
    }

    private fun getFileContentAsString(file: File?): String? {
        val fileString: String
        fileString = try {
            String(Files.readAllBytes(file!!.toPath()))
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
        return fileString
    }
}
