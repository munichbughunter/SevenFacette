@file: JvmName("Screenshots")

package de.p7s1.qa.sevenfacette.core

import java.io.File
import java.util.concurrent.ConcurrentHashMap

val screenshots: MutableMap<Long, File> = ConcurrentHashMap(4)

fun getLatestScreenshot(): File? {
    return screenshots[Thread.currentThread().id]
}

