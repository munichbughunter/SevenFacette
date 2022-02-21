@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")

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

typealias Without<T, U> = Any

typealias XOR<T, U> = Any

external open class Kafka(config: KafkaConfig) {
    open fun producer(config: ProducerConfig = definedExternally): Sender /* Sender & `T$52` */
    open fun consumer(config: ConsumerConfig): Consumer
    open fun admin(config: AdminConfig = definedExternally): Admin
    open fun logger(): Logger
}

typealias BrokersFunction = () -> dynamic

external interface KafkaConfig {
    var brokers: dynamic /* Array<String> | BrokersFunction */
        get() = definedExternally
        set(value) = definedExternally
    var ssl: dynamic /* tls.ConnectionOptions? | Boolean? */
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

external interface ISocketFactoryArgs {
    var host: String
    var port: Number
    var ssl: Any
    var onConnect: () -> Unit
}

typealias ISocketFactory = (args: ISocketFactoryArgs) -> net.Socket

external interface OauthbearerProviderResponse {
    var value: String
}

external interface `T$0` {
    var username: String
    var password: String
}

external interface `T$1` {
    var authorizationIdentity: String
    var accessKeyId: String
    var secretAccessKey: String
    var sessionToken: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `T$2` {
    var oauthBearerProvider: () -> Promise<OauthbearerProviderResponse>
}

external interface SASLMechanismOptionsMap {
    operator fun get(key: String): `T$0`
    operator fun set(key: String, value: `T$0`)
    var plain: `T$0`
    var aws: `T$1`
    var oauthbearer: `T$2`
}

typealias SASLMechanism = Any

typealias SASLMechanismOptions<T> = Any

typealias SASLOptions = SASLMechanismOptions<SASLMechanism>

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

typealias ICustomPartitioner = () -> (args: PartitionerArgs) -> Number

typealias DefaultPartitioner = ICustomPartitioner

typealias JavaCompatiblePartitioner = ICustomPartitioner

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
    var offlineReplicas: Array<Number>?
        get() = definedExternally
        set(value) = definedExternally
}

external interface IHeaders {
    @nativeGetter
    operator fun get(key: String): dynamic /* Buffer? | String? */
    @nativeSetter
    operator fun set(key: String, value: Buffer?)
    @nativeSetter
    operator fun set(key: String, value: String?)
}

external interface `T$3` {
    var restartOnFailure: ((err: Error) -> Promise<Boolean>)?
        get() = definedExternally
        set(value) = definedExternally
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
    var retry: RetryOptions? /* RetryOptions? & `T$3`? */
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
    var rackId: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `T$46` {
    var cluster: Cluster
}

typealias PartitionAssigner = (config: `T$46`) -> Assigner

external interface `T$4` {
    var nodeId: Number
    var host: String
    var port: Number
}

external interface CoordinatorMetadata {
    var errorCode: Number
    var coordinator: `T$4`
}

external interface `T$5` {
    var nodeId: String
}

external interface `T$6` {
    @nativeGetter
    operator fun get(leader: String): Array<Number>?
    @nativeSetter
    operator fun set(leader: String, value: Array<Number>)
}

external interface `T$7` {
    var groupId: String
}

external interface `T$8` {
    var fromBeginning: Boolean
}

external interface `T$9` {
    var partition: Number
}

external interface `T$10` {
    var topic: String
    var partitions: Array<`T$9`>
}

external interface `T$11` {
    var fromTimestamp: Number
}

external interface Cluster {
    fun isConnected(): Boolean
    fun connect(): Promise<Unit>
    fun disconnect(): Promise<Unit>
    fun refreshMetadata(): Promise<Unit>
    fun refreshMetadataIfNecessary(): Promise<Unit>
    fun addTargetTopic(topic: String): Promise<Unit>
    fun findBroker(node: `T$5`): Promise<Broker>
    fun findControllerBroker(): Promise<Broker>
    fun findTopicPartitionMetadata(topic: String): Array<PartitionMetadata>
    fun findLeaderForPartitions(topic: String, partitions: Array<Number>): `T$6`
    fun findGroupCoordinator(group: `T$7`): Promise<Broker>
    fun findGroupCoordinatorMetadata(group: `T$7`): Promise<CoordinatorMetadata>
    fun defaultOffset(config: `T$8`): Number
    fun fetchTopicsOffset(topics: Array<`T$10` /* `T$10` & XOR<`T$8`, `T$11`> */>): Promise<Array<TopicOffsets>>
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

external interface `T$12` {
    var members: Array<GroupMember>
    var topics: Array<String>
}

external interface `T$13` {
    var topics: Array<String>
}

external interface Assigner {
    var name: String
    var version: Number
    fun assign(group: `T$12`): Promise<Array<GroupMemberAssignment>>
    fun protocol(subscription: `T$13`): GroupState
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

external interface ITopicPartitionConfig {
    var topic: String
    var count: Number
    var assignments: Array<Array<Number>>?
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

external enum class AclResourceTypes {
    UNKNOWN /* = 0 */,
    ANY /* = 1 */,
    TOPIC /* = 2 */,
    GROUP /* = 3 */,
    CLUSTER /* = 4 */,
    TRANSACTIONAL_ID /* = 5 */,
    DELEGATION_TOKEN /* = 6 */
}

external enum class ConfigResourceTypes {
    UNKNOWN /* = 0 */,
    TOPIC /* = 2 */,
    BROKER /* = 4 */,
    BROKER_LOGGER /* = 8 */
}

external enum class ConfigSource {
    UNKNOWN /* = 0 */,
    TOPIC_CONFIG /* = 1 */,
    DYNAMIC_BROKER_CONFIG /* = 2 */,
    DYNAMIC_DEFAULT_BROKER_CONFIG /* = 3 */,
    STATIC_BROKER_CONFIG /* = 4 */,
    DEFAULT_CONFIG /* = 5 */,
    DYNAMIC_BROKER_LOGGER_CONFIG /* = 6 */
}

external enum class AclPermissionTypes {
    UNKNOWN /* = 0 */,
    ANY /* = 1 */,
    DENY /* = 2 */,
    ALLOW /* = 3 */
}

external enum class AclOperationTypes {
    UNKNOWN /* = 0 */,
    ANY /* = 1 */,
    ALL /* = 2 */,
    READ /* = 3 */,
    WRITE /* = 4 */,
    CREATE /* = 5 */,
    DELETE /* = 6 */,
    ALTER /* = 7 */,
    DESCRIBE /* = 8 */,
    CLUSTER_ACTION /* = 9 */,
    DESCRIBE_CONFIGS /* = 10 */,
    ALTER_CONFIGS /* = 11 */,
    IDEMPOTENT_WRITE /* = 12 */
}

external enum class ResourcePatternTypes {
    UNKNOWN /* = 0 */,
    ANY /* = 1 */,
    MATCH /* = 2 */,
    LITERAL /* = 3 */,
    PREFIXED /* = 4 */
}

external interface ResourceConfigQuery {
    var type: dynamic /* ResourceTypes | ConfigResourceTypes */
        get() = definedExternally
        set(value) = definedExternally
    var name: String
    var configNames: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
}

external interface ConfigEntries {
    var configName: String
    var configValue: String
    var isDefault: Boolean
    var configSource: ConfigSource
    var isSensitive: Boolean
    var readOnly: Boolean
    var configSynonyms: Array<ConfigSynonyms>
}

external interface ConfigSynonyms {
    var configName: String
    var configValue: String
    var configSource: ConfigSource
}

external interface `T$14` {
    var configEntries: Array<ConfigEntries>
    var errorCode: Number
    var errorMessage: String
    var resourceName: String
    var resourceType: dynamic /* ResourceTypes | ConfigResourceTypes */
        get() = definedExternally
        set(value) = definedExternally
}

external interface DescribeConfigResponse {
    var resources: Array<`T$14`>
    var throttleTime: Number
}

external interface `T$15` {
    var name: String
    var value: String
}

external interface IResourceConfig {
    var type: dynamic /* ResourceTypes | ConfigResourceTypes */
        get() = definedExternally
        set(value) = definedExternally
    var name: String
    var configEntries: Array<`T$15`>
}

typealias ValueOf<T> = Any

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

typealias RemoveInstrumentationEventListener<T> = () -> Unit

typealias ConnectEvent = InstrumentationEvent<Nothing?>

typealias DisconnectEvent = InstrumentationEvent<Nothing?>

external interface `T$47` {
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

typealias RequestEvent = InstrumentationEvent<`T$47`>

external interface `T$48` {
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

typealias RequestTimeoutEvent = InstrumentationEvent<`T$48`>

external interface `T$49` {
    var broker: String
    var clientId: String
    var queueSize: Number
}

typealias RequestQueueSizeEvent = InstrumentationEvent<`T$49`>

typealias SeekEntry = PartitionOffset

external interface `T$50` {
    var metadata: String?
}

external interface Acl {
    var principal: String
    var host: String
    var operation: AclOperationTypes
    var permissionType: AclPermissionTypes
}

external interface AclResource {
    var resourceType: AclResourceTypes
    var resourceName: String
    var resourcePatternType: ResourcePatternTypes
}

external interface `T$51` {
    var acls: Array<Acl>
}

external interface DescribeAclResponse {
    var throttleTime: Number
    var errorCode: Number
    var errorMessage: String?
        get() = definedExternally
        set(value) = definedExternally
    var resources: Array<AclResource /* AclResource & `T$51` */>
}

external interface AclFilter {
    var resourceType: AclResourceTypes
    var resourceName: String?
        get() = definedExternally
        set(value) = definedExternally
    var resourcePatternType: ResourcePatternTypes
    var principal: String?
        get() = definedExternally
        set(value) = definedExternally
    var host: String?
        get() = definedExternally
        set(value) = definedExternally
    var operation: AclOperationTypes
    var permissionType: AclPermissionTypes
}

external interface MatchingAcl {
    var errorCode: Number
    var errorMessage: String?
        get() = definedExternally
        set(value) = definedExternally
    var resourceType: AclResourceTypes
    var resourceName: String
    var resourcePatternType: ResourcePatternTypes
    var principal: String
    var host: String
    var operation: AclOperationTypes
    var permissionType: AclPermissionTypes
}

external interface DeleteAclFilterResponses {
    var errorCode: Number
    var errorMessage: String?
        get() = definedExternally
        set(value) = definedExternally
    var matchingAcls: Array<MatchingAcl>
}

external interface DeleteAclResponse {
    var throttleTime: Number
    var filterResponses: Array<DeleteAclFilterResponses>
}

external interface `T$16` {
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

external interface `T$17` {
    var topics: Array<String>
    var timeout: Number?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `T$18` {
    var validateOnly: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var timeout: Number?
        get() = definedExternally
        set(value) = definedExternally
    var topicPartitions: Array<ITopicPartitionConfig>
}

external interface `T$19` {
    var topics: Array<ITopicMetadata>
}

external interface `T$20` {
    var groupId: String
    var topic: String
    var resolveOffsets: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `T$21` {
    var groupId: String
    var topics: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
    var resolveOffsets: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `T$22` {
    var topic: String
    var partitions: Array<PartitionOffset /* PartitionOffset & `T$50` */>
}

external interface `T$23` {
    var high: String
    var low: String
}

external interface `T$24` {
    var brokers: Array<`T$4`>
    var controller: Number?
    var clusterId: String
}

external interface `T$25` {
    var groupId: String
    var topic: String
    var partitions: Array<SeekEntry>
}

external interface `T$26` {
    var groupId: String
    var topic: String
    var earliest: Boolean
}

external interface `T$27` {
    var resources: Array<ResourceConfigQuery>
    var includeSynonyms: Boolean
}

external interface `T$28` {
    var validateOnly: Boolean
    var resources: Array<IResourceConfig>
}

external interface `T$29` {
    var groups: Array<GroupOverview>
}

external interface `T$30` {
    var filters: Array<AclFilter>
}

external interface `T$31` {
    var acl: Array<Acl /* Acl & AclResource */>
}

external interface `T$32` {
    var topic: String
    var partitions: Array<SeekEntry>
}

external interface Admin {
    fun connect(): Promise<Unit>
    fun disconnect(): Promise<Unit>
    fun listTopics(): Promise<Array<String>>
    fun createTopics(options: `T$16`): Promise<Boolean>
    fun deleteTopics(options: `T$17`): Promise<Unit>
    fun createPartitions(options: `T$18`): Promise<Boolean>
    fun fetchTopicMetadata(options: `T$13` = definedExternally): Promise<`T$19`>
    fun fetchOffsets(options: `T$20`): Promise<Array<PartitionOffset /* PartitionOffset & `T$50` */>>
    fun fetchOffsets(options: `T$21`): Promise<Array<`T$22`>>
    fun fetchTopicOffsets(topic: String): Promise<Array<SeekEntry /* SeekEntry & `T$23` */>>
    fun fetchTopicOffsetsByTimestamp(topic: String, timestamp: Number = definedExternally): Promise<Array<SeekEntry>>
    fun describeCluster(): Promise<`T$24`>
    fun setOffsets(options: `T$25`): Promise<Unit>
    fun resetOffsets(options: `T$26`): Promise<Unit>
    fun describeConfigs(configs: `T$27`): Promise<DescribeConfigResponse>
    fun alterConfigs(configs: `T$28`): Promise<Any>
    fun listGroups(): Promise<`T$29`>
    fun deleteGroups(groupIds: Array<String>): Promise<Array<DeleteGroupsResult>>
    fun describeGroups(groupIds: Array<String>): Promise<GroupDescriptions>
    fun describeAcls(options: AclFilter): Promise<DescribeAclResponse>
    fun deleteAcls(options: `T$30`): Promise<DeleteAclResponse>
    fun createAcls(options: `T$31`): Promise<Boolean>
    fun deleteTopicRecords(options: `T$32`): Promise<Unit>
    fun logger(): Logger
    fun on(eventName: Any, listener: (event: ConnectEvent) -> Unit): RemoveInstrumentationEventListener<Any>
    fun on(eventName: Any, listener: (event: DisconnectEvent) -> Unit): RemoveInstrumentationEventListener<Any>
    fun on(eventName: Any, listener: (event: RequestEvent) -> Unit): RemoveInstrumentationEventListener<Any>
    fun on(eventName: Any, listener: (event: RequestQueueSizeEvent) -> Unit): RemoveInstrumentationEventListener<Any>
    fun on(eventName: Any, listener: (event: RequestTimeoutEvent) -> Unit): RemoveInstrumentationEventListener<Any>
    fun on(eventName: ValueOf<AdminEvents>, listener: (event: InstrumentationEvent<Any>) -> Unit): RemoveInstrumentationEventListener<Any>
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
    var timestamp: String
    var message: String
    @nativeGetter
    operator fun get(key: String): Any?
    @nativeSetter
    operator fun set(key: String, value: Any)
}

typealias logCreator = (logLevel: logLevel) -> (entry: LogEntry) -> Unit

external interface Logger {
    var info: (message: String, extra: Any?) -> Unit
    var error: (message: String, extra: Any?) -> Unit
    var warn: (message: String, extra: Any?) -> Unit
    var debug: (message: String, extra: Any?) -> Unit
    var namespace: (namespace: String, logLevel: logLevel) -> Logger
    var setLogLevel: (logLevel: logLevel) -> Unit
}

external interface `T$33` {
    var nodeId: Number
    var host: String
    var port: Number
    var rack: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `T$34` {
    var topicErrorCode: Number
    var topic: String
    var partitionMetadata: Array<PartitionMetadata>
}

external interface BrokerMetadata {
    var brokers: Array<`T$33`>
    var topicMetadata: Array<`T$34`>
}

external interface `T$35` {
    var minVersion: Number
    var maxVersion: Number
}

external interface ApiVersions {
    @nativeGetter
    operator fun get(apiKey: Number): `T$35`?
    @nativeSetter
    operator fun set(apiKey: Number, value: `T$35`)
}

external interface `T$36` {
    var groupId: String
    var groupGenerationId: Number
    var memberId: String
    var retentionTime: Number?
        get() = definedExternally
        set(value) = definedExternally
    var topics: Array<TopicOffsets>
}

external interface `T$37` {
    var groupId: String
    var topics: Array<TopicOffsets>
}

external interface `T$38` {
    var responses: Array<TopicOffsets>
}

external interface `T$39` {
    var partition: Number
    var fetchOffset: String
    var maxBytes: Number
}

external interface `T$40` {
    var topic: String
    var partitions: Array<`T$39`>
}

external interface `T$41` {
    var replicaId: Number?
        get() = definedExternally
        set(value) = definedExternally
    var isolationLevel: Number?
        get() = definedExternally
        set(value) = definedExternally
    var maxWaitTime: Number?
        get() = definedExternally
        set(value) = definedExternally
    var minBytes: Number?
        get() = definedExternally
        set(value) = definedExternally
    var maxBytes: Number?
        get() = definedExternally
        set(value) = definedExternally
    var topics: Array<`T$40`>
    var rackId: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `T$42` {
    var partition: Number
    var firstSequence: Number?
        get() = definedExternally
        set(value) = definedExternally
    var messages: Array<Message>
}

external interface `T$43` {
    var topic: String
    var partitions: Array<`T$42`>
}

external interface `T$44` {
    var topicData: Array<`T$43`>
    var transactionalId: String?
        get() = definedExternally
        set(value) = definedExternally
    var producerId: Number?
        get() = definedExternally
        set(value) = definedExternally
    var producerEpoch: Number?
        get() = definedExternally
        set(value) = definedExternally
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

external interface Broker {
    fun isConnected(): Boolean
    fun connect(): Promise<Unit>
    fun disconnect(): Promise<Unit>
    fun apiVersions(): Promise<ApiVersions>
    fun metadata(topics: Array<String>): Promise<BrokerMetadata>
    fun offsetCommit(request: `T$36`): Promise<Any>
    fun offsetFetch(request: `T$37`): Promise<`T$38`>
    fun fetch(request: `T$41`): Promise<Any>
    fun produce(request: `T$44`): Promise<Any>
}

external interface KafkaMessage {
    var key: Any?
    var value: Any?
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
    var offset: String?
        get() = definedExternally
        set(value) = definedExternally
    var timestamp: String?
        get() = definedExternally
        set(value) = definedExternally
    var baseOffset: String?
        get() = definedExternally
        set(value) = definedExternally
    var logAppendTime: String?
        get() = definedExternally
        set(value) = definedExternally
    var logStartOffset: String?
        get() = definedExternally
        set(value) = definedExternally
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

external interface `T$52` {
    fun connect(): Promise<Unit>
    fun disconnect(): Promise<Unit>
    fun isIdempotent(): Boolean
    var events: ProducerEvents
    fun on(eventName: Any, listener: (event: ConnectEvent) -> Unit): RemoveInstrumentationEventListener<Any>
    fun on(eventName: Any, listener: (event: DisconnectEvent) -> Unit): RemoveInstrumentationEventListener<Any>
    fun on(eventName: Any, listener: (event: RequestEvent) -> Unit): RemoveInstrumentationEventListener<Any>
    fun on(eventName: Any, listener: (event: RequestQueueSizeEvent) -> Unit): RemoveInstrumentationEventListener<Any>
    fun on(eventName: Any, listener: (event: RequestTimeoutEvent) -> Unit): RemoveInstrumentationEventListener<Any>
    fun on(eventName: ValueOf<ProducerEvents>, listener: (event: InstrumentationEvent<Any>) -> Unit): RemoveInstrumentationEventListener<Any>
    fun transaction(): Promise<Sender /* Sender & `T$54` */>
    fun logger(): Logger
}

external interface `T$53` {
    var consumerGroupId: String
}

external interface `T$54` {
    fun sendOffsets(offsets: Offsets /* Offsets & `T$53` */): Promise<Unit>
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
    var state: String /* 'Unknown' | 'PreparingRebalance' | 'CompletingRebalance' | 'Stable' | 'Dead' | 'Empty' */
}

external interface GroupDescriptions {
    var groups: Array<GroupDescription>
}

external interface TopicPartitions {
    var topic: String
    var partitions: Array<Number>
}

external interface TopicPartition {
    var topic: String
    var partition: Number
}

external interface `T$55` {
    var offset: String
}

external interface `T$56` {
    var metadata: String?
        get() = definedExternally
        set(value) = definedExternally
}

typealias TopicPartitionOffsetAndMedata = TopicPartition

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

external interface GroupOverview {
    var groupId: String
    var protocolType: String
}

external interface DeleteGroupsResult {
    var groupId: String
    var errorCode: Number?
        get() = definedExternally
        set(value) = definedExternally
    var error: KafkaJSProtocolError?
        get() = definedExternally
        set(value) = definedExternally
}

external interface ConsumerEvents {
    var HEARTBEAT: String /* 'consumer.heartbeat' */
    var COMMIT_OFFSETS: String /* 'consumer.commit_offsets' */
    var GROUP_JOIN: String /* 'consumer.group_join' */
    var FETCH_START: String /* 'consumer.fetch_start' */
    var FETCH: String /* 'consumer.fetch' */
    var START_BATCH_PROCESS: String /* 'consumer.start_batch_process' */
    var END_BATCH_PROCESS: String /* 'consumer.end_batch_process' */
    var CONNECT: String /* 'consumer.connect' */
    var DISCONNECT: String /* 'consumer.disconnect' */
    var STOP: String /* 'consumer.stop' */
    var CRASH: String /* 'consumer.crash' */
    var REBALANCING: String /* 'consumer.rebalancing' */
    var RECEIVED_UNSUBSCRIBED_TOPICS: String /* 'consumer.received_unsubscribed_topics' */
    var REQUEST: String /* 'consumer.network.request' */
    var REQUEST_TIMEOUT: String /* 'consumer.network.request_timeout' */
    var REQUEST_QUEUE_SIZE: String /* 'consumer.network.request_queue_size' */
}

external interface `T$57` {
    var groupId: String
    var memberId: String
    var groupGenerationId: Number
}

typealias ConsumerHeartbeatEvent = InstrumentationEvent<`T$57`>

external interface `T$58` {
    var groupId: String
    var memberId: String
    var groupGenerationId: Number
    var topics: Array<TopicOffsets>
}

typealias ConsumerCommitOffsetsEvent = InstrumentationEvent<`T$58`>

external interface IMemberAssignment {
    @nativeGetter
    operator fun get(key: String): Array<Number>?
    @nativeSetter
    operator fun set(key: String, value: Array<Number>)
}

external interface `T$59` {
    var duration: Number
    var groupId: String
    var isLeader: Boolean
    var leaderId: String
    var groupProtocol: String
    var memberId: String
    var memberAssignment: IMemberAssignment
}

typealias ConsumerGroupJoinEvent = InstrumentationEvent<`T$59`>

external interface `T$60` {
    var numberOfBatches: Number
    var duration: Number
}

typealias ConsumerFetchEvent = InstrumentationEvent<`T$60`>

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

typealias ConsumerStartBatchProcessEvent = InstrumentationEvent<IBatchProcessEvent>

external interface `T$61` {
    var duration: Number
}

typealias ConsumerEndBatchProcessEvent = InstrumentationEvent<IBatchProcessEvent /* IBatchProcessEvent & `T$61` */>

external interface `T$62` {
    var error: Error
    var groupId: String
    var restart: Boolean
}

typealias ConsumerCrashEvent = InstrumentationEvent<`T$62`>

external interface `T$63` {
    var groupId: String
    var memberId: String
}

typealias ConsumerRebalancingEvent = InstrumentationEvent<`T$63`>

external interface `T$64` {
    var groupId: String
    var generationId: Number
    var memberId: String
    var assignedTopics: Array<String>
    var topicsSubscribed: Array<String>
    var topicsNotSubscribed: Array<String>
}

typealias ConsumerReceivedUnsubcribedTopicsEvent = InstrumentationEvent<`T$64`>

external interface OffsetsByTopicPartition {
    var topics: Array<TopicOffsets>
}

external interface EachMessagePayload {
    var topic: String
    var partition: Number
    var message: KafkaMessage
    fun heartbeat(): Promise<Unit>
}

external interface EachBatchPayload {
    var batch: Batch
    fun resolveOffset(offset: String)
    fun heartbeat(): Promise<Unit>
    fun commitOffsetsIfNecessary(offsets: Offsets = definedExternally): Promise<Unit>
    fun uncommittedOffsets(): OffsetsByTopicPartition
    fun isRunning(): Boolean
    fun isStale(): Boolean
}

typealias ConsumerEachMessagePayload = EachMessagePayload

typealias ConsumerEachBatchPayload = EachBatchPayload

typealias EachBatchHandler = (payload: EachBatchPayload) -> Promise<Unit>

typealias EachMessageHandler = (payload: EachMessagePayload) -> Promise<Unit>

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
    var eachBatch: EachBatchHandler?
        get() = definedExternally
        set(value) = definedExternally
    var eachMessage: EachMessageHandler?
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

external interface `T$45` {
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
    fun commitOffsets(topicPartitions: Array<TopicPartition /* TopicPartition & `T$55` */>): Promise<Unit>
    fun seek(topicPartitionOffset: TopicPartition /* TopicPartition & `T$55` */)
    fun describeGroup(): Promise<GroupDescription>
    fun pause(topics: Array<`T$45`>)
    fun paused(): Array<TopicPartitions>
    fun resume(topics: Array<`T$45`>)
    fun on(eventName: Any, listener: (event: ConsumerHeartbeatEvent) -> Unit): RemoveInstrumentationEventListener<Any>
    fun on(eventName: Any, listener: (event: ConsumerCommitOffsetsEvent) -> Unit): RemoveInstrumentationEventListener<Any>
    fun on(eventName: Any, listener: (event: ConsumerGroupJoinEvent) -> Unit): RemoveInstrumentationEventListener<Any>
    fun on(eventName: Any, listener: (event: InstrumentationEvent<Any>) -> Unit): RemoveInstrumentationEventListener<Any>
    fun on(eventName: Any, listener: (event: ConsumerFetchEvent) -> Unit): RemoveInstrumentationEventListener<Any>
    fun on(eventName: Any, listener: (event: ConsumerStartBatchProcessEvent) -> Unit): RemoveInstrumentationEventListener<Any>
    fun on(eventName: Any, listener: (event: ConsumerEndBatchProcessEvent) -> Unit): RemoveInstrumentationEventListener<Any>
    fun on(eventName: Any, listener: (event: ConnectEvent) -> Unit): RemoveInstrumentationEventListener<Any>
    fun on(eventName: Any, listener: (event: DisconnectEvent) -> Unit): RemoveInstrumentationEventListener<Any>
    fun on(eventName: Any, listener: (event: InstrumentationEvent<Nothing?>) -> Unit): RemoveInstrumentationEventListener<Any>
    fun on(eventName: Any, listener: (event: ConsumerCrashEvent) -> Unit): RemoveInstrumentationEventListener<Any>
    fun on(eventName: Any, listener: (event: ConsumerRebalancingEvent) -> Unit): RemoveInstrumentationEventListener<Any>
    fun on(eventName: Any, listener: (event: ConsumerReceivedUnsubcribedTopicsEvent) -> Unit): RemoveInstrumentationEventListener<Any>
    fun on(eventName: Any, listener: (event: RequestEvent) -> Unit): RemoveInstrumentationEventListener<Any>
    fun on(eventName: Any, listener: (event: RequestTimeoutEvent) -> Unit): RemoveInstrumentationEventListener<Any>
    fun on(eventName: Any, listener: (event: RequestQueueSizeEvent) -> Unit): RemoveInstrumentationEventListener<Any>
    fun on(eventName: ValueOf<ConsumerEvents>, listener: (event: InstrumentationEvent<Any>) -> Unit): RemoveInstrumentationEventListener<Any>
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

typealias KafkaJSError = Error

external open class KafkaJSNonRetriableError : KafkaJSError {
    constructor(e: Error)
    constructor(e: String)
}

external open class KafkaJSProtocolError : KafkaJSError {
    open var code: Number
    open var type: String
    constructor(e: Error)
    constructor(e: String)
}

external open class KafkaJSOffsetOutOfRange : KafkaJSProtocolError {
    open var topic: String
    open var partition: Number
    constructor(e: Error, metadata: KafkaJSOffsetOutOfRangeMetadata = definedExternally)
    constructor(e: String, metadata: KafkaJSOffsetOutOfRangeMetadata = definedExternally)
}

external open class KafkaJSNumberOfRetriesExceeded : KafkaJSNonRetriableError {
    open var originalError: Error
    open var retryCount: Number
    open var retryTime: Number
    constructor(e: Error, metadata: KafkaJSNumberOfRetriesExceededMetadata = definedExternally)
    constructor(e: String, metadata: KafkaJSNumberOfRetriesExceededMetadata = definedExternally)
}

external open class KafkaJSConnectionError : KafkaJSError {
    open var broker: String
    constructor(e: Error, metadata: KafkaJSConnectionErrorMetadata = definedExternally)
    constructor(e: String, metadata: KafkaJSConnectionErrorMetadata = definedExternally)
}

external open class KafkaJSRequestTimeoutError : KafkaJSError {
    open var broker: String
    open var correlationId: Number
    open var createdAt: Number
    open var sentAt: Number
    open var pendingDuration: Number
    constructor(e: Error, metadata: KafkaJSRequestTimeoutErrorMetadata = definedExternally)
    constructor(e: String, metadata: KafkaJSRequestTimeoutErrorMetadata = definedExternally)
}

external open class KafkaJSMetadataNotLoaded : KafkaJSError

external open class KafkaJSTopicMetadataNotLoaded : KafkaJSMetadataNotLoaded {
    open var topic: String
    constructor(e: Error, metadata: KafkaJSTopicMetadataNotLoadedMetadata = definedExternally)
    constructor(e: String, metadata: KafkaJSTopicMetadataNotLoadedMetadata = definedExternally)
}

external open class KafkaJSStaleTopicMetadataAssignment : KafkaJSError {
    open var topic: String
    open var unknownPartitions: Number
    constructor(e: Error, metadata: KafkaJSStaleTopicMetadataAssignmentMetadata = definedExternally)
    constructor(e: String, metadata: KafkaJSStaleTopicMetadataAssignmentMetadata = definedExternally)
}

external open class KafkaJSServerDoesNotSupportApiKey : KafkaJSNonRetriableError {
    open var apiKey: Number
    open var apiName: String
    constructor(e: Error, metadata: KafkaJSServerDoesNotSupportApiKeyMetadata = definedExternally)
    constructor(e: String, metadata: KafkaJSServerDoesNotSupportApiKeyMetadata = definedExternally)
}

external open class KafkaJSBrokerNotFound : KafkaJSError

external open class KafkaJSPartialMessageError : KafkaJSError

external open class KafkaJSSASLAuthenticationError : KafkaJSError

external open class KafkaJSGroupCoordinatorNotFound : KafkaJSError

external open class KafkaJSNotImplemented : KafkaJSError

external open class KafkaJSTimeout : KafkaJSError

external open class KafkaJSLockTimeout : KafkaJSError

external open class KafkaJSUnsupportedMagicByteInMessageSet : KafkaJSError

external open class KafkaJSDeleteGroupsError : KafkaJSError {
    open var groups: Array<DeleteGroupsResult>
    constructor(e: Error, groups: Array<KafkaJSDeleteGroupsErrorGroups> = definedExternally)
    constructor(e: String, groups: Array<KafkaJSDeleteGroupsErrorGroups> = definedExternally)
}

external open class KafkaJSDeleteTopicRecordsError(metadata: KafkaJSDeleteTopicRecordsErrorTopic) : KafkaJSError

external interface KafkaJSDeleteGroupsErrorGroups {
    var groupId: String
    var errorCode: Number
    var error: KafkaJSError
}

external interface KafkaJSDeleteTopicRecordsErrorTopic {
    var topic: String
    var partitions: Array<KafkaJSDeleteTopicRecordsErrorPartition>
}

external interface KafkaJSDeleteTopicRecordsErrorPartition {
    var partition: Number
    var offset: String
    var error: KafkaJSError
}

external interface KafkaJSErrorMetadata {
    var retriable: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var topic: String?
        get() = definedExternally
        set(value) = definedExternally
    var partitionId: Number?
        get() = definedExternally
        set(value) = definedExternally
    var metadata: PartitionMetadata?
        get() = definedExternally
        set(value) = definedExternally
}

external interface KafkaJSOffsetOutOfRangeMetadata {
    var topic: String
    var partition: Number
}

external interface KafkaJSNumberOfRetriesExceededMetadata {
    var retryCount: Number
    var retryTime: Number
}

external interface KafkaJSConnectionErrorMetadata {
    var broker: String?
        get() = definedExternally
        set(value) = definedExternally
    var code: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface KafkaJSRequestTimeoutErrorMetadata {
    var broker: String
    var clientId: String
    var correlationId: Number
    var createdAt: Number
    var sentAt: Number
    var pendingDuration: Number
}

external interface KafkaJSTopicMetadataNotLoadedMetadata {
    var topic: String
}

external interface KafkaJSStaleTopicMetadataAssignmentMetadata {
    var topic: String
    var unknownPartitions: Array<PartitionMetadata>
}

external interface KafkaJSServerDoesNotSupportApiKeyMetadata {
    var apiKey: Number
    var apiName: String
}