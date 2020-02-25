package de.p7s1.qa.sevenfacette.veritas

expect open class ThreadLocalRef<T>(initial: () -> T) {
    fun get(): T
    fun set(value: T)
}

var <T> ThreadLocalRef<T>.value: T
    get() = get()
    set(value) {
        set(value)
    }
