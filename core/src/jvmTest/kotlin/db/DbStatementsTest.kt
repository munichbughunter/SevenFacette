package db

import de.p7s1.qa.sevenfacette.db.DbStatements
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Testingclass for [DbStatements].
 *
 * Testcases:
 *
 * @author Patrick Döring
 */
class DbStatementsTest {

    @Test
    fun addDbStatements(){
        val statements = DbStatements()
        statements.add("select * from fruits where name = 'apple'")
        assertEquals("select * from fruits where name = 'apple'", statements.get(0))
    }

    @Test
    fun retrieveDbStatementsList(){
        val statements = DbStatements()
        statements.add("select * from fruits where name = 'apple'")
        statements.add("select * from fruits where name = 'cherry'")
        statements.add("select * from fruits where name = 'orange'")
        assertEquals(3, statements.list.size)
    }

    @Test
    fun containsDbStatement(){
        val statements = DbStatements()
        statements.add("select * from fruits where name = 'apple'")
        statements.add("select * from fruits where name = 'cherry'")
        statements.add("select * from fruits where name = 'orange'")
        assertTrue(statements.contains("select * from fruits where name = 'orange'"))
    }

    @Test
    fun getDbStatementByIndex(){
        val statements = DbStatements()
        statements.add("select * from fruits where name = 'apple'")
        statements.add("select * from fruits where name = 'cherry'")
        statements.add("select * from fruits where name = 'orange'")
        assertEquals("select * from fruits where name = 'orange'", statements.get(2))
    }

    @Test
    fun reformatDbStatement(){
        val statements = DbStatements()
        statements.add("select * from fruits where name = 'apple'")
        statements.add("select * from fruits where name = %s")
        statements.add("select * from fruits where name = 'orange'")
        statements.reformat(1, "cherry")
        assertEquals("select * from fruits where name = cherry", statements.get(1))
    }
}
