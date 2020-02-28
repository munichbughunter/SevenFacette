package de.p7s1.qa.sevenfacette.veritas

actual open class ThreadLocalRef<T> actual constructor(private val initial: () -> T) : ThreadLocal<T>() {
    override fun initialValue(): T = initial()
}
