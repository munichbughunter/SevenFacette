package de.p7s1.qa.sevenfacette.http

import io.ktor.client.statement.*
import io.ktor.util.*
import io.ktor.utils.io.charsets.MalformedInputException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.promise
import kotlin.js.Promise

actual class HttpResponse actual constructor(response: io.ktor.client.statement.HttpResponse) {
    val body : Promise<String> = GlobalScope.promise(context = Dispatchers.Default) {
        try {
            return@promise response.readText()
        } catch (e: MalformedInputException) {
            return@promise response.readBytes().toString()
        }
    }
    val status: Int = response.status.value
    val headers: Map<String, List<String>> = response.headers.toMap()
}
