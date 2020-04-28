package de.p7s1.qa.sevenfacette.utils

actual class KSystem {
    actual companion object {

        @JvmStatic
        actual fun getEnv(key: String): String? = System.getenv(key)

        @JvmStatic
        actual fun getProperty(key: String): String? = System.getProperty(key)

        @JvmStatic
        actual fun setEnv(key: String, value: String) = System.getenv().set(key, value)

        @JvmStatic
        actual fun setProperty(key: String, value: String) {
            System.setProperty(key, value)
        }
    }
}
