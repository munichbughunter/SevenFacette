package db

import de.p7s1.qa.sevenfacette.db.DFactory
import de.p7s1.qa.sevenfacette.db.Database
import de.p7s1.qa.sevenfacette.db.DbStatements
import de.p7s1.qa.sevenfacette.db.SqlStatement
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

/**
 * Testingclass for [Database].
 *
 * Testcases:
 * Execute sql statement and return the result as list of entity
 * Execute sql statement and return the result as JSON
 * Execute db statement and return the result as List<Map<String,Object>>
 *
 * @author Stella Bastug
 */
class DatabaseTest {

    private lateinit var database : Database
    private val createDbStatement = SqlStatement("CREATE table fruits(id integer, name varchar(10))")
    private val insertApple = SqlStatement("insert into fruits values (1, 'apple')")
    private val insertBanana = SqlStatement("insert into fruits values (2, 'banana')")
    private val insertCherry = SqlStatement("insert into fruits values (3, 'cherry')")
    private val insertOrange = SqlStatement("insert into fruits values (4, 'orange')")
    private val insertPear = SqlStatement("insert into fruits values (5, 'pear')")

    @Test
    fun executeStatementReturnAsEntity() {
        database = DFactory.createDatabase("db2")
        database.executeSqlStatement(createDbStatement)
        database.executeSqlStatement(insertApple)
        database.executeSqlStatement(insertBanana)
        database.executeSqlStatement(insertCherry)
        database.executeSqlStatement(insertOrange)
        database.executeSqlStatement(insertPear)
        val selectStatement = SqlStatement("select * from fruits")
        val rs = database.executeSqlStatement(selectStatement, Fruit::class.java)
        rs.forEach { fruit ->
            assertNotNull(fruit.getId())
            assertTrue(fruit.getId()!! > 0)
            assertNotNull(fruit.getName())
        }
    }

    @Test
    fun executeStatementReturnAsJson() {
        database = DFactory.createDatabase("db2")
        val selectApple = SqlStatement("select * from fruits where name ='apple'")
        val rs = database.executeSqlStatement(selectApple)
        assertEquals("{\"ID\":1,\"NAME\":\"apple\"}", rs?.get(0).toString())
    }

    @Test
    fun executeDbStatements() {
        database = DFactory.createDatabase("db2")
        val statements = DbStatements()
        statements.add("select * from fruits where name = 'apple'")
        val result = database.executeStatements(statements)
        assertEquals(1, result?.size)
        assertEquals("apple", result?.get(0)?.get("NAME"))
    }
}
