package de.p7s1.qa.sevenfacette.http

import io.ktor.client.statement.HttpResponse

/**
 * Http response which can be used for test cases
 *
 * @param response Ktor reponse received in GenericHttpClient
 * @property body string body of http reponse
 * @property status http status of http response
 * @property headers Map of header-elements
 *
 * @author Florian Pilz
 */
expect class HttpResponse (response: HttpResponse) {}
