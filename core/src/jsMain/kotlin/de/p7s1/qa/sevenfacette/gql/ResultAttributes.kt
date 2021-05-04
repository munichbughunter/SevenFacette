package de.p7s1.qa.sevenfacette.gql

/**
 * TODO: Add Description
 *
 * @author Patrick Döring
 */
@ExperimentalJsExport
@JsName("ResultAttributes")
@JsExport
class ResultAttributes (private val name: String) {
    private var resultAttributes: MutableList<ResultAttributes> = mutableListOf()

    fun addResultAttributes(resultAttr: String): ResultAttributes {
        if (resultAttr.isNotEmpty()) {
            resultAttributes.add(ResultAttributes(resultAttr))
        }
        return this
    }

    override fun toString(): String {
        if (resultAttributes.size == 0) {
            return name
        }
        var str = "$name{"
        for (ra in resultAttributes) {
            str = "$str $ra"
        }
        str = "$str }"
        return str
    }
}

