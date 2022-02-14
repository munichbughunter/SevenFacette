package de.p7s1.qa.sevenfacette.screenplay

/**
 * TODO: Add Description
 *
 * @author Patrick Doering
 */
class MissingAbilityException
/**
 * @param actor        the [Actor] missing the abilityClass
 * @param abilityClass the class of the missing [Ability]
 */
    (actor: Actor?, abilityClass: Class<out Ability?>) :
    RuntimeException(String.format("%s is not able to %s", actor, abilityClass.simpleName))
