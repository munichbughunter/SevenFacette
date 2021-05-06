package de.p7s1.qa.sevenfacette.gql

/**
 * Inline fragment attributes.
 *
 * @author Patrick Döring
 */
class InlineFragment(private val name: String) {

    private var inlineFields: MutableList<InlineFragment> = mutableListOf()

    fun addInlineField(vararg inlineField: String): InlineFragment {
        if (inlineField.isNotEmpty()) {
            inlineField.forEach { inlineFields.add(InlineFragment(it)) }
        }
        return this
    }

    fun addInlineField(vararg inlineField: InlineFragment): InlineFragment {
        if (inlineField.isNotEmpty()) {
            inlineField.forEach { inlineFields.add(it) }
        }
        return this
    }

    override fun toString(): String {
        if (inlineFields.size == 0) {
            return name
        }
        var str = "...on $name{"
        inlineFields.forEach { str = "$str $it"}
        return "$str }"
    }
}
