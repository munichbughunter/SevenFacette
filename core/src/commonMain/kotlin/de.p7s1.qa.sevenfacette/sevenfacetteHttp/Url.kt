package de.p7s1.qa.sevenfacette.sevenfacetteHttp

/**
 * URL for use with GenericHttpClient
 * @see GenericHttpClient
 * If the properties are provided the Url is created like protocol://baseUrl:port/path
 * If an URL is added it is split into separate parts so protocol, baseUrl, path and port are filled.
 *
 * @property protocol used protocol like http or https. Default is "http"
 * @property baseUrl used base URL for endpoint
 * @property path used path for endpoint. Default is an empty string
 * @property port used port for endpoints. Default is -1 which means no port will be added
 * @property url generated url
 *
 * @author Florian Pilz
 */
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

    /**
     * Add protocol to URL
     *
     * @param protocol string protocol
     */
    fun protocol(protocol: String) = apply { this.protocol = protocol }

    /**
     * Add base URL to URL
     *
     * @param baseUrl string base URL
     */
    fun baseUrl(baseUrl: String) = apply { this.baseUrl = baseUrl }

    /**
     * Add path to url
     *
     * @param path string path
     */
    fun path(path: String) = apply { this.path = path }

    /**
     *  Add port to URL
     *
     *  @param port used port
     *  @throws Exception if port is < -1 or > 65535
     */
    fun port (port: Int) = apply {
        if(port < -1 || port > 65535) throw Exception("Port is outside of range")
        this.port = port
    }

    /**
     * Splits url and fills properties
     *
     * @param url string url
     */
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

    /**
     * Generates URL out of provided properties
     *
     * @return computed URL
     */
    fun create(): String {
        var url = if (this.baseUrl.takeLast(1) == "/") {
            this.baseUrl.dropLast(1)
        } else {
            this.baseUrl
        }

        url = if (url.contains("://")) {
            url
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
