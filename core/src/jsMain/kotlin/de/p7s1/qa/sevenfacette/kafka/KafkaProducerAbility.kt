package de.p7s1.qa.sevenfacette.kafka

import io.ktor.util.KtorExperimentalAPI

/**
 * TODO: Add Description
 *
 * @author Patrick Döring
 */
@KtorExperimentalAPI
@ExperimentalJsExport
@JsName("KafkaProducerAbility")
data class KafkaProducerAbility (var key: String?, var value: KProducer) {}
