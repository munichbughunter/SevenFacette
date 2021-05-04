package de.p7s1.qa.sevenfacette.gql

/**
 * TODO: Add Description
 *
 * @author Patrick Döring
 */
@ExperimentalJsExport
@JsName("InlineFragmentAttributes")
@JsExport
class InlineFragmentAttributes (private val name: String) {
    private var inlineAttributes: MutableList<InlineFragmentAttributes> = mutableListOf()

    fun addInlineAttributes(inlineAttr: String): InlineFragmentAttributes {
        if (inlineAttr.isNotEmpty()) {
            inlineAttributes.add(InlineFragmentAttributes(inlineAttr))
        }
        return this
    }

    override fun toString(): String {
        if (inlineAttributes.size == 0) {
            return name
        }
        var str = "...on $name{"
        inlineAttributes.forEach { str = "$str $it"}
        return "$str }"
    }
}
