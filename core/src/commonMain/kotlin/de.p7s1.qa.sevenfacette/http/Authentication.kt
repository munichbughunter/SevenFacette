package de.p7s1.qa.sevenfacette.http

import de.p7s1.qa.sevenfacette.utils.KSystem
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
        if(!values.containsKey("type")) {
            throw Exception("No type for authentication defined")
        }
        return when(values["type"]) {
            "basic" -> BasicAuthProvider(
                    values["username"] ?: "",
                    values["password"] ?: "",
                    values["realm"] ?: "",
                    values["sendWithoutRequest"] != "" && values["sendWithoutRequest"] == "true")
            else -> throw Exception("Type ${values["type"]} not supported")
        }
    }
}
