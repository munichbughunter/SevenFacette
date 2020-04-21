package de.p7s1.qa.sevenfacette.config.types

import kotlinx.serialization.Serializable

@Serializable
data class KafkaConfig (
        var bootstrap: String,
        var consumer: List<KafkaTopicConfig>,
        var producer: List<KafkaTopicConfig>
)
