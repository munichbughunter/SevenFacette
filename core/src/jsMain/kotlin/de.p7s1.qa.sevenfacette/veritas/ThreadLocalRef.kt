package de.p7s1.qa.sevenfacette.veritas

actual open class ThreadLocalRef<T> actual constructor(initial: () -> T) {
    private var localValue: T = initial()

    actual fun get(): T = localValue

    actual fun set(value: T) {
        localValue = value
    }
}
