package de.p7s1.qa.sevenfacette.config

import com.sksamuel.hoplite.ConfigLoader
import com.sksamuel.hoplite.Masked


data class Config(val name: String,
                  val env: String,
                  val host: String,
                  val port: Int,
                  val user: String,
                  val password: Masked)

fun main() {
    val config = ConfigLoader().loadConfigOrThrow<Config>("/application.json")
    println(config)
}
