package de.p7s1.qa.sevenfacette.sevenfacetteHttp

// ToDo: documentation and constants

class Url {
    var protocol: String = "http"
        private set
    lateinit var baseUrl: String
        private set
    var path: String = ""
        private set
    var port: Int = -1
        private set
    var url: String = ""
        private set

    fun protocol(protocol: String) = apply { this.protocol = protocol }
    fun baseUrl(baseUrl: String) = apply { this.baseUrl = baseUrl }
    fun path(path: String) = apply { this.path = path }
    fun port (port: Int) = apply {
        // ToDo: Add logging
        require(port >= 0 && port <=65535, { println("Port is outside of range")})
        this.port = port
    }

    fun url(url: String) {
        val protocolParts = url.split("://")
        var urlString: String
        if (protocolParts.size > 0) {
            this.protocol = protocolParts[0]
            urlString = protocolParts[1]
        } else {
            urlString = protocolParts[1]
        }

        val urlParts = urlString.split("/")
        val portParts = urlParts[0].split(":")
        this.baseUrl = portParts[0]
        if (portParts.size > 0) {this.port = portParts[1].toInt() }

        if (urlParts.size > 0) {
            this.path = urlParts.drop(1).joinToString("/")
        }
    }

    fun create(): String {
        var url = if (this.baseUrl.takeLast(1) == "/") {
            this.baseUrl.dropLast(1)
        } else {
            this.baseUrl
        }

        url = if (url.contains("://")) {
            this.url
        } else {
            "${this.protocol}://${url}"
        }

        if (this.port > -1 ) url = "${url}:${this.port}"

        if (this.path.isNotEmpty()) {
            url = if (this.path.take(1) == "/") {
                "${url}${path}"
            } else {
                "${url}/${path}"
            }
        }
        return url
    }
}