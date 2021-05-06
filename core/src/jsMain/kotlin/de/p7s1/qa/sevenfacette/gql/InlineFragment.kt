package de.p7s1.qa.sevenfacette.gql

/**
 * TODO: Add Description
 *
 * @author Patrick Döring
 */
@ExperimentalJsExport
@JsName("InlineFragment")
@JsExport
class InlineFragment (private val name: String) {
    private var inlineFields: MutableList<InlineFragment> = mutableListOf()

    fun addInlineField(inlineField: String): InlineFragment {
        if (inlineField.isNotEmpty()) {
            inlineFields.add(InlineFragment(inlineField))
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
