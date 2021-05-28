package de.p7s1.qa.sevenfacette.gql

/**
 * TODO: Add Description
 *
 * @author Patrick Döring
 */
@ExperimentalJsExport
@JsName("Argument")
@JsExport
class Argument private constructor() : HashMap<String?, Any?>() {

    fun addArgument(key: String?, obj: Any?): Argument {
        put(key, obj)
        return this
    }

    override fun toString(): String {
        val keys: MutableSet<String?> = keys
        if (keys.size == 0) {
            return ""
        }
        var stringVal = "("
        val connect = ','
        for (key in keys) {
            stringVal = stringVal + key + ":" + packVal(get(key)) + connect
        }
        val last = stringVal[stringVal.length - 1]
        if (connect == last) {
            stringVal = stringVal.substring(0, stringVal.length - 1)
        }
        stringVal = "$stringVal)"
        return stringVal
    }

    private fun packVal(`val`: Any?): String {
        if (`val` == null) {
            return "null"
        }
        if (`val` is Int
            || `val` is Boolean
            || `val` is Float
            || `val` is Double
        ) {
            return `val`.toString()
        }
        if (`val` is Enum<*>) {
            return `val`.name
        }
        return "\\\"" + `val`.toString() + "\\\""
    }

    companion object {
        fun buildByMap(map: Map<*, *>): Argument {
            val requestParameter = build()
            map.forEach { (any, any2) -> requestParameter[any as String?] = any2 }

            return requestParameter
        }

        fun build(): Argument {
            return Argument()
        }
    }
}
