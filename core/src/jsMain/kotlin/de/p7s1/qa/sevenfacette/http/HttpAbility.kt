package de.p7s1.qa.sevenfacette.http

import io.ktor.util.KtorExperimentalAPI

/**
 * TODO: Add Description
 *
 * @author Patrick Döring
 */
@KtorExperimentalAPI
@ExperimentalJsExport
@JsName("HttpAbility")
data class HttpAbility (var key: String?, var value: GenericHttpClient) {}
