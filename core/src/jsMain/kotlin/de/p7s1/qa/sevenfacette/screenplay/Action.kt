package de.p7s1.qa.sevenfacette.screenplay

/**
 * TODO: Add Description
 *
 * @author Patrick Döring
 */
@ExperimentalJsExport
@JsExport
interface Action {
    fun executeWith(actor: Actor)
}
