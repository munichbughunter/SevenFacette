package de.p7s1.qa.sevenfacette.http

import de.p7s1.qa.sevenfacette.http.config.AuthenticationFactory
import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.features.HttpSend
import io.ktor.client.features.auth.Auth
import io.ktor.client.features.cookies.AcceptAllCookiesStorage
import io.ktor.client.features.cookies.HttpCookies
import io.ktor.client.features.json.JacksonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.util.KtorExperimentalAPI
import org.apache.http.conn.ssl.NoopHostnameVerifier
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

        /**
         * Factory function - creates Ktor client and set values according to given configuration
         *
         * @param HttpConfig given configuration
         * @return Ktor client
         */
        @KtorExperimentalAPI
        @JvmStatic
        fun createClient(config: HttpConfig): GenericHttpClient {
            val client = HttpClient(Apache) {
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

                if(config.authentication != null) {
                    install(Auth){
                        providers.add(AuthenticationFactory(config.authentication!!).getAuthentication())
                    }
                }

                engine {
                    socketTimeout = config.socketTimeout
                    connectTimeout = config.connectionTimeout
                    connectionRequestTimeout = config.connectionRequestTimeout

                    customizeClient { // Trust all certificates
                        setSSLContext(
                                SSLContextBuilder
                                        .create()
                                        .loadTrustMaterial(TrustAllStrategy())
                                        .build()
                        )
                        setSSLHostnameVerifier(NoopHostnameVerifier())

                    }

                    if(config.proxy != null) {
                        proxy = createProxy(config.proxy)
                    }
                }
            }

            return GenericHttpClient().setClient(config.url, client)
        }

        /**
         * Creates proxy object if HttpProxy in configuration is not null
         *
         * @param host string host of proxy. Can be null. In this case only the port is used as proxy.
         * @param port string port of proxy
         * @return this
         */
        fun createProxy(proxy: HttpProxy?) =
             if (proxy?.host == null) Proxy(Proxy.Type.HTTP, InetSocketAddress(proxy!!.port)) else Proxy(Proxy.Type.HTTP, InetSocketAddress(proxy.host, proxy.port))
    }
}
