package db

import de.p7s1.qa.sevenfacette.config.types.DatabaseConfig
import de.p7s1.qa.sevenfacette.db.DFactory
import de.p7s1.qa.sevenfacette.db.Database
import de.p7s1.qa.sevenfacette.db.DbStatements
import de.p7s1.qa.sevenfacette.db.SqlStatement
import org.junit.Before
import org.junit.Test
import java.sql.SQLException
import kotlin.RuntimeException
import kotlin.test.*

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

    private val insertApple = SqlStatement("insert into fruits (name) values('apple')")
    private val insertionFail = SqlStatement("insert into fruits (id, name) values('abc', 'cherry')")
    private val updateApple = SqlStatement("update fruits SET name = 'cherry' where name = 'apple'")
    private val updateAppleFail = SqlStatement("update fruits SET name = 'cherry' where name = 'banana'")
    private val insertBanana = SqlStatement("insert into fruits (name) values ('banana')")
    private val insertCherry = SqlStatement("insert into fruits (name) values ('cherry')")
    private val insertOrange = SqlStatement("insert into fruits (name) values ('orange')")
    private val insertPear = SqlStatement("insert into fruits (name) values ('pear')")
    private val createDbStatement = SqlStatement("create table fruits(id bigint auto_increment, name varchar(10))")
    private val dropTableFruits = SqlStatement("drop table fruits")
    private val deleteBanana = SqlStatement("delete from fruits where name = 'banana'")

    @Test
    fun executeInsertStatementWithoutConnection() {
        database = DFactory.createDatabase("db2")
        database.executeSqlStatement(createDbStatement)
        val insertResult = database.executeSqlStatement(insertApple)
        assertEquals("{\"ID\":1,\"NAME\":\"apple\"}", insertResult?.get(0).toString())
        database.executeSqlStatement(dropTableFruits)
    }

    @Test
    fun executeInsertStatementValidateObject() {
        database = DFactory.createDatabase("db2")
        database.executeSqlStatement(createDbStatement)
        val resultSet = database.executeSqlStatement(insertApple, Fruit::class.java)
        resultSet.forEach { fruit ->
            assertNotNull(fruit.getId())
            assertTrue(fruit.getId()!! > 0)
            assertNotNull(fruit.getName())
            assertEquals("apple", fruit.getName())
        }
        database.executeSqlStatement(dropTableFruits)
    }

    @Test
    fun executeInsertStatementWithConnectionIsNull() {
        database = DFactory.createDatabase("db2")
        database.executeSqlStatement(createDbStatement, null, true)
        val insertResult = database.executeSqlStatement(insertApple, null, true)
        // assertEquals("{\"ID\":1,\"NAME\":\"apple\"}", insertResult?.get(0).toString())
        assertNotNull(insertResult?.get(0).toString())
        database.executeSqlStatement(dropTableFruits)
    }

    @Test
    fun executeInsertStatementWithConnectionIsNullAutCloseFalse() {
        database = DFactory.createDatabase("db2")
        database.executeSqlStatement(createDbStatement)
        val insertResult = database.executeSqlStatement(insertApple, null, false)
        assertNotNull(insertResult?.get(0).toString())
        //assertEquals("{\"ID\":1,\"NAME\":\"apple\"}", insertResult?.get(0).toString())
        database.executeSqlStatement(dropTableFruits)
    }

    @Test
    fun executeInsertStatementWithEstablishedConnection() {
        database = DFactory.createDatabase("db2")
        val dbConnection = database.openConnection()
        database.executeSqlStatement(createDbStatement)
        //database.executeSqlStatement(createDbStatement, dbConnection, false)
        val insertResult = database.executeSqlStatement(insertApple, dbConnection, false)
        // assertEquals("{\"ID\":1,\"NAME\":\"apple\"}", insertResult?.get(0).toString())
        assertNotNull(insertResult?.get(0).toString())
        database.executeSqlStatement(dropTableFruits)
        database.closeConnection()
    }

    @Test
    fun executeInsertStatementWithEstablishedConnectionAutCloseTrue() {
        database = DFactory.createDatabase("db2")
        val dbConnection = database.openConnection()
        database.executeSqlStatement(createDbStatement, dbConnection, true)
        database.executeSqlStatement(insertApple, dbConnection, true)
        database.executeSqlStatement(insertBanana, dbConnection, true)
        assertNotEquals(dbConnection, database.getConn())
        database.executeSqlStatement(dropTableFruits, dbConnection, true)
        database.closeConnection()
    }

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
        database.executeSqlStatement(dropTableFruits)
    }

    @Test
    fun executeUpdateStatement() {
        database = DFactory.createDatabase("db2")
        database.executeSqlStatement(createDbStatement)
        database.executeSqlStatement(insertApple)
        database.executeSqlStatement(updateApple)
        val selectStatement = SqlStatement("select * from fruits")
        val rs = database.executeSqlStatement(selectStatement, Fruit::class.java)
        rs.forEach { fruit ->
            assertNotNull(fruit.getId())
            assertTrue(fruit.getId()!! > 0)
            assertNotNull(fruit.getName())
            assertEquals("cherry", fruit.getName())
        }
        database.executeSqlStatement(dropTableFruits)
    }

    @Test
    fun executeUpdateStatementFail() {
        database = DFactory.createDatabase("db2")
        database.executeSqlStatement(createDbStatement)
        database.executeSqlStatement(insertApple)
        assertFailsWith<RuntimeException> ("SQL Update failed!"){
            database.executeSqlStatement(updateAppleFail)
        }
        database.executeSqlStatement(dropTableFruits)
    }

    @Test
    fun executeStatementReturnAsJson() {
        database = DFactory.createDatabase("db2")
        database.executeSqlStatement(createDbStatement)
        database.executeSqlStatement(insertApple)
        val selectApple = SqlStatement("select * from fruits where name ='apple'")
        val rs = database.executeSqlStatement(selectApple)
        assertEquals("{\"ID\":1,\"NAME\":\"apple\"}", rs?.get(0).toString())
        assertNotNull(rs?.get(0).toString())
        database.executeSqlStatement(dropTableFruits)
    }

    @Test
    fun executeDbStatements() {
        database = DFactory.createDatabase("db2")
        database.executeSqlStatement(createDbStatement)
        database.executeSqlStatement(insertApple)
        val statements = DbStatements()
        statements.add("select * from fruits where name = 'apple'")
        val result = database.executeStatements(statements)
        assertEquals(1, result?.size)
        assertEquals("apple", result?.get(0)?.get("NAME"))
        database.executeSqlStatement(dropTableFruits)
    }

    @Test
    fun executeStatementWaitUntilExist() {
        database = DFactory.createDatabase("db2")
        database.executeSqlStatement(createDbStatement)
        val insertResult = database.waitUntilExistsOrUpdated(insertPear)
        assertEquals("{\"ID\":2,\"NAME\":\"pear\"}", insertResult?.get(0).toString())
        database.executeSqlStatement(dropTableFruits)
    }

    @Test
    fun executeStatementWaitUntilDeleted() {
        database = DFactory.createDatabase("db2")
        database.executeSqlStatement(createDbStatement)
        database.executeSqlStatement(insertBanana)
        val deletionResult = database.waitUntilDeleted(deleteBanana)
        println(deletionResult)
        assertEquals("[]", deletionResult.toString())
        database.executeSqlStatement(dropTableFruits)
    }

    @Test
    fun executeStatementWaitUntilExistEntity() {
        database = DFactory.createDatabase("db2")
        database.executeSqlStatement(createDbStatement)
        database.executeSqlStatement(insertApple)
        database.executeSqlStatement(updateApple)
        val selectStatement = SqlStatement("select * from fruits")
        val rs = database.waitUntilExistsOrUpdated(selectStatement, Fruit::class.java)
        rs.forEach { fruit ->
            assertNotNull(fruit.getId())
            assertTrue(fruit.getId()!! > 0)
            assertNotNull(fruit.getName())
            assertEquals("cherry", fruit.getName())
        }
        database.executeSqlStatement(dropTableFruits)
    }

    @Test
    fun executeStatementAutoCommitFalse() {
        database = DFactory.createDatabase("db3")
        database.executeSqlStatement(createDbStatement)
        database.executeSqlStatement(insertApple)
        database.executeSqlStatement(updateApple)
        val selectStatement = SqlStatement("select * from fruits")
        val rs = database.waitUntilExistsOrUpdated(selectStatement, Fruit::class.java)
        rs.forEach { fruit ->
            assertNotNull(fruit.getId())
            assertTrue(fruit.getId()!! > 0)
            assertNotNull(fruit.getName())
            assertEquals("cherry", fruit.getName())
        }
        database.executeSqlStatement(dropTableFruits)
    }

    @Test
    fun connectionFailDriver() {
        val dbConfig = DatabaseConfig("db_url", "db_driver", null, null, true, false)
        database = DFactory.createDatabase(dbConfig)
        val selectStatement = SqlStatement("select * from fruits")
        assertFailsWith<RuntimeException> {
            database.executeSqlStatement(selectStatement)
        }
    }

    @Test
    fun connectionFailUrl() {
        val dbConfig = DatabaseConfig("db_url", "org.h2.Driver", null, null, true, false)
        database = DFactory.createDatabase(dbConfig)
        val selectStatement = SqlStatement("select * from fruits")
        assertFailsWith<RuntimeException> {
            database.executeSqlStatement(selectStatement)
        }
    }

    @Test
    fun validateStatementFail() {
        database = DFactory.createDatabase("db2")
        val statement = SqlStatement("insert into melons ?")
        assertFailsWith<RuntimeException> ("This is not a prepared statement: insert into melons ?"){
            database.executeSqlStatement(statement)
        }
    }
}
