package de.p7s1.qa.sevenfacette.screenplay

/**
 * TODO: Add Description
 *
 * @author Patrick Döring
 */
@ExperimentalJsExport
@JsExport
class Actions (private vararg val actions: Action) {
    fun executeWith(actor: Actor) {
        actions.forEach {
            it.executeWith(actor)
        }
    }
}
