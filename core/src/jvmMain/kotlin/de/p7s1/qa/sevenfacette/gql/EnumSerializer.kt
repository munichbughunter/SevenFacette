package de.p7s1.qa.sevenfacette.gql

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException

/**
 * Enum serializer.
 *
 * @author Patrick Döring
 */
class EnumSerializer : JsonSerializer<Enum<*>?>() {

    @Throws(IOException::class)
    override fun serialize(
        value: Enum<*>?,
        gen: JsonGenerator?,
        serializers: SerializerProvider?
    ) {
        gen?.writeNumber(value?.name)
    }
}
