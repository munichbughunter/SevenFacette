package de.p7s1.qa.sevenfacette.utils

expect class KSystem {
    companion object {
        fun getEnv(key: String): String?

        fun getProperty(key: String): String?

        fun setEnv(key: String, value: String)

        fun setProperty(key: String, value: String)
    }
}
