package de.p7s1.qa.sevenfacette

fun commonSharedCode(platform: Platform): String {
    return "Common: Hi! ${platform.greetingMethod}(${platform.name})"
}
