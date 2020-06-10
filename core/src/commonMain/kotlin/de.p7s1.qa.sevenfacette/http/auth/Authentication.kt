package de.p7s1.qa.sevenfacette.http.auth

import io.ktor.client.features.auth.AuthProvider
import io.ktor.client.features.auth.providers.BasicAuthProvider

/**
 * Maps the configuration data to the Ktor authentication
 *
 * @property values Map of configuration data
 * @property clientName String given name of the client. Used to access values of environment variables
 *
 * @author Florian Pilz
 */
class AuthenticationFactory(private val values: MutableMap<String, String>) {

    /**
     * Creates the Ktor authentication provider according to the given type
     *
     * @return AuthProvider provided by Ktor
     */
    fun getAuthentication(): AuthProvider {
        println(values["sendWithoutRequest"] != "")
        val test = values["sendWithoutRequest"]?.toBoolean() ?: true
        println("TESt $test")

        println(values["sendWithoutRequest"] != "" && values["sendWithoutRequest"] == "false")
        println(values["sendWithoutRequest"] == "false")

        if(!values.containsKey("type")) {
            throw Exception("No type for authentication defined")
        }
        return when(values["type"]) {
            "basic" -> BasicAuthProvider(
                    values["username"] ?: "",
                    values["password"] ?: "",
                    values["realm"] ?: "",
                    values["sendWithoutRequest"]?.toBoolean() ?: true
            )
            "oauth1" -> OAuthProvider(
                    OAuth1Config(
                        values["consumer_key"]?:"",
                        values["access_token"]?:"",
                        values["secret"]?:"",
                        OAuthSignatureMethod.PLAINTEXT,
                        sendWithoutRequest = values["sendWithoutRequest"]?.toBoolean() ?: true
                    )
            )
            "oauth2" -> OAuthProvider(
                    OAuth2Config(
                        values["bearer"]?:"",
                        sendWithoutRequest = values["sendWithoutRequest"]?.toBoolean() ?: true
                    )
            )
            else -> throw Exception("Type ${values["type"]} not supported")
        }
    }
}
