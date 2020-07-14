package de.p7s1.qa.sevenfacette.core

import de.p7s1.qa.sevenfacette.driver.Browser
import de.p7s1.qa.sevenfacette.utils.WrongUrlException
import kotlin.reflect.KProperty


//fun <T : FConfig> loadConfig(klazz: KClass<T>): FConfig {
//    return ConfigFactory.create(klazz.java, System.getProperties())
//}

class BaseUrlDelegate {

    private var prop: String = ""

    operator fun getValue(browser: Browser, property: KProperty<*>): String {
        if (prop.isBlank()) {
            //val url = browser.config.baseUrl()
            val url = browser.config.baseUrl
            if (url == null) {
                throw WrongUrlException("Can't navigate to url [$url]. " +
                        "Please use absolute or set the base url !!!")
            }
            return url.removeSuffix("/")
        }
        return prop.removeSuffix("/")
    }

    operator fun setValue(browser: Browser, property: KProperty<*>, value: String) {
        prop = value
    }
}

fun baseUrl(): BaseUrlDelegate {
    return BaseUrlDelegate()
}

class TimeoutDelegate {

    private var prop: Int = -1

    operator fun getValue(browser: Browser, property: KProperty<*>): Int {
        if (prop < 0) {
            return browser.config.timeout
        }
        return prop
    }

    operator fun setValue(browser: Browser, property: KProperty<*>, value: Int) {
        prop = value
    }
}

fun timeout(): TimeoutDelegate {
    return TimeoutDelegate()
}

class PoolingIntervalDelegate {
    private var prop: Double = - 0.1

    operator fun getValue(browser: Browser, property: KProperty<*>): Double {
        if (prop < 0) {
            return browser.config.pollingInterval
        }
        return prop
    }

    operator fun setValue(browser: Browser, property: KProperty<*>, value: Double) {
        prop = value
    }
}

fun poolingInterval(): PoolingIntervalDelegate {
    return PoolingIntervalDelegate()
}

class ScreenSizeDelegate {
    private var prop: List<Int> = listOf()

    operator fun getValue(browser: Browser, property: KProperty<*>): List<Int> {
        if (prop.isEmpty()) {
            return browser.config.screenSize
        }
        return prop
    }

    operator fun setValue(browser: Browser, property: KProperty<*>, value: List<Int>) {
        prop = value
    }
}

fun screenSize(): ScreenSizeDelegate {
    return ScreenSizeDelegate()
}
