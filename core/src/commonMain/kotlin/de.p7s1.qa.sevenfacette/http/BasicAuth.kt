package de.p7s1.qa.sevenfacette.http


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
class BasicAuth(var username: String, var password: String) : Authentication {
    var realm: String? = null
    var sendWithoutRequest: Boolean = true
}
