package de.p7s1.qa.sevenfacette.config.types

import kotlinx.serialization.Serializable

@Serializable
data class DKafkaConfig (
        var bootstrapServer: String,
        var consumer: Map<String, KafkaTopicConfig>,
        var producer: Map<String, KafkaTopicConfig>
)