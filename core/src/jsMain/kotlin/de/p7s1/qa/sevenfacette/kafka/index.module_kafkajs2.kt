@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")

package de.p7s1.qa.sevenfacette.kafka

import kotlin.js.*
import kotlin.js.Json
import org.khronos.webgl.*
import org.w3c.dom.*
import org.w3c.dom.events.*
import org.w3c.dom.parsing.*
import org.w3c.dom.svg.*
import org.w3c.dom.url.*
import org.w3c.fetch.*
import org.w3c.files.*
import org.w3c.notifications.*
import org.w3c.performance.*
import org.w3c.workers.*
import org.w3c.xhr.*

typealias ISocketFactory = (host: String, port: Number, ssl: tls.ConnectionOptions, onConnect: () -> Unit) -> net.Socket

typealias ICustomPartitioner = () -> (args: PartitionerArgs) -> Number

typealias DefaultPartitioner = ICustomPartitioner

typealias JavaCompatiblePartitioner = ICustomPartitioner

typealias ValueOf<T> = Any

typealias RemoveInstrumentationEventListener<T> = () -> Unit

typealias ConnectEvent = InstrumentationEvent<Nothing?>

typealias DisconnectEvent = InstrumentationEvent<Nothing?>

typealias RequestEvent = InstrumentationEvent<`T$30`>


typealias RequestTimeoutEvent = InstrumentationEvent<`T$31`>

typealias RequestQueueSizeEvent = InstrumentationEvent<`T$32`>

typealias logCreator = (logLevel: logLevel) -> (entry: LogEntry) -> Unit

typealias ConsumerHeartbeatEvent = InstrumentationEvent<`T$36`>

typealias ConsumerCommitOffsetsEvent = InstrumentationEvent<`T$39`>

typealias ConsumerStartBatchProcessEvent = InstrumentationEvent<IBatchProcessEvent>

typealias ConsumerEndBatchProcessEvent = InstrumentationEvent<IBatchProcessEvent /* IBatchProcessEvent & `T$42` */>

typealias ConsumerEachMessagePayload = EachMessagePayload

typealias ConsumerEachBatchPayload = EachBatchPayload

typealias ConsumerCrashEvent = InstrumentationEvent<`T$43`>

typealias ConsumerGroupJoinEvent = InstrumentationEvent<`T$40`>

typealias ConsumerFetchEvent = InstrumentationEvent<`T$41`>
