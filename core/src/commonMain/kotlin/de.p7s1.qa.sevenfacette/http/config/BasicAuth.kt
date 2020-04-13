package de.p7s1.qa.sevenfacette.http.config

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Basic authentication type
 *
 * @property username
 * @property password
 * @property realm
 * @property sendWithoutRequest
 *
 *
 * @author Florian Pilz
 */
@Serializable
@SerialName("basic")
class BasicAuth(var username: String, var password: String) : Authentication() {
    var realm: String? = null
    var sendWithoutRequest: Boolean = true
}
