package de.p7s1.qa.sevenfacette.gql

/**
 * TODO: Add Description
 *
 * @author Patrick Döring
 */
@ExperimentalJsExport
@JsName("GraphqlMutation")
@JsExport
class GraphqlMutation (operationName: String) : GraphqlRequest(operationName) {

    override fun toString(): String {
        val superStr = super.toString()
        return "{\"query\":\"mutation{$superStr}\"}"
    }
}
