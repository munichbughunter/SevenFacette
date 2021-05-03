package de.p7s1.qa.sevenfacette.gql

/**
 * Graphql result attributes.
 *
 * @author Patrick Döring
 */
class ResultAttributes(private val name: String) {

    private var resultAttributes: MutableList<ResultAttributes> = ArrayList()

    fun addResultAttributes(vararg resultAttr: String): ResultAttributes {
        if (resultAttr.isNotEmpty()) {
            for (str in resultAttr) {
                val ra = ResultAttributes(str)
                resultAttributes.add(ra)
            }
        }
        return this
    }

    fun addResultAttributes(vararg resultAttr: ResultAttributes): ResultAttributes {
        if (resultAttr.isNotEmpty()) {
            for (ra in resultAttr) {
                resultAttributes.add(ra)
            }
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
