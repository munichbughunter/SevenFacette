package de.p7s1.qa.sevenfacette.http.auth

import de.p7s1.qa.sevenfacette.utils.DateTime
import io.ktor.client.features.auth.AuthProvider
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.http.HttpHeaders
import io.ktor.http.auth.AuthScheme
import io.ktor.http.auth.HeaderValueEncoding
import io.ktor.http.auth.HttpAuthHeader
import io.ktor.util.InternalAPI
import io.ktor.util.generateNonce

sealed class OAuthConfig(open var sendWithoutRequest: Boolean = true)
data class OAuth1Config @OptIn(InternalAPI::class) constructor(
        var oauth_consumer_key: String,
        var oauth_token: String,
        var oauth_signature: String,
        var OAuthSignatureMethod: OAuthSignatureMethod,
        var oauth_nonce: String = generateNonce(),
        var oauth_version: String = "1.0",
        var oauth_timestamp: Long = DateTime.now(),
        override var sendWithoutRequest: Boolean = true
): OAuthConfig()
data class OAuth2Config(
        var bearer: String,
        override var sendWithoutRequest: Boolean = true
): OAuthConfig()

class OAuthProvider(val config: OAuthConfig): AuthProvider {

    override val sendWithoutRequest: Boolean = config.sendWithoutRequest

    override suspend fun addRequestHeaders(request: HttpRequestBuilder) {
        when(config) {
            is OAuth1Config -> {
                request.headers[HttpHeaders.Authorization] = constructOAuth1Value()
            }
            is OAuth2Config -> {
                request.headers.append(HttpHeaders.Authorization, constructOAuth2Header())
            }
        }
    }

    override fun isApplicable(auth: HttpAuthHeader): Boolean = true

    private fun constructOAuth2Header(): String {
        return HttpAuthHeader.Single("Bearer", (config as OAuth2Config).bearer).render()
    }

    private fun constructOAuth1Value(): String {
        val oConfig = config as OAuth1Config
        return HttpAuthHeader.Parameterized (
                AuthScheme.OAuth,
                linkedMapOf<String, String>().apply {
                    this["oauth_consumer_key"] = oConfig.oauth_consumer_key
                    this["oauth_token"] = oConfig.oauth_token
                    this["oauth_signature_method"] = oConfig.OAuthSignatureMethod.toString()
                    this["oauth_nonce"] = oConfig.oauth_nonce
                    this["oauth_signature"] = oConfig.oauth_signature
                    this["oauth_version"] = oConfig.oauth_version
                    this["oauth_timestamp"] = oConfig.oauth_timestamp.toString()
                },
                HeaderValueEncoding.QUOTED_ALWAYS
        ).render()
    }
}
