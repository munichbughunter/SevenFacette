package de.p7s1.qa.sevenfacette.sevenfacetteHttp

/**
 * Multipart data Used for MultipartBody
 * @see MultipartBody
 *
 * @property T type of value. Can be string or bytearray
 * @property name name of multipart body part
 * @property value value of multipart body part
 *
 * @author Florian Pilz
 */
data class MultiPartData <T>(val name: String, val value: T)
