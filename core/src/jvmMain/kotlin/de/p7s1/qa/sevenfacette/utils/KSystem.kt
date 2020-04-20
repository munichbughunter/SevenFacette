package de.p7s1.qa.sevenfacette.utils

actual class KSystem {
    actual companion object {

        @JvmStatic
        actual fun getEnv(name: String): String? = System.getenv(name)

        @JvmStatic
        actual fun getProperty(name: String): String? = System.getProperty(name)

    }
}
