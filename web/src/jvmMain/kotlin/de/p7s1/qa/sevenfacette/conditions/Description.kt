package de.p7s1.qa.sevenfacette.conditions

import de.p7s1.qa.sevenfacette.utils.DiffExtractor

open class Description(val actual: Any,
                       val expected: Any,
                       var diff: Boolean = true) {

    open val reason: String? = "condition did not match"

    open var message = """%s
                expected: %s
                actual: %s
            """

    override fun toString(): String {
        if (!diff) {
            return message.format(reason, expected, actual)
        }

        val extractor = DiffExtractor(display(expected), display(actual))
        val prefix = extractor.compactPrefix()
        val suffix = extractor.compactSuffix()

        return message.format(reason, "$prefix${extractor.expectedDiff()}$suffix",
                "$prefix${extractor.actualDiff()}$suffix")
    }
}

fun display(value: Any?): String {
    return when (value) {
        null -> "null"
        is String -> "$value"
        is Class<*> -> value.name
        is Array<*> -> value.joinToString(prefix = "[", postfix = "]", transform = ::display)
        is Regex -> "/$value/"
        else -> value.toString()
    }
}
