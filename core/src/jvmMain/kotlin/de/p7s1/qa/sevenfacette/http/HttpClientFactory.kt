package de.p7s1.qa.sevenfacette.http

import de.p7s1.qa.sevenfacette.config.ConfigReader
import de.p7s1.qa.sevenfacette.config.types.FacetteConfig
import de.p7s1.qa.sevenfacette.config.types.HttpClientConfig
import io.ktor.client.engine.*
import io.ktor.client.engine.apache.*
import io.ktor.util.*
import org.apache.http.conn.ssl.TrustAllStrategy
import org.apache.http.ssl.SSLContextBuilder

/**
 * Create Ktor client and set values according to given configuration
 *
 * @author Florian Pilz
 */
class HttpClientFactory {
    companion object {

        private var authenticationProvidedByUser: Boolean = false

        private fun createConfig(clientName: String): HttpClientConfig {

            val config = ConfigReader.getHttpClientConfig(clientName)
                    ?: throw Exception("Client $clientName not found in configuration")
            if(config.connectionRequestTimeout==null) config.connectionRequestTimeout = FacetteConfig.http?.connectionRequestTimeout
            if(config.connectionTimeout==null) config.connectionTimeout = FacetteConfig.http?.connectionTimeout
            if(config.socketTimeout==null) config.socketTimeout = FacetteConfig.http?.socketTimeout
            if(config.proxy==null) config.proxy = FacetteConfig.http?.proxy
            return config
        }

        @JvmStatic
        fun createClient(clientName: String): GenericHttpClient = createClient(
                createConfig(clientName)
        )

        @JvmStatic
        fun createClient(clientName: String, authentication: MutableMap<String, String>): GenericHttpClient {
            authenticationProvidedByUser = true
            val config = createConfig(clientName)
            config.authentication = authentication
            return createClient(config)
        }

        /**
         * Factory function - creates Ktor client and set values according to given configuration
         *
         * @param HttpConfig given configuration
         * @return Ktor client
         */
        @KtorExperimentalAPI
        @JvmStatic
        fun createClient(configHttp: HttpClientConfig): GenericHttpClient {
            val apache = Apache.create {
                customizeClient {
                    if(configHttp.proxy != null) {
                        proxy =  ProxyBuilder.socks(configHttp.proxy!!.host!!, configHttp.proxy!!.port)
                    }
                    socketTimeout = configHttp.socketTimeout!!
                    connectTimeout = configHttp.connectionTimeout!!
                    connectionRequestTimeout = configHttp.connectionRequestTimeout!!

                    setSSLContext(
                            SSLContextBuilder
                                    .create()
                                    .loadTrustMaterial(TrustAllStrategy())
                                    .build()
                    )
                }
            }

            return GenericHttpClient().setClient(configHttp, apache)
        }
    }
}
