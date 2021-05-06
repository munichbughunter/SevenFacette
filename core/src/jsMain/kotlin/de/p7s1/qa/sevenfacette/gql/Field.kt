package de.p7s1.qa.sevenfacette.gql

/**
 * TODO: Add Description
 *
 * @author Patrick Döring
 */
@ExperimentalJsExport
@JsName("Field")
@JsExport
class Field (private val name: String) {
    private var fields: MutableList<Field> = mutableListOf()

    fun addField(field: String): Field {
        if (field.isNotEmpty()) {
            fields.add(Field(field))
        }
        return this
    }

    override fun toString(): String {
        if (fields.size == 0) {
            return name
        }
        var str = "$name{"
        for (ra in fields) {
            str = "$str $ra"
        }
        str = "$str }"
        return str
    }
}
