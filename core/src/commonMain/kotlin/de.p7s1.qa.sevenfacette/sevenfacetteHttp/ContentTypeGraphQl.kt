package de.p7s1.qa.sevenfacette.sevenfacetteHttp

import io.ktor.http.ContentType

/**
 * Adds content type "graphql" to list of content types
 *
 * @author Florian Pilz
 */
val ContentType.Application.GraphQl: ContentType
    get() = ContentType("application", "graphql")
