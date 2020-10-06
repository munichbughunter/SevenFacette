package de.p7s1.qa.sevenfacette.db

import de.p7s1.qa.sevenfacette.config.ConfigReader
import de.p7s1.qa.sevenfacette.config.types.DatabaseConfig

/**
 * JVM specific implementation of the DFactory to initialise Database connections
 *
 * @author Patrick Döring
 */
class DFactory {

    companion object {
        @JvmStatic
        fun createDatabase(databaseName: String) : Database {
            val config = ConfigReader.getDatabase(databaseName) ?: throw Exception("No config found for database $databaseName")
            return Database(config)
        }

        @JvmStatic
        fun createDatabase(config: DatabaseConfig) : Database =
                Database(config)
    }
}
