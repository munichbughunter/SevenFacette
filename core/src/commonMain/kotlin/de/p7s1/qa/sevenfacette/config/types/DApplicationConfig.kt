package de.p7s1.qa.sevenfacette.config.types

import kotlinx.serialization.Serializable

@Serializable
data class DApplicationConfig(
        val resources: List<DResourceConfig>
) {
    fun getResourceFolder(folderName: String): String? = resources.first { it.name == folderName }.path
}
