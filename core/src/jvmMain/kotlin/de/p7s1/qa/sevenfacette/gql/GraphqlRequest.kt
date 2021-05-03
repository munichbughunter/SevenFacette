package de.p7s1.qa.sevenfacette.gql

/**
 * Graphql request.
 *
 * @author Patrick Döring
 */
abstract class GraphqlRequest constructor(private var requestName: String) {

    private var resultAttributes: MutableList<ResultAttributes> = mutableListOf()
    private var inlineFragmentAttributes: MutableList<InlineFragmentAttributes> = mutableListOf()
    private var requestParam: RequestParameter? = null

    fun addParameter(key: String?, `val`: Any?): RequestParameter {
        return getRequestParameter()!!.addParameter(key, `val`)
    }

    fun addParameters(params: Map<String, Any>) {
        params.forEach { (t, u) ->  getRequestParameter()!!.addParameter(t, u)}
    }

    fun getRequestParameter(): RequestParameter? {
        if (this.requestParam == null) {
            this.requestParam = RequestParameter.build()
        }
        return this.requestParam
    }

    fun addInlineFragment(vararg inlineAttribute: InlineFragmentAttributes): GraphqlRequest {
        if (inlineAttribute.isNotEmpty()) {
            inlineAttribute.forEach { inlineFragmentAttributes.add(it) }
        }
        return this
    }

    fun addResultAttributes(vararg resultAttr: String) : GraphqlRequest {
        if (resultAttr.isNotEmpty()) {
            resultAttr.forEach { resultAttribute ->
                resultAttributes.add(ResultAttributes(resultAttribute))
            }
        }
        return this
    }

    fun addResultAttributes(vararg resultAttr: ResultAttributes): GraphqlRequest {
        if (resultAttr.isNotEmpty()) {
            resultAttr.forEach { resultAttribute ->
                resultAttributes.add(resultAttribute)
            }
        }
        return this
    }

    fun getRequestName(): String {
        return requestName
    }

    fun setRequestName(requestName: String) {
        this.requestName = requestName
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
        var gql = requestName
        gql = gql.plus(getRequestParameter().toString())
        if (resultAttributes.size > 0) {
            gql = addResultAttr(gql)
        }

        if (inlineFragmentAttributes.size > 0) {
            gql = addInlineAttr(gql)
        }

        return gql
    }

    private fun addInlineAttr(_gql: String) : String {
        var gql = _gql
        inlineFragmentAttributes.forEach {
            gql = gql.plus(it.toString())
        }
        return gql
    }

    private fun addResultAttr(_gql: String) : String {
        var gql = _gql
        var resultAttr = ""
        var first = true
        resultAttributes.forEach {
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
