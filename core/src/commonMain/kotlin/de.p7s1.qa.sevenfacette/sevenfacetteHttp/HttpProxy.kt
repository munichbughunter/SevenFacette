package de.p7s1.qa.sevenfacette.sevenfacetteHttp

/**
 * Class to hand over to GenericHttpClient to use proxies
 * @see GenericHttpClient
 *
 * @property url URL for proxy
 * @property port port for proxy
 *
 * @author Florian Pilz
 */
class HttpProxy(val url: String, val port: String)
