package de.p7s1.qa.sevenfacette.http.config

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
class AuthenticationFactory(private val values: MutableMap<String, String>, private val clientName: String? = null) {

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
                    getValue("username"),
                    getValue("password"),
                    getValue("realm"),
                    getValue("sendWithoutRequest") != "" && getValue("sendWithoutRequest") == "true")
            else -> throw Exception("Type ${values["type"]} not supported")
        }
    }

    /**
     * This function returns the configuration value. It searches
     * 1. in the environment properties. If no value is provided
     * 2. in the configuration file
     * This gives the possibility to combine values from the environment properties (e.g. user and pass) and the configuration file.
     * If a client name is provided the function searches for clientName_key in the environment properties
     *
     * @param key String key to access the configuration
     * @return String value of configuration
     */
    private fun getValue(key: String): String {
        val envKey = if(clientName == null) key else "${clientName}_$key"
        return if(!KSystem.getEnv(envKey).isNullOrEmpty()) {
            KSystem.getEnv(envKey) ?: ""
        } else {
            values[key] ?: ""
        }
    }
}
