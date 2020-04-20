package de.p7s1.qa.sevenfacette.config

import de.p7s1.qa.sevenfacette.http.config.HttpClientConfig

actual object FacetteConfig {
    actual var httpClients: List<HttpClientConfig>
        get() = TODO("Not yet implemented")
        set(value) {}
    actual var custom: Map<String, Any>
        get() = TODO("Not yet implemented")
        set(value) {}
}
