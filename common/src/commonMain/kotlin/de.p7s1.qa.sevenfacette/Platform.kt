package de.p7s1.qa.sevenfacette

expect class Platform {
    val greetingMethod: String
    val name: String
}

expect fun getPlatform(): Platform
