package de.p7s1.qa.sevenfacette.locators


interface ElementLocator<out T> {

    fun find(): T

    val description: String
}
