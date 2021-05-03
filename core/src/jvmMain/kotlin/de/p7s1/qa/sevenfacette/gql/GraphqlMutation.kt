package de.p7s1.qa.sevenfacette.gql

/**
 * Graphql mutation query.
 *
 * @author Patrick Döring
 */
class GraphqlMutation (requestName: String) : GraphqlRequest(requestName) {

    override fun toString(): String {
        val superStr = super.toString()
        return "{\"query\":\"mutation{$superStr}\"}"
    }
}
