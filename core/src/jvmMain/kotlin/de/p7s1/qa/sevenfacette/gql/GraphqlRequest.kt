package de.p7s1.qa.sevenfacette.gql

/**
 * Graphql request.
 *
 * @author Patrick Döring
 */
abstract class GraphqlRequest constructor(private var operationName: String) {

    private var fields: MutableList<Field> = mutableListOf()
    private var inlineFragmentFields: MutableList<InlineFragment> = mutableListOf()
    private var argument: Argument? = null

    fun addArgument(key: String?, `val`: Any?): GraphqlRequest {
        getArgument()!!.addArgument(key, `val`)
        return this
    }

    fun addArgumentObject(key: String?, obj: Any?): GraphqlRequest {
        getArgument()!!.addArgumentObject(key, obj)
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

    fun addInlineFragment(vararg inlineField: InlineFragment): GraphqlRequest {
        if (inlineField.isNotEmpty()) {
            inlineField.forEach { inlineFragmentFields.add(it) }
        }
        return this
    }

    fun addField(field: String) : GraphqlRequest {
        if (field.isNotEmpty()) {
            fields.add(Field(field))
        }
        return this
    }

    fun addFields(vararg field: String) : GraphqlRequest {
        if (field.isNotEmpty()) {
            field.forEach { fieldAttribute ->
                fields.add(Field(fieldAttribute))
            }
        }
        return this
    }

    fun addField(vararg field: Field): GraphqlRequest {
        if (field.isNotEmpty()) {
            field.forEach { fieldAttribute ->
                fields.add(fieldAttribute)
            }
        }
        return this
    }

    /*override fun toString(): String {
        val requestBuffer = StringBuffer(requestName)
        val paramStr: String = getRequestParameter().toString()
        val resultAttrBuffer = StringBuffer("")
        val inlineAttrBuffer = StringBuffer("")
        var first = true

        for (ra in resultAttributes) {
            if (first) {
                first = false
            } else {
                resultAttrBuffer.append(" ")
            }
            resultAttrBuffer.append(ra.toString())
        }
        val resultAttrStr = resultAttrBuffer.toString()
        requestBuffer.append(paramStr)

        if (resultAttrStr != "") {
            requestBuffer.append("{")
            requestBuffer.append(resultAttrStr)
            requestBuffer.append("}")
        }

        for (ra in inlineFragmentAttributes) {
            inlineAttrBuffer.append(ra.toString())
        }
        val inlineAttrStr = inlineAttrBuffer.toString()
        if (inlineAttrStr != "") {
            requestBuffer.append(inlineAttrStr)
        }

        return requestBuffer.toString()
    }*/

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
