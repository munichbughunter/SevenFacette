package de.p7s1.qa.sevenfacette.http

import de.p7s1.qa.sevenfacette.config.types.HttpClientConfig
import io.ktor.client.engine.*
import io.ktor.client.engine.js.*

class HttpClientFactory {
    @JsName("createHttpClient")
    fun createHttpClient(configHttp: HttpClientConfig): GenericHttpClient {
        val js = Js.create {
            proxy = if(configHttp.proxy?.host != null) null else ProxyBuilder.socks(configHttp.proxy?.host!!, configHttp.proxy!!.port)

        }
        return GenericHttpClient().setClient(configHttp, js)
    }
}