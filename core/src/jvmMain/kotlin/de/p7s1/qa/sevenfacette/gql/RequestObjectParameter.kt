package de.p7s1.qa.sevenfacette.gql

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module

/**
 * Represents request object parameter.
 *
 * @author Patrick Döring
 */
class RequestObjectParameter(data: Any) {

    private val objectMapper: ObjectMapper = ObjectMapper()
    private var data: Any
    fun getData(): Any {
        return data
    }

    fun setData(data: Any) {
        this.data = data
    }

    override fun toString(): String {
        var json = dataToJson()
        json = json.replace("\\\"".toRegex(), "\\\\\"")
        return json
    }

    private fun dataToJson(): String {
        var str = "null"
        try {
            str = objectMapper.writeValueAsString(getData())
        } catch (e: JsonProcessingException) {
            e.printStackTrace()
        }
        return str
    }

    init {
        objectMapper.registerModule(Jdk8Module())
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        objectMapper.configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES, false)
        val simpleModule = SimpleModule()
        simpleModule.addSerializer(Enum::class.java, EnumSerializer())
        objectMapper.registerModule(simpleModule)
        this.data = data
    }
}
