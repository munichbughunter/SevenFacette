package de.p7s1.qa.sevenfacette.gql

/**
 * TODO: Add Description
 *
 * @author Patrick Döring
 */
@ExperimentalJsExport
@JsName("GraphqlMutation")
@JsExport
class GraphqlMutation (requestName: String) : GraphqlRequest(requestName) {

    override fun toString(): String {
        val superStr = super.toString()
        return "{\"query\":\"mutation{$superStr}\"}"
    }
}
