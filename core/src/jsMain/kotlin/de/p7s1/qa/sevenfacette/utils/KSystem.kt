package de.p7s1.qa.sevenfacette.utils

import NodeJS.Process
import NodeJS.get
import NodeJS.set

external val process: Process

actual class KSystem {

    actual companion object {

        actual fun getEnv(key: String): String? {
            return process.env[key]
        }

        actual fun setEnv(key: String, value: String) {
            process.env[key] = value
        }

        actual fun getProperty(key: String): String? =
                getEnv(key)

        actual fun setProperty(key: String, value: String) =
                setEnv(key, value)
    }
}
