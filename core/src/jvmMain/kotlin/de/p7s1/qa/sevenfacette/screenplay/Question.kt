package de.p7s1.qa.sevenfacette.screenplay

import de.p7s1.qa.sevenfacette.screenplay.Actor

/**
 * Question interface
 *
 * @author Patrick Döring
 */
interface Question {
    fun askWith(actor: Actor)
}
