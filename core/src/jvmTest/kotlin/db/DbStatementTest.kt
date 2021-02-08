package db

import de.p7s1.qa.sevenfacette.db.DbStatement
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * Test class for [DbStatement].
 *
 * Testcases:
 *  validate prepared Statement is valid -> false
 *  validate prepared Statement is valid -> true
 *
 * @author Patrick Döring
 */
class DbStatementTest {

    @Test
    fun validatePrepStatementFalse() {
        val dbStatement = DbStatement("SELECT * FROM person WHERE name = ? AND age = ?")
        assertFalse(dbStatement.validate())
    }

    @Test
    fun validatePrepStatementTrue() {
        val dbStatement = DbStatement("SELECT * FROM person WHERE name = ? " +
                "AND age = ? AND job = ? AND car = ? AND address = ?",
                "TestName", 25, true, null, "")

        assertTrue(dbStatement.validate())
    }

    @Test
    fun prepStatementIncludingReplace() {
        val dbStatement = DbStatement("SELECT * FROM person WHERE name = ? " +
                "AND age = ? AND job = ? AND car = ? AND address = ?",
                "TestName", 25, true, null, "")

        assertEquals("SELECT * FROM person WHERE name = 'TestName' AND age = 25 AND " +
                "job = true AND car = null AND address = ''", dbStatement.sqlStatement)
    }

    @Test
    fun validate() {
        val dbStatement = DbStatement("SELECT * FROM person WHERE name = ? " +
                "AND age = ? AND job = ? AND car = ? AND address = ?",
                "Person ?", 25, true, null, "")


        val b = dbStatement.validate()

        System.out.println(b)
    }

    @Test
    fun replaceAll() {
        val statement = DbStatement("SELECT * FROM person WHERE name = 'Peter?' " +
                "AND age = ? AND job = ? AND car = ? AND address = ?", 25, true, null, "")
        assertTrue(statement.validate())
    }

    @Test
    fun formatParameter() {

    }
}
