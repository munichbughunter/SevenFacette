package de.p7s1.qa.sevenfacette.db.config

import de.p7s1.qa.sevenfacette.db.DFactory
import de.p7s1.qa.sevenfacette.db.DbStatements

/**
 * Holds the basic Kafka configuration parameter
 *
 * @parmeter [dbDriver] Database driver
 * @parmeter [dbUrl] Database url
 * @parmeter [dbUser] Database user
 * @parmeter [dbPW] Database password
 *
 * @author Patrick Döring
 */
class DConfig {

    var dbDriver: String = ""

    var dbUrl: String = ""

    var dbUser: String = ""

    var dbPW: String = ""

    var autoCommit: Boolean = true

    var stopOnError: Boolean = false

    fun executeQuery(dbStatements: DbStatements) : List<Map<String, Any>>? {
        return DFactory.executeQuery(this@DConfig, dbStatements)
    }
}
