package db

import de.p7s1.qa.sevenfacette.db.SqlStatement
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * Test class for [SqlStatement]
 *
 * Testcases:
 *  Prepared Statement is valid -> false
 *  Prepared Statement is valid -> true
 *  Prepared Statement with whitespace at the end
 *  Prepared Statement without whitespace at the end
 *  Prepared Statement with format parameter in replacement like 'Person ?' -> no replacement
 *  Prepared Statement with format parameter in basic statement -> no replacement
 *  Prepared Statement without whitespace between format parameter
 *  Prepared Statement without constructor arguments and call replaceAll separately
 *  Prepared Statement with whitespaces between placeholder
 *
 * @author Patrick Döring
 */
class SqlStatementTest {

    @Test
    fun validatePrepStatementFalse() {
        val dbStatement = SqlStatement("SELECT * FROM person WHERE name = ? AND age = ?")
        assertFalse(dbStatement.validatePreparedStatement())
    }

    @Test
    fun validatePrepStatementTrue() {
        val dbStatement = SqlStatement("SELECT * FROM person WHERE name = ? " +
                "AND age = ? AND job = ? AND car = ? AND address = ?",
                "TestName", 25, true, null, "")

        assertTrue(dbStatement.validatePreparedStatement())
    }

    @Test
    fun prepStatementWithEmptyCharEnding() {
        val dbStatement = SqlStatement("SELECT * FROM person WHERE name = ? " +
                "AND age = ? AND job = ? AND car = ? AND address = ? ",
                "TestName", 25, true, null, "")

        assertEquals("SELECT * FROM person WHERE name = 'TestName' AND age = 25 AND " +
                "job = true AND car = null AND address = ''", dbStatement.sqlStatement)
    }

    @Test
    fun prepStatementWithoutEmptyCharEnding() {
        val dbStatement = SqlStatement("SELECT * FROM person WHERE name = ? " +
                "AND age = ? AND job = ? AND car = ? AND address = ?",
                "TestName", 25, true, null, "")

        assertEquals("SELECT * FROM person WHERE name = 'TestName' AND age = 25 AND " +
                "job = true AND car = null AND address = ''", dbStatement.sqlStatement)
    }

    @Test
    fun replaceWithFormatParameterValue() {
        val dbStatement = SqlStatement("SELECT * FROM person WHERE name = ? " +
                "AND age = ? AND job = ? AND car = ? AND address = ?",
                "Person ?", 25, true, null, "")

        assertTrue(dbStatement.validatePreparedStatement())
    }

    @Test
    fun replaceOnlyFormatParameter() {
        val statement = SqlStatement("SELECT * FROM person WHERE name = 'Peter?' " +
                "AND age = ? AND job = ? AND car = ? AND address = ?", 25, true, null, "")
        assertTrue(statement.validatePreparedStatement())
    }

    @Test
    fun replaceWithoutEmptyCharacter() {
        val dbStatement = SqlStatement("SELECT * FROM person WHERE name=?" +
                " AND age=? AND job=? AND car=? AND address=?",
                "Person ?", 25, true, null, "")

        assertEquals("SELECT * FROM person WHERE name='Person ?' AND age=25 AND " +
                "job=true AND car=null AND address=''", dbStatement.sqlStatement)
    }

    @Test
    fun replaceWithoutArgs() {
        val dbStatement = SqlStatement("SELECT * FROM person WHERE name = 'Peter ?' " +
                "AND age = ? AND job = ? AND car = ? AND address = ?")

        dbStatement.replaceAllPlaceholder(25, true, null, "")

        assertTrue(dbStatement.validatePreparedStatement())

        assertEquals("SELECT * FROM person WHERE name = 'Peter ?' AND age = 25 AND " +
                "job = true AND car = null AND address = ''", dbStatement.sqlStatement)
    }

    @Test
    fun placeholderWhitespace() {
        val dbStatement = SqlStatement("SELECT * FROM person WHERE name = 'Peter ? Ja ist er' " +
                "AND age = ' ? ' AND job = '?' AND car = ? AND address = ? ")

        dbStatement.replaceAllPlaceholder(null, "")

        assertTrue(dbStatement.validatePreparedStatement())

        assertEquals("SELECT * FROM person WHERE name = 'Peter ? Ja ist er' AND age = ' ? ' AND " +
                "job = '?' AND car = null AND address = ''", dbStatement.sqlStatement)
    }
}
