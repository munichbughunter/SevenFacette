package de.p7s1.qa.sevenfacette.config

actual class BasicAuth actual constructor(serviceName: String, username: String?, password: String?) : RestServiceAuth
actual class LDAPAuth actual constructor(username: String, password: String) : RestServiceAuth
