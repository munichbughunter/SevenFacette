package de.p7s1.qa.sevenfacette.http

enum class CONTENTTYPES(val contentType: ContentType) {
    APPLICATION_ANY (ContentType("application", "*")),
    APPLICATION_ATOM (ContentType("application", "atom+xml")),
    APPLICATION_GRAPHQL (ContentType("application", "graphql")),
    APPLICATION_JSON (ContentType("application", "json")),
    APPLICATION_JAVASCRIPT (ContentType("application", "javascript")),
    APPLICATION_OCTET_STREAM (ContentType("application", "octet-stream")),
    APPLICATION_FONTWOFF (ContentType("application", "font-woff")),
    APPLICATION_RSS (ContentType("application", "rss+xml")),
    APPLICATION_XML (ContentType("application", "xml")),
    APPLICATION_XXL_DTD (ContentType("application", "xml-dtd")),
    APPLICATION_ZIP (ContentType("application", "zip")),
    APPLICATION_GZIP (ContentType("application", "gzip")),
    APPLICATION_FORM_URL_ENCODED (ContentType("application", "x-www-form-urlencoded")),
    APPLICATION_PDF (ContentType("application", "pdf")),
    APPLICATION_WASM (ContentType("application", "wasm")),
    APPLICATION_PROBLEM_JSON (ContentType("application", "problem+json")),
    APPLICATION_PROBLEM_XML (ContentType("application", "problem+xml")),
    AUDIO_ANY (ContentType("audio", "*")),
    AUDIO_MP4 (ContentType("audio", "mp4")),
    AUDIO_MPEG (ContentType("audio", "mpeg")),
    AUDIO_OGG (ContentType("audio", "ogg")),
    IMAGE_ANY (ContentType("image", "*")),
    IMAGE_GIF (ContentType("image", "gif")),
    IMAGE_JPEG (ContentType("image", "jpeg")),
    IMAGE_PNG (ContentType("image", "png")),
    IMAGE_SVG (ContentType("image", "svg+xml")),
    IMAGE_XICON (ContentType("image", "x-icon")),
    KAFKA_JSON (ContentType("application", "vnd.kafka.json.v1+json")),
    MESSAGE_ANY (ContentType("message", "*")),
    MESSAGE_HTTP (ContentType("message", "http")),
    MULTIPART_ANY (ContentType("multipart", "*")),
    MULTIPART_MIXED (ContentType("multipart", "mixed")),
    MULTIPART_ALTERNATIVE (ContentType("multipart", "alternative")),
    MULTIPART_RELATED (ContentType("multipart", "related")),
    MULTIPART_FORMDATA (ContentType("multipart", "form-data")),
    MULTIPART_SIGNED (ContentType("multipart", "signed")),
    MULTIPART_EXNCRYPTED (ContentType("multipart", "encrypted")),
    MULTIPART_BYTE_RANGES (ContentType("multipart", "byteranges")),
    TEXT_ANY (ContentType("text", "*")),
    TEXT_PLAIN (ContentType("text", "plain")),
    TEXT_CSS (ContentType("text", "css")),
    TEXT_CSV (ContentType("text", "csv")),
    TEXT_HTML (ContentType("text", "html")),
    TEXT_JAVASCRIPT (ContentType("text", "javascript")),
    TEXT_VCARD (ContentType("text", "vcard")),
    TEXT_XML (ContentType("text", "xml")),
    TEXT_EVENT_STREAM (ContentType("text", "event-stream")),
    VIDEO_ANY (ContentType("video", "*")),
    VIDEO_MPEG (ContentType("video", "mpeg")),
    VIDEO_MP4 (ContentType("video", "mp4")),
    VIDEO_OGG (ContentType("video", "ogg")),
    VIDEO_QUICKTIME (ContentType("video", "quicktime"));

    fun formattedMediaType() = contentType.contentType + '/' + contentType.contentSubtype
}
