package de.p7s1.qa.sevenfacette.veritas.verification

import de.p7s1.qa.sevenfacette.veritas.Verify
import de.p7s1.qa.sevenfacette.veritas.verification.utils.show
import de.p7s1.qa.sevenfacette.veritas.verification.utils.expected
import java.io.File
import java.nio.charset.Charset

/**
 * Returns an assert on the file's name.
 */
fun Verify<File>.name() = prop("name", File::getName)

/**
 * Returns an assert on the file's path.
 */
fun Verify<File>.path() = prop("path", File::getPath)

/**
 * Returns an assert on the file's parent.
 */
fun Verify<File>.parent() = prop("parent", File::getParent)

/**
 * Returns an assert on the file's extension.
 */
fun Verify<File>.extension() = prop("extension", File::extension)

/**
 * Returns an assert on the file's contents as text.
 */
fun Verify<File>.text(charset: Charset = Charsets.UTF_8) = prop("text") { it.readText(charset) }

/**
 * Returns an assert on the file's contents as bytes.
 */
fun Verify<File>.bytes() = prop("bytes", File::readBytes)

/**
 * Asserts the file exists.
 */
fun Verify<File>.exists() = given { actual ->
    if (actual.exists()) return
    expected("to exist")
}

/**
 * Asserts the file is a directory.
 * @see [isFile]
 */
fun Verify<File>.isDirectory() = given { actual ->
    if (actual.isDirectory) return
    expected("to be a directory")
}

/**
 * Asserts the file is a simple file (not a directory).
 * @see [isDirectory]
 */
fun Verify<File>.isFile() = given { actual ->
    if (actual.isFile) return
    expected("to be a file")
}

/**
 * Asserts the file is hidden.
 * @see [isNotHidden]
 */
fun Verify<File>.isHidden() = given { actual ->
    if (actual.isHidden) return
    expected("to be hidden")
}

/**
 * Asserts the file is not hidden.
 * @see [isHidden]
 */
fun Verify<File>.isNotHidden() = given { actual ->
    if (!actual.isHidden) return
    expected("to not be hidden")
}

/**
 * Asserts the file has the expected name.
 */
fun Verify<File>.hasName(expected: String) {
    name().isEqualTo(expected)
}

/**
 * Asserts the file has the expected path.
 */
fun Verify<File>.hasPath(expected: String) {
    path().isEqualTo(expected)
}

/**
 * Asserts the file has the expected parent path.
 */
fun Verify<File>.hasParent(expected: String) {
    parent().isEqualTo(expected)
}

/**
 * Asserts the file has the expected extension.
 */
fun Verify<File>.hasExtension(expected: String) {
    extension().isEqualTo(expected)
}

/**
 * Asserts the file contains exactly the expected text (and nothing else).
 * @param charset The character set of the file, default is [Charsets.UTF_8]
 * @see [hasBytes]
 */
fun Verify<File>.hasText(expected: String, charset: Charset = Charsets.UTF_8) {
    text(charset).isEqualTo(expected)
}

/**
 * Asserts the file has the expected direct child.
 */
fun Verify<File>.hasDirectChild(expected: File) = given { actual ->
    if (actual.listFiles()?.contains(expected) == true) return
    expected("to have direct child ${show(expected)}")
}
