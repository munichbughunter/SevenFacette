package de.p7s1.qa.sevenfacette.http

class HttpRestAction (
        val baseURI: String,
        val path: String,
        val body: String,
        val header: Map<String,String>,
        val contentType: String


        //val meats: Int,
        //val hasKetchup: Boolean = false,
        //val hasTomatoes: Boolean = false
)
