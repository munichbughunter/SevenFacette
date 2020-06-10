package de.p7s1.qa.sevenfacette.http

import de.p7s1.qa.sevenfacette.config.types.FacetteConfig
import de.p7s1.qa.sevenfacette.config.types.HttpClientConfig
import de.p7s1.qa.sevenfacette.http.auth.AuthenticationFactory
import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.features.HttpSend
import io.ktor.client.features.auth.Auth
import io.ktor.client.features.auth.providers.basic
import io.ktor.client.features.cookies.AcceptAllCookiesStorage
import io.ktor.client.features.cookies.HttpCookies
import io.ktor.client.features.json.JacksonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.util.KtorExperimentalAPI
import org.apache.http.Header
import org.apache.http.HeaderElement
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

        @JvmStatic
        fun createClient(clientName: String): GenericHttpClient = createClient(
                FacetteConfig.http?.getClient(clientName)!!
        )

        @JvmStatic
        fun createClient(clientName: String, authentication: MutableMap<String, String>): GenericHttpClient {
            authenticationProvidedByUser = true
            val config = FacetteConfig.http?.getClient(clientName)
            config?.authentication = authentication
            return createClient(config!!)
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
                    socketTimeout = configHttp.socketTimeout
                    connectTimeout = configHttp.connectionTimeout
                    connectionRequestTimeout = configHttp.connectionRequestTimeout

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
