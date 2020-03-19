package de.p7s1.qa.sevenfacette.sevenfacetteHttp

import io.ktor.http.ContentType

val ContentType.Application.GraphQl: ContentType
    get() = ContentType("application", "graphql")
