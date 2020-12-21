package de.p7s1.qa.sevenfacette.http

import de.p7s1.qa.sevenfacette.config.ConfigReader
import io.ktor.client.engine.*
import io.ktor.client.engine.js.*
import io.ktor.util.*

@KtorExperimentalAPI
@JsName("createHttpClient")
fun createHttpClient(clientName: String): GenericHttpClient {
    val config = ConfigReader.getHttpClientConfig(clientName)
            ?: throw Exception("Client $clientName not found in configuration")

    val js = Js.create {
        if(config.proxy?.host != null) {
            proxy =  ProxyBuilder.socks(config.proxy?.host!!, config.proxy!!.port)
        }
    }
    return GenericHttpClient().setClient(config, js)
}

