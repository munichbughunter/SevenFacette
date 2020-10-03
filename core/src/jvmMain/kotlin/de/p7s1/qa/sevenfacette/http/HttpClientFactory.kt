package de.p7s1.qa.sevenfacette.http

import de.p7s1.qa.sevenfacette.config.types.FacetteConfig
import de.p7s1.qa.sevenfacette.config.types.HttpClientConfig
import de.p7s1.qa.sevenfacette.http.auth.AuthenticationFactory
import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.client.features.*
import io.ktor.client.features.auth.*
import io.ktor.client.features.cookies.*
import io.ktor.client.features.json.*
import io.ktor.util.*
import org.apache.http.conn.ssl.TrustAllStrategy
import org.apache.http.ssl.SSLContextBuilder
import java.net.InetSocketAddress
import java.net.Proxy

/**
 * Create Ktor client and set values according to given configuration
 *
 * @author Florian Pilz
 */
class HttpClientFactory {
    companion object {

        private var authenticationProvidedByUser: Boolean = false

        private fun createConfig(clientName: String): HttpClientConfig {
            val config = FacetteConfig.http?.getClient(clientName)
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

                if(configHttp.proxy != null) {
                    proxy = createProxy(configHttp.proxy)
                }
            }

            val client = HttpClient(apache) {
                expectSuccess = false

                install(HttpCookies) {
                    storage = AcceptAllCookiesStorage()
                }

                install(JsonFeature) {
                    serializer = JacksonSerializer()
                }

                install(HttpSend){
                    maxSendCount = 2
                }

                if(configHttp.authentication != null) {
                    install(Auth) {
                        providers.add(AuthenticationFactory(configHttp.authentication!!).getAuthentication())
                    }
                }
            }
            client.engineConfig
            return GenericHttpClient().setClient(configHttp.url!!, client)
        }

        /**
         * Creates proxy object if HttpProxy in configuration is not null
         *
         * @param host string host of proxy. Can be null. In this case only the port is used as proxy.
         * @param port string port of proxy
         * @return this
         */
        @JvmStatic
        fun createProxy(proxy: HttpProxy?) =
                if (proxy?.host == null) Proxy(Proxy.Type.HTTP, InetSocketAddress(proxy!!.port)) else Proxy(Proxy.Type.HTTP, InetSocketAddress(proxy.host, proxy.port))
    }
}
