package de.p7s1.qa.sevenfacette.gql

/**
 * Inline fragment attributes.
 *
 * @author Patrick Döring
 */
class InlineFragmentAttributes(private val name: String) {

    private var inlineAttributes: MutableList<InlineFragmentAttributes> = mutableListOf()

    fun addInlineAttributes(vararg inlineAttr: String): InlineFragmentAttributes {
        if (inlineAttr.isNotEmpty()) {
            inlineAttr.forEach { inlineAttributes.add(InlineFragmentAttributes(it)) }
        }
        return this
    }

    fun addInlineAttributes(vararg inlineAttr: InlineFragmentAttributes): InlineFragmentAttributes {
        if (inlineAttr.isNotEmpty()) {
            inlineAttr.forEach { inlineAttributes.add(it) }
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
