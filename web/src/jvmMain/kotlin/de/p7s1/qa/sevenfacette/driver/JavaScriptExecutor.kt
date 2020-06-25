package de.p7s1.qa.sevenfacette.driver

/**
 * TODO: Add Description
 *
 * @author Patrick Döring
 */
interface JavaScriptExecutor {
    fun execute(vararg args: Any, async: Boolean = false, script: () -> String): Any?

    fun call(vararg args: Any, async: Boolean = false, script: () -> String): Any? = execute(
            *args, async = async, script = script
    )

    fun run(vararg args: Any, async: Boolean = false, script: () -> String): Any? = execute(
            *args, async = async, script = script
    )

    operator fun invoke(vararg args: Any, async: Boolean = false, script: () -> String): Any? = execute(
            *args, async = async, script = script
    )

    operator fun get(value: String): Any? = execute { "return $value;" }

    operator fun set(name: String, value: Any?) {
        when (value) {
            null -> execute { "window.$name = null;" }
            else -> execute(value) { "window.$name = arguments[0];" }
        }
    }
}
