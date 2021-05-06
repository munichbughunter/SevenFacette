package de.p7s1.qa.sevenfacette.screenplay

import de.p7s1.qa.sevenfacette.screenplay.Actor

/**
 * Task interface
 *
 * @author Patrick Döring
 */
interface Task {
    fun executeWith(actor: Actor)
}
