package de.p7s1.qa.sevenfacette.gql

/**
 * Graphql result attributes.
 *
 * @author Patrick Döring
 */
class Field(private val name: String) {

    private var fields: MutableList<Field> = ArrayList()

    fun addField(vararg field: String): Field {
        if (field.isNotEmpty()) {
            for (str in field) {
                val ra = Field(str)
                fields.add(ra)
            }
        }
        return this
    }

    fun addField(vararg field: Field): Field {
        if (field.isNotEmpty()) {
            for (ra in field) {
                fields.add(ra)
            }
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
