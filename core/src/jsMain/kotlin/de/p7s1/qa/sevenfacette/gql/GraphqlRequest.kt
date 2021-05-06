package de.p7s1.qa.sevenfacette.gql

/**
 * TODO: Add Description
 *
 * @author Patrick Döring
 */
@ExperimentalJsExport
@JsName("GraphqlRequest")
@JsExport
abstract class GraphqlRequest constructor(private var operationName: String) {
    private var fields: MutableList<Field> = mutableListOf()
    private var inlineFragmentFields: MutableList<InlineFragment> = mutableListOf()
    private var argument: Argument? = null

    fun addArgument(key: String?, `val`: Any?): GraphqlRequest {
        getArgument()!!.addArgument(key, `val`)
        return this
    }

    fun addArguments(arguments: Map<String, Any>): GraphqlRequest {
        arguments.forEach {
            (key, argValue) -> getArgument()!!.addArgument(key, argValue)
        }
        return this
    }

    fun getArgument(): Argument? {
        if (this.argument == null) {
            this.argument = Argument.build()
        }
        return this.argument
    }

    fun addInlineFragment(inlineField: String): GraphqlRequest {
        if (inlineField.isNotEmpty()) {
            inlineFragmentFields.add(InlineFragment(inlineField))
        }
        return this
    }

    fun addField(field: String) : GraphqlRequest {
        if (field.isNotEmpty()) {
            fields.add(Field(field))
        }
        return this
    }

    override fun toString() : String {
        var gql = operationName
        gql = gql.plus(getArgument().toString())
        if (fields.size > 0) {
            gql = addFieldAttr(gql)
        }

        if (inlineFragmentFields.size > 0) {
            gql = addInlineAttr(gql)
        }

        return gql
    }

    private fun addInlineAttr(_gql: String) : String {
        var gql = _gql
        inlineFragmentFields.forEach {
            gql = gql.plus(it.toString())
        }
        return gql
    }

    private fun addFieldAttr(_gql: String) : String {
        var gql = _gql
        var resultAttr = ""
        var first = true
        fields.forEach {
            if (first) first = false
            else {
                resultAttr = resultAttr.plus(" ")
            }
            resultAttr = resultAttr.plus(it.toString())
        }
        if (resultAttr != "") {
            gql = gql.plus("{")
            gql = gql.plus(resultAttr)
            gql = gql.plus("}")
        }
        return gql
    }
}
