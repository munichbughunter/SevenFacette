package de.p7s1.qa.sevenfacette.sevenfacetteHttp


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
class BasicAuth(val username: String, val password: String, val realm: String? = null, val sendWithoutRequest: Boolean = true) : Authentication
