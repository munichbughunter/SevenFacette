package de.p7s1.qa.sevenfacette.driver

import de.p7s1.qa.sevenfacette.conditions.Condition
import de.p7s1.qa.sevenfacette.conditions.not
import de.p7s1.qa.sevenfacette.core.FEventListener
import de.p7s1.qa.sevenfacette.core.waitFor
import de.p7s1.qa.sevenfacette.locators.ElementLocator
import org.openqa.selenium.WebDriver

abstract class Element<out T>(protected val locator: ElementLocator<T>,
                              protected val driver: WebDriver) {
    var waitTimeout: Int = 4000
    var waitPoolingInterval: Double = 0.1
    var eventListener: FEventListener? = null


    protected fun should(condition: Condition<T>, waitTimeout: Int) {
        try {
            waitFor(driver, this.locator, condition, waitTimeout, waitPoolingInterval)
        } catch (ex: Exception) {
            eventListener?.onFail(ex)
            throw ex
        }
    }

    protected fun should(condition: Condition<T>) {
        should(condition, waitTimeout)
    }

    protected fun shouldNot(condition: Condition<T>) {
        should(not(condition))
    }

    infix fun shouldHave(condition: Condition<T>) {
        should(condition)
    }

    fun shouldBe(condition: Condition<T>) {
        should(condition)
    }

    fun shouldNotHave(condition: Condition<T>) {
        shouldNot(condition)
    }

    fun shouldNotBe(condition: Condition<T>) {
        shouldNot(condition)
    }
}
