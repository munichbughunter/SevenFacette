package de.p7s1.qa.sevenfacette.gql


/**
 * Graphql Query.
 *
 * @author Patrick Döring
 */
class GraphqlQuery(operationName: String) : GraphqlRequest(operationName) {

    override fun toString(): String {
        val superStr = super.toString()
        return "{\"query\":\"{$superStr}\"}"
    }
}
