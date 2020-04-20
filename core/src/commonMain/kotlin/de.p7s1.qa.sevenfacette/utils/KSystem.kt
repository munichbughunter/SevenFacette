package de.p7s1.qa.sevenfacette.utils

expect class KSystem {
    companion object {
        fun getEnv(name: String): String?

        fun getProperty(name: String): String?
    }
}
