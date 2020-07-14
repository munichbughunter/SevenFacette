@file:JvmName("Be")
package de.p7s1.qa.sevenfacette.conditions

@JvmField val visible = Visibility()
@JvmField val invisible = Not(Visibility())
@JvmField val empty = CollectionSize(0)
@JvmField val clickable = Clickable()
