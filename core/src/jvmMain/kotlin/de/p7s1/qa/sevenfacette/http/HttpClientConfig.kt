package de.p7s1.qa.sevenfacette.http

import io.ktor.util.KtorExperimentalAPI

/**
 * Configuration for specific Http client.
 * Receives configuration and returns a http client
 * Might be used if configuration files are added - by providing a client name the client specific configuration might be extracted.
 *
 * @author Florian Pilz
 */
class HttpClientConfig(var config: HttpConfig, var clientName: String = "") {

    @KtorExperimentalAPI
    fun createClient(): GenericHttpClient = HttpClientFactory.createClient(config)
}
