package db

import de.p7s1.qa.sevenfacette.config.types.DatabaseConfig
import de.p7s1.qa.sevenfacette.db.DFactory
import de.p7s1.qa.sevenfacette.db.Database
import org.junit.Test
import java.lang.Exception
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull

/**
 * Testingclass for [DFactory].
 *
 * Testcases:
 * Create the database by a string name
 * Create the database by a string name equals empty --> throws an exception
 * Create the database by a database config object
 *
 * @author Stella Bastug
 */
class DFactoryTest {

    private lateinit var database : Database

    @Test
    fun createDatabaseByName() {
        database = DFactory.createDatabase("db2")
        assertNotNull(database)
    }

    @Test
    fun createDatabaseByNameNull() {
        assertFailsWith<Exception> {
            database = DFactory.createDatabase("")
        }
    }

    @Test
    fun createDatabaseByConfig() {
        val dbConfig = DatabaseConfig("db_url", "db_driver", null, null, true, false)
        database = DFactory.createDatabase(dbConfig)
        assertNotNull(database)
    }
}
