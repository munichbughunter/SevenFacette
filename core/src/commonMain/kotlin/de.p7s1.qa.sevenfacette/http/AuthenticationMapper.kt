package de.p7s1.qa.sevenfacette.http

import de.p7s1.qa.sevenfacette.http.config.Authentication
import de.p7s1.qa.sevenfacette.http.config.BasicAuth
import io.ktor.client.features.auth.AuthProvider
import io.ktor.client.features.auth.providers.BasicAuthProvider
import io.ktor.client.features.auth.providers.DigestAuthProvider
import mu.KotlinLogging

/**
 * Maps SevenFacette authentication types to Ktor authentication
 *
 * @author Florian Pilz
 */

private val logger = KotlinLogging.logger {}
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
                    logger.debug { "Mapping basic auth" }
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
