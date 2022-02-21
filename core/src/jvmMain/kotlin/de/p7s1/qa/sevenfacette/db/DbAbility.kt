package de.p7s1.qa.sevenfacette.db

import de.p7s1.qa.sevenfacette.screenplay.Ability
import org.json.simple.JSONArray

/**
 * TODO: Add Description
 *
 * @author Patrick Döring
 */
class DbAbility (private val database: Database) : Ability {

    override fun name(): String {
        return abilityName
    }

    override fun <T> withConfiguration(name: String): DbAbility {
        abilityName = name
        return DbAbility(DFactory.createDatabase(name))
    }

    companion object {
        var abilityName : String = ""
        /*fun withConfiguration(name: String) : DbAbility {
            abilityName = name
            return DbAbility(DFactory.createDatabase(name))
        }*/
    }

    fun executeSqlStatement(preparedDbStatement: SqlStatement) : JSONArray? {
        return database.executeSqlStatement(preparedDbStatement)
    }

    fun <T> executeSqlStatement(preparedDbStatement: SqlStatement, clazz: Class<T>) : List<T> {
        return database.executeSqlStatement(preparedDbStatement, clazz)
    }
}
