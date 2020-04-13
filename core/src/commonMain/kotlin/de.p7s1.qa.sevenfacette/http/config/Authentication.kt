package de.p7s1.qa.sevenfacette.http.config

import io.ktor.client.features.auth.AuthProvider
import io.ktor.client.features.auth.providers.BasicAuthProvider
import kotlinx.serialization.Polymorphic
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule

/**
 * Interface for authentication types
 *
 * @author Florian Pilz
 */
@Serializable
@Polymorphic
abstract class Authentication

val authenticationModule = SerializersModule {
    polymorphic(Authentication::class) {
        BasicAuth::class with BasicAuth.serializer()
    }
}




class AuthenticationFactory(val values: MutableMap<String, String>) {
    fun getAuthentication(): AuthProvider {
        if(!values.containsKey("type")) {
            throw Exception("No type for authentication defined")
        }
        return when(values["type"]) {
            "basic" -> BasicAuthProvider(values["username"]!!, values["password"]!!, values["realm"], values.containsKey("sendWithoutRequest") && values["sendWithoutRequest"] == "true")
            else -> throw Exception("Type ${values["type"]} not supported")
        }
    }

}
