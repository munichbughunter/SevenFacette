package de.p7s1.qa.sevenfacette.http

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.promise
import kotlin.js.Promise

/**
 * TODO: Add Description
 *
 * @author Patrick Döring
 */
suspend fun executeGet(url: String): String {
    val client = HttpClient()
    return client.get<String>(url)
}

fun executeGetRequest(url: String): Promise<String> =
        GlobalScope.promise {
            executeGet(url)
        }
