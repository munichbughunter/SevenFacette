package de.p7s1.qa.sevenfacette

actual class Platform {
    actual val greetingMethod: kotlin.String
        get() = "System.out.println"
    actual val name: kotlin.String
        get() = "JVM"
}

actual fun getPlatform(): Platform {
    return Platform() //To change body of created functions use File | Settings | File Templates.
}
