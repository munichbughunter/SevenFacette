package de.p7s1.qa.sevenfacette.http

import io.ktor.client.statement.*
import io.ktor.client.statement.HttpResponse
import io.ktor.util.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

actual class HttpResponse actual constructor(response: HttpResponse) {
    actual val body: String
    actual val status: Int
    actual val headers: Map<String, List<String>>

    /**
     * On initialization the Ktor response is used to fill the classes properties.
     * The body is returned in an asynchronous way so it is inside a runBlocking block
     */
    init {
        var bodyTemp: String = ""
        GlobalScope.launch {
            bodyTemp = response.readText()
        }.onJoin

        this.body = bodyTemp
        this.status = response.status.value
        this.headers = response.headers.toMap()
    }
}
