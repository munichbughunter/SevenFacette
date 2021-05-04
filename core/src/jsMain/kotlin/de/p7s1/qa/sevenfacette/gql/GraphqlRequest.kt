package de.p7s1.qa.sevenfacette.gql

/**
 * TODO: Add Description
 *
 * @author Patrick Döring
 */
@ExperimentalJsExport
@JsName("GraphqlRequest")
@JsExport
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

    fun addInlineFragment(inlineAttribute: String): GraphqlRequest {
        if (inlineAttribute.isNotEmpty()) {
            inlineFragmentAttributes.add(InlineFragmentAttributes(inlineAttribute))
        }
        return this
    }

    fun addResultAttributes(resultAttr: String) : GraphqlRequest {
        if (resultAttr.isNotEmpty()) {
            resultAttributes.add(ResultAttributes(resultAttr))
        }
        return this
    }

    fun getRequestName(): String {
        return requestName
    }

    fun setRequestName(requestName: String) {
        this.requestName = requestName
    }

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
