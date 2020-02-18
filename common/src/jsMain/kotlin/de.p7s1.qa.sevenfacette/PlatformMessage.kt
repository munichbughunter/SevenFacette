package de.p7s1.qa.sevenfacette


actual class Platform {
    actual val greetingMethod: kotlin.String
        get() = "Console.log" //To change initializer of created properties use File | Settings | File Templates.
    actual val name: kotlin.String
        get() = "JS" //To change initializer of created properties use File | Settings | File Templates.
}

actual fun getPlatform(): Platform {
    return Platform() //To change body of created functions use File | Settings | File Templates.
}