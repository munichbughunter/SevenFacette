package de.p7s1.qa.sevenfacette.sevenfacetteHttp

import io.ktor.client.features.auth.AuthProvider
import io.ktor.client.features.auth.providers.BasicAuthProvider
import io.ktor.client.features.auth.providers.DigestAuthProvider

/**
 * Maps SevenFacette authentication types to Ktor authentication
 *
 * @author Florian Pilz
 */
class AuthenticationMapper {
    companion object {

        /**
         * Performs the mapping
         *
         * @param authentication SevenFacette authentication
         * @return AuthProvider by Ktor
         */
        fun map(authentication: Authentication?): AuthProvider {
            when(authentication!!::class) {
                BasicAuth::class -> {
                    authentication as BasicAuth
                    return BasicAuthProvider(
                            authentication.username,
                            authentication.password,
                            authentication.realm,
                            authentication.sendWithoutRequest)
                }
                else -> {
                    return DigestAuthProvider("", "", "")
                }
            }
        }
    }
}
