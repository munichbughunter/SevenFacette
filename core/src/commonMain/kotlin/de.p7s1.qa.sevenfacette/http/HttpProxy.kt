package de.p7s1.qa.sevenfacette.http

import kotlinx.serialization.Serializable

/**
 * Config used for generic rest clients
 *
 * @author Florian Pilz
 */
@Serializable
class HttpProxy {
    var host: String? = null
    var port: Int = -1
}
