package de.p7s1.qa.sevenfacette.config

data class Configuration (val services: List<RestServicesConfiguration>)

data class RestServicesConfiguration (val name: String, val baseUrl: String, val headers: Map<String, String>)

expect class BasicAuth(serviceName: String, username: String?, password: String?) : RestServiceAuth
expect class LDAPAuth(username: String, password: String) : RestServiceAuth
