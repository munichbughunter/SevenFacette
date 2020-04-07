package de.p7s1.qa.sevenfacette.db

import de.p7s1.qa.sevenfacette.db.config.DConfig

/**
 * JVM specific implementation of the DFactory to initialise Database connections
 *
 * @author Patrick Döring
 */
class DFactory {

    companion object {
        @JvmStatic
        fun executeQuery(dbConfig: DConfig, dbStatements: de.p7s1.qa.sevenfacette.db.DbStatements) : List<Map<String, Any>>? {
            return Database(dbConfig).executeStatements(dbStatements)
        }
    }
}
