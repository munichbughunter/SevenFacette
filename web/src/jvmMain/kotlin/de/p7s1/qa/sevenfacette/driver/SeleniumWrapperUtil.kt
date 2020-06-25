package de.p7s1.qa.sevenfacette.driver

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import java.lang.reflect.InvocationTargetException
import java.util.Arrays


/**
 * TODO: Add Description
 *
 * @author Patrick Döring
 */
object SeleniumWrapperUtil {

    /**
     * @param w
     * The wrapper type.
     * @param o
     * The object to be checked.
     * @return `true` if the given object is wrapper of the selected type, otherwise `false`.
     */
    fun isWrapper(w: WrapperOf<*>, o: Any): Boolean {
        return getWrapperClass(w, o) != null
    }

    /**
     * Extracts the wrapped element or driver from a given object while implementing backwards compatibility to the
     * internal Selenium API. It is advised to call [.isWrapper] before calling this method.
     *
     * @param w
     * The wrapper type.
     * @param o
     * The object to be used.
     * @return The wrapped element or driver from the given object if it is instance of the selected type.
     *
     * @throws RuntimeException
     * If the `getWrapped` method throws an exception.
     * @throws UnsupportedOperationException
     * If the `getWrapped` method cannot be invoked or the object does not wrap an instance of
     * the selected type.
     */
    fun <T> getWrapped(w: WrapperOf<out T>, o: Any): T? {
        val wrappedClass: Class<out T> = w.getWrappedClass() as Class<out T>
        val clazz = getWrapperClass(w, o)
                ?: throw IllegalArgumentException("Type '" + o.javaClass + "' is not instance of any of wrapper class names")

        val wrapped: Any?
        try {
            wrapped = clazz.getMethod(w.getWrapperMethodName() as String).invoke(o)
            if (wrapped == null) { // We can't determine the type of null.
                return null
            }
            if (wrappedClass.isInstance(wrapped)) {
                return wrappedClass.cast(wrapped)
            }
        } catch (e: InvocationTargetException) {
            throw RuntimeException(
                    "Failed to invoke " + o.javaClass.simpleName + "#" + w.getWrapperMethodName() + ".",
                    e.targetException)
        } catch (e: NoSuchMethodException) {
            throw UnsupportedOperationException(
                    "Failed to invoke " + o.javaClass.simpleName + "#" + w.getWrapperMethodName() + ".", e)
        } catch (e: IllegalAccessException) {
            throw UnsupportedOperationException(
                    "Failed to invoke " + o.javaClass.simpleName + "#" + w.getWrapperMethodName() + ".", e)
        }
        throw UnsupportedOperationException("Failed to retrieve expected type '" + wrappedClass.simpleName
                + "' of '" + w.getWrapperMethodName() + "', got '" + wrapped.javaClass.simpleName + "'")
    }

    // ToDo: How to fix that...
    private fun getWrapperClass(w: WrapperOf<*>, o: Any): Class<*>? {
        /*
        for (wrapsElementClassName in w.getWrapperClassNames()) {
            try {
                val clazz = Class.forName(wrapsElementClassName)
                if (clazz.isInstance(o)) {
                    return clazz
                }
            } catch (e: ClassNotFoundException) {
            }
        }

         */
        return null
    }

    /**
     * Determines a Selenium wrapper type (i.e. an element or a driver) and contains information for reflective access.
     */
    class WrapperOf<T>(private val wClass: Any, s: String, arrayOf: Array<String> ){

        fun getWrappedClass(): Class<out T?>? {
            return wrappedClass
        }

        fun getWrapperClassNames(): Any? {
            return wrapperClassNames
        }

        fun getWrapperMethodName(): Any? {
            return wrapperMethodName
        }


        private val wrappedClass: Class<out T?>? = null
        private val wrapperMethodName: String? = null
        private val wrapperClassNames: Array<String> = emptyArray()

        companion object {
            val ELEMENT = WrapperOf<WebElement>(WebElement::class.java, "getWrappedElement", arrayOf("org.openqa.selenium.WrapsElement", "org.openqa.selenium.internal.WrapsElement"))
            val DRIVER = WrapperOf<WebDriver>(WebDriver::class.java, "getWrappedDriver", arrayOf("org.openqa.selenium.WrapsDriver", "org.openqa.selenium.internal.WrapsDriver"))
        }
    }
}
