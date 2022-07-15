package de.p7s1.qa.sevenfacette.kafka

/**
 * TODO: Add Description
 *
 * @author Patrick Doering
 */
data class KRecord(var key: String?, var value: String?, var offset: Long, var partition: Int)
