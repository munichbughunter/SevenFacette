package de.p7s1.qa.sevenfacette.sevenfacetteHttp

import io.ktor.http.ContentType

object ContentType {
    object Application {
        val Any = ContentType("application", "*")
        val Atom = ContentType("application", "atom+xml")
        val Json = ContentType("application", "json")
        val JavaScript = ContentType("application", "javascript")
        val OctetStream = ContentType("application", "octet-stream")
        val FontWoff = ContentType("application", "font-woff")
        val Rss = ContentType("application", "rss+xml")
        val Xml = ContentType("application", "xml")
        val Xml_Dtd = ContentType("application", "xml-dtd")
        val Zip = ContentType("application", "zip")
        val GZip = ContentType("application", "gzip")
        val FormUrlEncoded = ContentType("application", "x-www-form-urlencoded")
        val Pdf = ContentType("application", "pdf")
        val Wasm = ContentType("application", "wasm")
        val ProblemJson = ContentType("application", "problem+json")
        val ProblemXml = ContentType("application", "problem+xml")
    }

    object Audio {
        val Any = ContentType("audio", "*")
        val MP4 = ContentType("audio", "mp4")
        val MPEG = ContentType("audio", "mpeg")
        val OGG = ContentType("audio", "ogg")
    }

    object Image {
        val Any = ContentType("image", "*")
        val GIF = ContentType("image", "gif")
        val JPEG = ContentType("image", "jpeg")
        val PNG = ContentType("image", "png")
        val SVG = ContentType("image", "svg+xml")
        val XIcon = ContentType("image", "x-icon")
    }

    object Message {
        val Any = ContentType("message", "*")
        val Http = ContentType("message", "http")
    }

    object MultiPart {
        val Any = ContentType("multipart", "*")
        val Mixed = ContentType("multipart", "mixed")
        val Alternative = ContentType("multipart", "alternative")
        val Related = ContentType("multipart", "related")
        val FormData = ContentType("multipart", "form-data")
        val Signed = ContentType("multipart", "signed")
        val Encrypted = ContentType("multipart", "encrypted")
        val ByteRanges = ContentType("multipart", "byteranges")
    }

    object Text {
        val Any = ContentType("text", "*")
        val Plain = ContentType("text", "plain")
        val CSS = ContentType("text", "css")
        val CSV = ContentType("text", "csv")
        val Html = ContentType("text", "html")
        val JavaScript = ContentType("text", "javascript")
        val VCard = ContentType("text", "vcard")
        val Xml = ContentType("text", "xml")
        val EventStream = ContentType("text", "event-stream")
    }

    object Video {
        val Any = ContentType("video", "*")
        val MPEG = ContentType("video", "mpeg")
        val MP4 = ContentType("video", "mp4")
        val OGG = ContentType("video", "ogg")
        val QuickTime = ContentType("video", "quicktime")
    }
}
