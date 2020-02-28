package de.p7s1.qa.sevenfacette.config

actual class BasicAuth actual constructor(val serviceName: String, var username: String?, var password: String?) : RestServiceAuth {
    init {
        if(username == null) username = System.getProperty("${serviceName}-${username}")
        if(password == null) password = System.getProperty("${serviceName}-${password}")
    }
}

actual class LDAPAuth actual constructor(username: String, password: String) : RestServiceAuth
