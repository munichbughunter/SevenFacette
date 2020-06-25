package de.p7s1.qa.sevenfacette.utils

import kotlin.reflect.KProperty

/**
 * TODO: Add Description
 *
 * @author Patrick Döring
 */
internal class ThreadLocalDelegate<T>(private val delegate: ThreadLocal<T> = ThreadLocal<T>()) {

    constructor(initialValueSupplier: () -> T) : this(ThreadLocal.withInitial(initialValueSupplier))

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T = delegate.get()

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T): Unit = delegate.set(value)
}
