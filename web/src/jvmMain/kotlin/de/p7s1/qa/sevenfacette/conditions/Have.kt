@file:JvmName("Have")
package de.p7s1.qa.sevenfacette.conditions

import de.p7s1.qa.sevenfacette.conditions.*

fun text(text: String) = Text(text)

fun size(size: Int) = CollectionSize(size)

fun sizeAtLeast(size: Int) = CollectionMinimumSize(size)

fun elementWithText(text: String) = CollectionContainText(text)

fun exactText(vararg text: String) = CollectionExactText(text)

fun attr(name: String, value: String) = AttributeValue(name, value)

fun cssClass(cssClass: String) = CssClassValue(cssClass)
