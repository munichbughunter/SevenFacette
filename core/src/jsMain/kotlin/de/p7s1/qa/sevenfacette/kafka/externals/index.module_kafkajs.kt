@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")
@file:JsModule("kafkajs")

package de.p7s1.qa.sevenfacette.kafka.externals

import Buffer
import kotlin.js.*

external open class Kafka(config: KafkaConfig) {
    open fun producer(config: ProducerConfig = definedExternally): Sender /* Sender & `T$33` */
    open fun consumer(config: ConsumerConfig = definedExternally): Consumer
    open fun admin(config: AdminConfig = definedExternally): Admin
    open fun logger(): Logger
}

external interface KafkaConfig {
    var brokers: Array<String>
    //var ssl: dynamic /* tls.ConnectionOptions? | Boolean? */
    var ssl: SSLOptions?
        get() = definedExternally
        set(value) = definedExternally
    var sasl: SASLOptions?
        get() = definedExternally
        set(value) = definedExternally
    var clientId: String?
        get() = definedExternally
        set(value) = definedExternally
    var connectionTimeout: Number?
        get() = definedExternally
        set(value) = definedExternally
    var authenticationTimeout: Number?
        get() = definedExternally
        set(value) = definedExternally
    var reauthenticationThreshold: Number?
        get() = definedExternally
        set(value) = definedExternally
    var requestTimeout: Number?
        get() = definedExternally
        set(value) = definedExternally
    var enforceRequestTimeout: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var retry: RetryOptions?
        get() = definedExternally
        set(value) = definedExternally
    var socketFactory: ISocketFactory?
        get() = definedExternally
        set(value) = definedExternally
    var logLevel: logLevel?
        get() = definedExternally
        set(value) = definedExternally
    var logCreator: logCreator?
        get() = definedExternally
        set(value) = definedExternally
}

external interface SSLOptions {
    var rejectUnauthorized: Boolean
}

external interface SASLOptions {
    var mechanism: String /* 'plain' | 'scram-sha-256' | 'scram-sha-512' | 'aws' */
    var username: String
    var password: String
}

external interface ProducerConfig {
    var createPartitioner: ICustomPartitioner?
        get() = definedExternally
        set(value) = definedExternally
    var retry: RetryOptions?
        get() = definedExternally
        set(value) = definedExternally
    var metadataMaxAge: Number?
        get() = definedExternally
        set(value) = definedExternally
    var allowAutoTopicCreation: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var idempotent: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var transactionalId: String?
        get() = definedExternally
        set(value) = definedExternally
    var transactionTimeout: Number?
        get() = definedExternally
        set(value) = definedExternally
    var maxInFlightRequests: Number?
        get() = definedExternally
        set(value) = definedExternally
}

external interface Message {
    var key: dynamic /* Buffer? | String? */
        get() = definedExternally
        set(value) = definedExternally
    var value: dynamic /* Buffer? | String? */
        get() = definedExternally
        set(value) = definedExternally
    var partition: Number?
        get() = definedExternally
        set(value) = definedExternally
    var headers: IHeaders?
        get() = definedExternally
        set(value) = definedExternally
    var timestamp: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface PartitionerArgs {
    var topic: String
    var partitionMetadata: Array<PartitionMetadata>
    var message: Message
}



external object Partitioners {
    var DefaultPartitioner: DefaultPartitioner
    var JavaCompatiblePartitioner: JavaCompatiblePartitioner
}

external interface PartitionMetadata {
    var partitionErrorCode: Number
    var partitionId: Number
    var leader: Number
    var replicas: Array<Number>
    var isr: Array<Number>
}

external interface IHeaders {
    @nativeGetter
    operator fun get(key: String): dynamic /* Buffer? | String? */
    @nativeSetter
    operator fun set(key: String, value: Buffer)
    @nativeSetter
    operator fun set(key: String, value: String)
}

external interface ConsumerConfig {
    var groupId: String
    var partitionAssigners: Array<PartitionAssigner>?
        get() = definedExternally
        set(value) = definedExternally
    var metadataMaxAge: Number?
        get() = definedExternally
        set(value) = definedExternally
    var sessionTimeout: Number?
        get() = definedExternally
        set(value) = definedExternally
    var rebalanceTimeout: Number?
        get() = definedExternally
        set(value) = definedExternally
    var heartbeatInterval: Number?
        get() = definedExternally
        set(value) = definedExternally
    var maxBytesPerPartition: Number?
        get() = definedExternally
        set(value) = definedExternally
    var minBytes: Number?
        get() = definedExternally
        set(value) = definedExternally
    var maxBytes: Number?
        get() = definedExternally
        set(value) = definedExternally
    var maxWaitTimeInMs: Number?
        get() = definedExternally
        set(value) = definedExternally
    var retry: RetryOptions?
        get() = definedExternally
        set(value) = definedExternally
    var allowAutoTopicCreation: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var maxInFlightRequests: Number?
        get() = definedExternally
        set(value) = definedExternally
    var readUncommitted: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface PartitionAssigner

external interface `T$0` {
    var nodeId: Number
    var host: String
    var port: Number
}

external interface CoordinatorMetadata {
    var errorCode: Number
    var coordinator: `T$0`
}

external interface `T$1` {
    var nodeId: String
}

external interface `T$2` {
    @nativeGetter
    operator fun get(leader: String): Array<Number>?
    @nativeSetter
    operator fun set(leader: String, value: Array<Number>)
}

external interface `T$3` {
    var groupId: String
}

external interface `T$4` {
    var fromBeginning: Boolean
}

external interface `T$5` {
    var partition: Number
}

external interface `T$6` {
    var topic: String
    var partitions: Array<`T$5`>
    var fromBeginning: Boolean
}

external interface `T$7` {
    var partition: Number
    var offset: String
}

external interface `T$8` {
    var topic: String
    var partitions: Array<`T$7`>
}

external interface Cluster {
    fun isConnected(): Boolean
    fun connect(): Promise<Unit>
    fun disconnect(): Promise<Unit>
    fun refreshMetadata(): Promise<Unit>
    fun refreshMetadataIfNecessary(): Promise<Unit>
    fun addTargetTopic(topic: String): Promise<Unit>
    fun findBroker(node: `T$1`): Promise<Broker>
    fun findControllerBroker(): Promise<Broker>
    fun findTopicPartitionMetadata(topic: String): Array<PartitionMetadata>
    fun findLeaderForPartitions(topic: String, partitions: Array<Number>): `T$2`
    fun findGroupCoordinator(group: `T$3`): Promise<Broker>
    fun findGroupCoordinatorMetadata(group: `T$3`): Promise<CoordinatorMetadata>
    fun defaultOffset(config: `T$4`): Number
    fun fetchTopicsOffset(topics: Array<`T$6`>): Promise<`T$8`>
}

external interface Assignment {
    @nativeGetter
    operator fun get(topic: String): Array<Number>?
    @nativeSetter
    operator fun set(topic: String, value: Array<Number>)
}

external interface GroupMember {
    var memberId: String
    var memberMetadata: Any
}

external interface GroupMemberAssignment {
    var memberId: String
    var memberAssignment: Any
}

external interface GroupState {
    var name: String
    var metadata: Any
}

external interface `T$9` {
    var members: Array<GroupMember>
    var topics: Array<String>
}

external interface `T$10` {
    var topics: Array<String>
}

external interface Assigner {
    var name: String
    var version: Number
    fun assign(group: `T$9`): Promise<Array<GroupMemberAssignment>>
    fun protocol(subscription: `T$10`): GroupState
}

external interface RetryOptions {
    var maxRetryTime: Number?
        get() = definedExternally
        set(value) = definedExternally
    var initialRetryTime: Number?
        get() = definedExternally
        set(value) = definedExternally
    var factor: Number?
        get() = definedExternally
        set(value) = definedExternally
    var multiplier: Number?
        get() = definedExternally
        set(value) = definedExternally
    var retries: Number?
        get() = definedExternally
        set(value) = definedExternally
}

external interface AdminConfig {
    var retry: RetryOptions?
        get() = definedExternally
        set(value) = definedExternally
}

external interface ITopicConfig {
    var topic: String
    var numPartitions: Number?
        get() = definedExternally
        set(value) = definedExternally
    var replicationFactor: Number?
        get() = definedExternally
        set(value) = definedExternally
    var replicaAssignment: Array<Any?>?
        get() = definedExternally
        set(value) = definedExternally
    var configEntries: Array<Any?>?
        get() = definedExternally
        set(value) = definedExternally
}

external interface ITopicMetadata {
    var name: String
    var partitions: Array<PartitionMetadata>
}

external enum class ResourceTypes {
    UNKNOWN /* = 0 */,
    ANY /* = 1 */,
    TOPIC /* = 2 */,
    GROUP /* = 3 */,
    CLUSTER /* = 4 */,
    TRANSACTIONAL_ID /* = 5 */,
    DELEGATION_TOKEN /* = 6 */
}

external interface ResourceConfigQuery {
    var type: ResourceTypes
    var name: String
    var configNames: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
}

external interface ConfigEntries {
    var configName: String
    var configValue: String
    var isDefault: Boolean
    var isSensitive: Boolean
    var readOnly: Boolean
    var configSynonyms: Array<ConfigSynonyms>
}

external interface ConfigSynonyms {
    var configName: String
    var configValue: String
    var configSource: Number
}

external interface `T$11` {
    var configEntries: Array<ConfigEntries>
    var errorCode: Number
    var errorMessage: String
    var resourceName: String
    var resourceType: ResourceTypes
}

external interface DescribeConfigResponse {
    var resources: Array<`T$11`>
    var throttleTime: Number
}

external interface `T$12` {
    var name: String
    var value: String
}

external interface IResourceConfig {
    var type: ResourceTypes
    var name: String
    var configEntries: Array<`T$12`>
}


external interface AdminEvents {
    var CONNECT: String /* 'admin.connect' */
    var DISCONNECT: String /* 'admin.disconnect' */
    var REQUEST: String /* 'admin.network.request' */
    var REQUEST_TIMEOUT: String /* 'admin.network.request_timeout' */
    var REQUEST_QUEUE_SIZE: String /* 'admin.network.request_queue_size' */
}

external interface InstrumentationEvent<T> {
    var id: String
    var type: String
    var timestamp: Number
    var payload: T
}



external interface `T$30` {
    var apiKey: Number
    var apiName: String
    var apiVersion: Number
    var broker: String
    var clientId: String
    var correlationId: Number
    var createdAt: Number
    var duration: Number
    var pendingDuration: Number
    var sentAt: Number
    var size: Number
}


external interface `T$31` {
    var apiKey: Number
    var apiName: String
    var apiVersion: Number
    var broker: String
    var clientId: String
    var correlationId: Number
    var createdAt: Number
    var pendingDuration: Number
    var sentAt: Number
}


external interface `T$32` {
    var broker: String
    var clientId: String
    var queueSize: Number
}


external interface SeekEntry {
    var partition: Number
    var offset: String
}

external interface `T$13` {
    var validateOnly: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var waitForLeaders: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var timeout: Number?
        get() = definedExternally
        set(value) = definedExternally
    var topics: Array<ITopicConfig>
}

external interface `T$14` {
    var topics: Array<String>
    var timeout: Number?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `T$15` {
    var topics: Array<ITopicMetadata>
}

external interface `T$16` {
    var groupId: String
    var topic: String
}

external interface `T$17` {
    var partition: Number
    var offset: String
    var metadata: String?
}

external interface `T$18` {
    var partition: Number
    var offset: String
    var high: String
    var low: String
}

external interface `T$19` {
    var groupId: String
    var topic: String
    var partitions: Array<SeekEntry>
}

external interface `T$20` {
    var groupId: String
    var topic: String
    var earliest: Boolean
}

external interface `T$21` {
    var resources: Array<ResourceConfigQuery>
    var includeSynonyms: Boolean
}

external interface `T$22` {
    var validateOnly: Boolean
    var resources: Array<IResourceConfig>
}

external interface Admin {
    fun connect(): Promise<Unit>
    fun disconnect(): Promise<Unit>
    fun createTopics(options: `T$13`): Promise<Boolean>
    fun deleteTopics(options: `T$14`): Promise<Unit>
    fun fetchTopicMetadata(options: `T$10`): Promise<`T$15`>
    fun fetchOffsets(options: `T$16`): Promise<Array<`T$17`>>
    fun fetchTopicOffsets(topic: String): Promise<Array<`T$18`>>
    fun setOffsets(options: `T$19`): Promise<Unit>
    fun resetOffsets(options: `T$20`): Promise<Unit>
    fun describeConfigs(configs: `T$21`): Promise<DescribeConfigResponse>
    fun alterConfigs(configs: `T$22`): Promise<Any>
    fun logger(): Logger
    fun on(eventName: ValueOf<AdminEvents>, listener: (args: Any) -> Unit): RemoveInstrumentationEventListener<Any>
    var events: AdminEvents
}

external object PartitionAssigners {
    var roundRobin: PartitionAssigner
}

external interface ISerializer<T> {
    fun encode(value: T): Buffer
    fun decode(buffer: Buffer): T?
}

external interface MemberMetadata {
    var version: Number
    var topics: Array<String>
    var userData: Any
}

external interface MemberAssignment {
    var version: Number
    var assignment: Assignment
    var userData: Any
}

external object AssignerProtocol {
    var MemberMetadata: ISerializer<MemberMetadata>
    var MemberAssignment: ISerializer<MemberAssignment>
}

external enum class logLevel {
    NOTHING /* = 0 */,
    ERROR /* = 1 */,
    WARN /* = 2 */,
    INFO /* = 4 */,
    DEBUG /* = 5 */
}

external interface LogEntry {
    var namespace: String
    var level: logLevel
    var label: String
    var log: LoggerEntryContent
}

external interface LoggerEntryContent {
    var timestamp: Date
    var message: String
    @nativeGetter
    operator fun get(key: String): Any?
    @nativeSetter
    operator fun set(key: String, value: Any)
}


external interface Logger {
    var info: (message: String, extra: Any?) -> Unit
    var error: (message: String, extra: Any?) -> Unit
    var warn: (message: String, extra: Any?) -> Unit
    var debug: (message: String, extra: Any?) -> Unit
}

external interface `T$23` {
    var minVersion: Number
    var maxVersion: Number
}

external interface `T$24` {
    @nativeGetter
    operator fun get(apiKey: Number): `T$23`?
    @nativeSetter
    operator fun set(apiKey: Number, value: `T$23`)
}

external interface `T$25` {
    var topicErrorCode: Number
    var topic: Number
    var partitionMetadata: Array<PartitionMetadata>
}

external interface `T$26` {
    var brokers: Array<`T$0`>
    var topicMetadata: Array<`T$25`>
}

external interface `T$27` {
    var groupId: String
    var groupGenerationId: Number
    var memberId: String
    var retentionTime: Number?
        get() = definedExternally
        set(value) = definedExternally
    var topics: Array<`T$8`>
}

external interface Broker {
    fun isConnected(): Boolean
    fun connect(): Promise<Unit>
    fun disconnect(): Promise<Unit>
    fun apiVersions(): Promise<`T$24`>
    fun metadata(topics: Array<String>): Promise<`T$26`>
    fun offsetCommit(request: `T$27`): Promise<Any>
}

external interface KafkaMessage {
    var key: Any
    var value: Any
    var timestamp: String
    var size: Number
    var attributes: Number
    var offset: String
    var headers: IHeaders?
        get() = definedExternally
        set(value) = definedExternally
}

external interface ProducerRecord {
    var topic: String
    var messages: Array<Message>
    var acks: Number?
        get() = definedExternally
        set(value) = definedExternally
    var timeout: Number?
        get() = definedExternally
        set(value) = definedExternally
    var compression: CompressionTypes?
        get() = definedExternally
        set(value) = definedExternally
}

external interface RecordMetadata {
    var topicName: String
    var partition: Number
    var errorCode: Number
    var offset: String
    var timestamp: String
}

external interface TopicMessages {
    var topic: String
    var messages: Array<Message>
}

external interface ProducerBatch {
    var acks: Number?
        get() = definedExternally
        set(value) = definedExternally
    var timeout: Number?
        get() = definedExternally
        set(value) = definedExternally
    var compression: CompressionTypes?
        get() = definedExternally
        set(value) = definedExternally
    var topicMessages: Array<TopicMessages>?
        get() = definedExternally
        set(value) = definedExternally
}

external interface PartitionOffset {
    var partition: Number
    var offset: String
}

external interface TopicOffsets {
    var topic: String
    var partitions: Array<PartitionOffset>
}

external interface Offsets {
    var topics: Array<TopicOffsets>
}

external interface Sender {
    fun send(record: ProducerRecord): Promise<Array<RecordMetadata>>
    fun sendBatch(batch: ProducerBatch): Promise<Array<RecordMetadata>>
}

external interface ProducerEvents {
    var CONNECT: String /* 'producer.connect' */
    var DISCONNECT: String /* 'producer.disconnect' */
    var REQUEST: String /* 'producer.network.request' */
    var REQUEST_TIMEOUT: String /* 'producer.network.request_timeout' */
    var REQUEST_QUEUE_SIZE: String /* 'producer.network.request_queue_size' */
}

external interface `T$33` {
    fun connect(): Promise<Unit>
    fun disconnect(): Promise<Unit>
    fun isIdempotent(): Boolean
    var events: ProducerEvents
    fun on(eventName: ValueOf<ProducerEvents>, listener: (args: Any) -> Unit): RemoveInstrumentationEventListener<Any>
    fun transaction(): Promise<Sender /* Sender & `T$35` */>
    fun logger(): Logger
}

external interface `T$34` {
    var consumerGroupId: String
}

external interface `T$35` {
    fun sendOffsets(offsets: Offsets /* Offsets & `T$34` */): Promise<Unit>
    fun commit(): Promise<Unit>
    fun abort(): Promise<Unit>
    fun isActive(): Boolean
}

external interface ConsumerGroup {
    var groupId: String
    var generationId: Number
    var memberId: String
    var coordinator: Broker
}

external interface MemberDescription {
    var clientHost: String
    var clientId: String
    var memberId: String
    var memberAssignment: Any
    var memberMetadata: Any
}

external interface GroupDescription {
    var groupId: String
    var members: Array<MemberDescription>
    var protocol: String
    var protocolType: String
    var state: String
}

external interface TopicPartitions {
    var topic: String
    var partitions: Array<Number>
}

external interface TopicPartitionOffsetAndMedata {
    var topic: String
    var partition: Number
    var offset: String
    var metadata: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface Batch {
    var topic: String
    var partition: Number
    var highWatermark: String
    var messages: Array<KafkaMessage>
    fun isEmpty(): Boolean
    fun firstOffset(): String?
    fun lastOffset(): String
    fun offsetLag(): String
    fun offsetLagLow(): String
}

external interface ConsumerEvents {
    var HEARTBEAT: String /* 'consumer.heartbeat' */
    var COMMIT_OFFSETS: String /* 'consumer.commit_offsets' */
    var GROUP_JOIN: String /* 'consumer.group_join' */
    var FETCH: String /* 'consumer.fetch' */
    var START_BATCH_PROCESS: String /* 'consumer.start_batch_process' */
    var END_BATCH_PROCESS: String /* 'consumer.end_batch_process' */
    var CONNECT: String /* 'consumer.connect' */
    var DISCONNECT: String /* 'consumer.disconnect' */
    var STOP: String /* 'consumer.stop' */
    var CRASH: String /* 'consumer.crash' */
    var REQUEST: String /* 'consumer.network.request' */
    var REQUEST_TIMEOUT: String /* 'consumer.network.request_timeout' */
    var REQUEST_QUEUE_SIZE: String /* 'consumer.network.request_queue_size' */
}

external interface `T$36` {
    var groupId: String
    var memberId: String
    var groupGenerationId: Number
}


external interface `T$37` {
    var offset: String
    var partition: String
}

external interface `T$38` {
    var topic: String
    var partitions: Array<`T$37`>
}

external interface `T$39` {
    var groupId: String
    var memberId: String
    var groupGenerationId: Number
    var topics: Array<`T$38`>
}


external interface IMemberAssignment {
    @nativeGetter
    operator fun get(key: String): Array<Number>?
    @nativeSetter
    operator fun set(key: String, value: Array<Number>)
}

external interface `T$40` {
    var duration: Number
    var groupId: String
    var isLeader: Boolean
    var leaderId: String
    var groupProtocol: String
    var memberId: String
    var memberAssignment: IMemberAssignment
}


external interface `T$41` {
    var numberOfBatches: Number
    var duration: Number
}


external interface IBatchProcessEvent {
    var topic: String
    var partition: Number
    var highWatermark: String
    var offsetLag: String
    var offsetLagLow: String
    var batchSize: Number
    var firstOffset: String
    var lastOffset: String
}


external interface `T$42` {
    var duration: Number
}


external interface `T$43` {
    var error: Error
    var groupId: String
}


external interface OffsetsByTopicPartition {
    var topics: Array<TopicOffsets>
}

external interface EachMessagePayload {
    var topic: String
    var partition: Number
    var message: KafkaMessage
}

external interface EachBatchPayload {
    var batch: Batch
    fun resolveOffset(offset: String)
    fun heartbeat(): Promise<Unit>
    fun commitOffsetsIfNecessary(offsets: Offsets = definedExternally): Promise<Unit>
    fun uncommittedOffsets(): Promise<OffsetsByTopicPartition>
    fun isRunning(): Boolean
    fun isStale(): Boolean
}



external interface ConsumerRunConfig {
    var autoCommit: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var autoCommitInterval: Number?
        get() = definedExternally
        set(value) = definedExternally
    var autoCommitThreshold: Number?
        get() = definedExternally
        set(value) = definedExternally
    var eachBatchAutoResolve: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var partitionsConsumedConcurrently: Number?
        get() = definedExternally
        set(value) = definedExternally
    var eachBatch: ((payload: EachBatchPayload) -> Promise<Unit>)?
        get() = definedExternally
        set(value) = definedExternally
    var eachMessage: ((payload: EachMessagePayload) -> Promise<Unit>)?
        get() = definedExternally
        set(value) = definedExternally
}

external interface ConsumerSubscribeTopic {
    var topic: dynamic /* String | RegExp */
        get() = definedExternally
        set(value) = definedExternally
    var fromBeginning: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `T$28` {
    var topic: String
    var partition: Number
    var offset: String
}

external interface `T$29` {
    var topic: String
    var partitions: Array<Number>?
        get() = definedExternally
        set(value) = definedExternally
}

external interface Consumer {
    fun connect(): Promise<Unit>
    fun disconnect(): Promise<Unit>
    fun subscribe(topic: ConsumerSubscribeTopic): Promise<Unit>
    fun stop(): Promise<Unit>
    fun run(config: ConsumerRunConfig = definedExternally): Promise<Unit>
    fun commitOffsets(topicPartitions: Array<TopicPartitionOffsetAndMedata>): Promise<Unit>
    fun seek(topicPartition: `T$28`)
    fun describeGroup(): Promise<GroupDescription>
    fun pause(topics: Array<`T$29`>)
    fun paused(): Array<TopicPartitions>
    fun resume(topics: Array<`T$29`>)
    fun on(eventName: ValueOf<ConsumerEvents>, listener: (args: Any) -> Unit): RemoveInstrumentationEventListener<Any>
    fun logger(): Logger
    var events: ConsumerEvents
}

external enum class CompressionTypes {
    None /* = 0 */,
    GZIP /* = 1 */,
    Snappy /* = 2 */,
    LZ4 /* = 3 */,
    ZSTD /* = 4 */
}

external object CompressionCodecs {
}
