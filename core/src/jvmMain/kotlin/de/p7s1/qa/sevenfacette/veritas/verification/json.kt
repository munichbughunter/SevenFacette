package de.p7s1.qa.sevenfacette.veritas.verification

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.BigIntegerNode
import com.fasterxml.jackson.databind.node.BooleanNode
import com.fasterxml.jackson.databind.node.DecimalNode
import com.fasterxml.jackson.databind.node.DoubleNode
import com.fasterxml.jackson.databind.node.FloatNode
import com.fasterxml.jackson.databind.node.IntNode
import com.fasterxml.jackson.databind.node.LongNode
import com.fasterxml.jackson.databind.node.NullNode
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.databind.node.ShortNode
import com.fasterxml.jackson.databind.node.TextNode
import com.fasterxml.jackson.databind.node.ValueNode
import com.jayway.jsonpath.Configuration
import com.jayway.jsonpath.JsonPath
import com.jayway.jsonpath.spi.json.JacksonJsonNodeJsonProvider
import de.p7s1.qa.sevenfacette.veritas.Verify
import de.p7s1.qa.sevenfacette.veritas.verification.utils.expected
import de.p7s1.qa.sevenfacette.veritas.verification.utils.show
import org.skyscreamer.jsonassert.JSONAssert
import java.math.BigDecimal
import java.math.BigInteger

private val mapper = ObjectMapper()

private val jsonPathConf = Configuration.builder()
        .jsonProvider(JacksonJsonNodeJsonProvider(mapper))
        .build()

fun jsonNodeOf(str: String) = mapper.readTree(str)

fun Verify<JsonNode>.isInt(): Verify<Int> = transform { actual ->
    if (actual.isInt) return@transform actual.intValue()
    expected("class:${show(Int::class)} but was class:${show(actual::class)}")
}

fun Verify<JsonNode>.isShort(): Verify<Short> = transform { actual ->
    if (actual.isShort) return@transform actual.shortValue()
    expected("class:${show(Int::class)} but was class:${show(actual::class)}")
}

fun Verify<JsonNode>.isLong(): Verify<Long> = transform { actual ->
    if (actual.isLong) return@transform actual.longValue()
    expected("class:${show(Long::class)} but was class:${show(actual::class)}")
}

fun Verify<JsonNode>.isBigInteger(): Verify<BigInteger> = transform { actual ->
    if (actual.isBigInteger) return@transform actual.bigIntegerValue()
    expected("class:${show(BigInteger::class)} but was class:${show(actual::class)}")
}

fun Verify<JsonNode>.isString(): Verify<String> = transform { actual ->
    if (actual.isTextual) return@transform actual.textValue()
    expected("class:${show(String::class)} but was class:${show(actual::class)}")
}

fun Verify<JsonNode>.isFloat(): Verify<Float> = transform { actual ->
    if (actual.isFloat) return@transform actual.floatValue()
    expected("class:${show(Double::class)} but was class:${show(actual::class)}")
}

fun Verify<JsonNode>.isDouble(): Verify<Double> = transform { actual ->
    if (actual.isDouble) return@transform actual.doubleValue()
    expected("class:${show(Double::class)} but was class:${show(actual::class)}")
}

fun Verify<JsonNode>.isBigDecimal(): Verify<BigDecimal> = transform { actual ->
    if (actual.isBigDecimal) return@transform actual.decimalValue()
    expected("class:${show(BigDecimal::class)} but was class:${show(actual::class)}")
}

fun Verify<JsonNode>.isBoolean(): Verify<Boolean> = transform { actual ->
    if (actual.isBoolean) return@transform actual.booleanValue()
    expected("class:${show(Boolean::class)} but was class:${show(actual::class)}")
}

fun Verify<JsonNode>.isObject(): Verify<ObjectNode> = transform { actual ->
    if (actual.isObject) return@transform actual as ObjectNode
    expected("class:${show(ObjectNode::class)} but was class:${show(actual::class)}")
}

fun Verify<JsonNode>.isArray(): Verify<ArrayNode> = transform { actual ->
    if (actual.isArray) return@transform actual as ArrayNode
    expected("class:${show(ArrayNode::class)} but was class:${show(actual::class)}")
}

fun Verify<JsonNode>.isValueNodeArray(): Verify<List<Any?>> = transform { actual ->
    if (actual.isArray) {
        return@transform Sequence { actual.elements() }.onEach {
            if (it is ValueNode == false) {
                expected("element class:${show(ValueNode::class)} but was element class:${show(it::class)}")
            }
        }.map {
            @Suppress("IMPLICIT_CAST_TO_ANY")
            when (it) {
                is DoubleNode -> it.doubleValue()
                is FloatNode -> it.floatValue()
                is IntNode -> it.intValue()
                is BigIntegerNode -> it.bigIntegerValue()
                is DecimalNode -> it.decimalValue()
                is ShortNode -> it.shortValue()
                is LongNode -> it.longValue()
                is NullNode -> null
                is BooleanNode -> it.booleanValue()
                is TextNode -> it.textValue()
                else -> throw AssertionError("Unexpected node type ${it::class.java}")
            }
        }.toList()
    }
    expected("class:${show(ArrayNode::class)} but was class:${show(actual::class)}")
}

fun Verify<JsonNode>.isNullLiteral(): Verify<NullNode> = transform { actual ->
    if (actual.isNull) return@transform actual as NullNode
    expected("class:${show(NullNode::class)} but was class:${show(actual::class)}")
}

fun Verify<JsonNode>.jsonPath(path: String): Verify<JsonNode> = transform { actual ->
    return@transform JsonPath.using(jsonPathConf).parse(actual).read<JsonNode>(path)
}

fun Verify<String?>.jsonIsEqualTo(other: String?, ignoreCase: Boolean = false) = given { actual ->
     JSONAssert.assertEquals(other, actual, ignoreCase)
}

fun Verify<String?>.jsonIsNotEqualTo(other: String?, ignoreCase: Boolean = false) = given { actual ->
    JSONAssert.assertNotEquals(other, actual, ignoreCase)
}
