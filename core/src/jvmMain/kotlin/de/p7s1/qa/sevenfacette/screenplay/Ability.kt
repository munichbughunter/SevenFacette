package de.p7s1.qa.sevenfacette.screenplay

/**
 * Ability interface
 *
 * @author Patrick Döring
 */
interface Ability {
    fun name() : String
    fun <T> withConfiguration(name: String): Any
}
