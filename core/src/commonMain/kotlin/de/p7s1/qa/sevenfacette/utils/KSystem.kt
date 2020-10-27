package de.p7s1.qa.sevenfacette.utils

expect class KSystem {
    companion object {
        fun getEnv(key: String): String?
        fun setEnv(key: String, value: String)
    }
}
