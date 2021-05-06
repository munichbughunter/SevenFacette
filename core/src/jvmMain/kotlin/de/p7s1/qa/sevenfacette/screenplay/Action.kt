package de.p7s1.qa.sevenfacette.screenplay

import de.p7s1.qa.sevenfacette.screenplay.Actor

/**
 * Action interface
 *
 * @author Patrick Döring
 */
interface Action {
    fun executeWith(actor: Actor)
}
