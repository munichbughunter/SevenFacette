package de.p7s1.qa.sevenfacette.utils

@ExperimentalJsExport
@JsExport
actual class Files {
    actual companion object {
        //@JsName("getAsByteArray")
        actual fun getAsByteArray(path: String): ByteArray {
            return fs.readFileSync(path, "utf8").encodeToByteArray()
        }

        //@JsName("getAsText")
        actual fun getAsText(path: String): String {
            return fs.readFileSync(path, "utf8")
        }

        actual fun getResource(fileName: String): String? =
                getAsText(fileName)

        actual fun getResourceText(fileName: String): String? =
                getAsText(fileName)
    }
}
