package de.p7s1.qa.sevenfacette.screenplay

import com.microsoft.playwright.*

class Screen(private val page: Page) {

    companion object {

        fun launch(browser: Browser): Screen {
            return Screen(browser.newPage());
        }
    }

    fun navigate(whereTo: String): Response {
        return page.navigate(whereTo);

    }

    fun click(where: String) {}
    fun fill(where: String, value: String) {
        page.fill(where, value)
    }

    fun innerText(where: String, value: String) {}
    fun innerText(where: String): String {
        return ""
    }

    fun attribute(where: String, value: String) {}
    fun attribute(where: String): String {
        return ""
    }
}
