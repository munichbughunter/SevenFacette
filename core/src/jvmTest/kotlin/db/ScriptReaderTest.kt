package db

import de.p7s1.qa.sevenfacette.db.ScriptReader
import org.junit.Test
import kotlin.test.assertEquals

/**
 * Testingclass for [ScriptReader].
 *
 * @author Stella Bastug
 */
class ScriptReaderTest {

    private val RESOURCES_TEST_FOLDER = "testfiles/"

    @Test
    fun getStatementsFromScript(){
        val statements = ScriptReader().getStatements(RESOURCES_TEST_FOLDER, "testStatements.sql")
        assertEquals(2, statements?.size())
        assertEquals("INSERT INTO land( id, name, land_kuerzel) VALUES( get_next_id('land'), 'Deutschland', 'DE');", statements?.get(0))
        assertEquals("UPDATE table_Test SET tValue = tValue;", statements?.get(1))
        println("und?")
    }
}
