package utils

import de.p7s1.qa.sevenfacette.gql.GraphqlMutation
import de.p7s1.qa.sevenfacette.gql.GraphqlQuery
import de.p7s1.qa.sevenfacette.gql.GraphqlStatement
import de.p7s1.qa.sevenfacette.gql.InlineFragment
import de.p7s1.qa.sevenfacette.gql.Field
import org.junit.Test
import utils.GenderEnum.*
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertFalse


class GraphQlTest {

    @Test
    fun graphqlQuery() {
        val query = GraphqlQuery("findUser")
        query.addFields("name","adress","age")

        val expectedGql = "{\"query\":\"{findUser{name adress age}}\"}"

        assertEquals(expectedGql, query.toString())
    }

    @Test
    fun graphqlMutation() {
        val mutationQuery = GraphqlMutation("person")
        mutationQuery
            .getArgument()
            ?.addArgument("age", "25")
            ?.addArgument("name", "Bob")

        val expectedGql = "{\"query\":\"mutation{person(name:\\\"Bob\\\",age:\\\"25\\\")}\"}"
        assertEquals(expectedGql, mutationQuery.toString())
    }

    @Test
    fun graphqlParametersMutation() {
        val queryParams: MutableMap<String, Any> = mutableMapOf()
        queryParams["age"] = "25"
        queryParams["name"] = "Bob"
        val mutationQuery = GraphqlMutation("person")
        mutationQuery.addArguments(queryParams)

        val expectedGql = "{\"query\":\"mutation{person(name:\\\"Bob\\\",age:\\\"25\\\")}\"}"
        assertEquals(expectedGql, mutationQuery.toString())
    }

    @Test
    fun graphqlMutationWithResultParams() {
        val mutationQuery = GraphqlMutation("person")
        mutationQuery
            .addArgument("age", "25")
            .addArgument("name", "Bob")
        mutationQuery.addField("data")


        val expectedGql = "{\"query\":\"mutation{person(name:\\\"Bob\\\",age:\\\"25\\\"){data}}\"}"
        assertEquals(expectedGql, mutationQuery.toString())
    }

    @Test
    fun graphqlMutationObjectParameter() {
        val mutationQuery = GraphqlMutation("addUser")
        val users: MutableList<User> = mutableListOf()
        users.add(User("tim", M))
        users.add(User("tanja", F))
        users.add(User("alonso", D))

        mutationQuery
            .addArgument("Abschlussklasse", "2009")
            .addArgumentObject("users", users)
        val expectedGql = "{\"query\":\"mutation{addUser(Abschlussklasse:\\\"2009\\\",users:[{name:\\\"tim\\\",genderEnum:M},{name:\\\"tanja\\\",genderEnum:F},{name:\\\"alonso\\\",genderEnum:D}])}\"}"
        assertEquals(expectedGql, mutationQuery.toString())
    }

    @Test
    fun graphqlContentCC() {
        val gqlQuery = GraphqlQuery("contents")
        gqlQuery.addArgument("id", "123456")
        gqlQuery.addField("actualAssemblage")
        val audioAttribute = Field("audio")
        audioAttribute
            .addField("id")
            .addField("name")
            .addField("shortName")

        gqlQuery.addField(audioAttribute)

        val expectedGql = "{\"query\":\"{contents(id:\\\"123456\\\"){actualAssemblage audio{ id name shortName }}}\"}"
        assertEquals(expectedGql, gqlQuery.toString())
    }

    @Test
    fun graphqlWithInlineFragment() {
        val query = GraphqlQuery("country")
        query.addArgument("code", "BF")
        query.addFields("name", "native", "phone")

        val inlineFragment = InlineFragment("Country")
        inlineFragment
            .addInlineField("code", "capital")

        query.addInlineFragment(inlineFragment)
        val expectedGql = "{\"query\":\"{country(code:\\\"BF\\\"){name native phone}...on Country{ code capital }}\"}"
        assertEquals(expectedGql, query.toString())
    }

    @Test
    fun gqlStringEscape() {
        val gql = "mutation{person(name: ?, age: ?)}"
        val query = GraphqlStatement(gql, "Bob", "25")

        val expectedGql = "{\"query\":\"mutation{person(name: \\\"Bob\\\", age: \\\"25\\\")}\"}"
        assertEquals(expectedGql, query.gql)
    }

    @Test
    fun validateGraphQlStatementTrue() {
        val gql = "mutation{person(name: ?, age: ?)}"
        val query = GraphqlStatement(gql, "Bob", "25")
        assertTrue(query.validateGraphQlStatement())
    }

    @Test
    fun validateGraphQlStatementFalse() {
        val gql = "mutation{person(name: ?, age: ?)}"
        val query = GraphqlStatement(gql)
        assertFalse(query.validateGraphQlStatement())
    }
}
