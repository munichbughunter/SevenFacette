package de.p7s1.qa.sevenfacette.kafka;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import de.p7s1.qa.sevenfacette.config.types.KafkaTopicConfig;
import de.p7s1.qa.sevenfacette.kafka.config.SaslConfig;
import de.p7s1.qa.sevenfacette.utils.Logger;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;

import static org.awaitility.Awaitility.*;

public class KConsumer implements Runnable {
    private final Logger logger = new Logger();

    private final org.apache.kafka.clients.consumer.KafkaConsumer<String, String> consumer;
    private final Map<TopicPartition, OffsetAndMetadata> currentOffsets = new HashMap<>();
    private final ConcurrentLinkedQueue<KRecord> kafkaRecordQueue = new ConcurrentLinkedQueue<>();

    private boolean keepGoing = true;
    private Thread consumerThread;
    private final long maxConsumingTime;

    public String topicName;

    public KConsumer(KafkaTopicConfig config) {
        Map<String, Object> kafkaConfig = new HashMap<>();
        kafkaConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, config.bootstrapServer);
        kafkaConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        kafkaConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        kafkaConfig.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, config.autoOffset);

        if (config.autoCommit) {
            kafkaConfig.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
            kafkaConfig.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, config.autoCommitInterval);
        }

        if (config.groupID.isBlank()) {
            kafkaConfig.put(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString());
        } else {
            kafkaConfig.put(ConsumerConfig.GROUP_ID_CONFIG, config.groupID);
        }

        if(!config.readIsolationLevel.isBlank()) {
            kafkaConfig.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, config.readIsolationLevel);
        }

        if (config.useSASLAuthentication) {
            kafkaConfig = SaslConfig.addSaslProperties(kafkaConfig, config);
        }

        consumer = new org.apache.kafka.clients.consumer.KafkaConsumer<>(kafkaConfig);
        consumer.subscribe(Collections.singletonList(config.topicName), new HandleRebalance());

        this.maxConsumingTime = config.maxConsumingTime;
        this.topicName = config.topicName;
    }

    public void consume() {
        keepGoing = true;
        // create a new thread where the consumer will run on (consume() unitl stopped)
        consumerThread = new Thread(this);
        consumerThread.start();
    }

    @Override
    public void run() {
        try {
            // looping until stop() is called on main thread, the shutdown hook will clean up on exit
            while (keepGoing) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(maxConsumingTime));
                logger.info(getCurrentTimeStamp() + " -- consuming data...");
                for (ConsumerRecord<String, String> record : records) {
                    kafkaRecordQueue.add(new KRecord(record.key(), record.value(), record.offset(), record.partition()));
                    currentOffsets.put(new TopicPartition(record.topic(), record.partition()),
                            new OffsetAndMetadata(record.offset() + 1, "no metadata"));
                }
                for (TopicPartition topicPartition : consumer.assignment()) {
                    logger.info("Committing offset at position: " + currentOffsets);
                }

                consumer.commitAsync(currentOffsets, (offsets, exception) -> {
                    if (exception != null) {
                        logger.error("Failed to commit offset: " + offsets + "; " + exception.getMessage());
                    }
                });
            }
        } catch (WakeupException e) {
            // ignore
        } finally {
            try {
                consumer.commitSync(currentOffsets);
            } finally {
                consumer.close();
                logger.info("Closed consumer and we are done");
            }
        }
    }

    public void stop() {
        logger.info("stop() was called. Closing consumer...");
        keepGoing = false;
        try {
            consumerThread.join();
        } catch (InterruptedException ex) {
            logger.error("Joining Thread failed: " + ex.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    public void seekToEnd(){
        Set<TopicPartition> partitions = consumer.assignment();
        consumer.seekToEnd(partitions);
    }

    public List<KRecord> waitForRecords(int timeout, int pollInterval) {
        printTimeOutWarning(timeout);
        with().pollInterval(Duration.ofMillis(pollInterval)).await().atMost(Duration.ofMillis(timeout))
                .until(this::hasRecords);
        return getRecords();
    }

    public List<KRecord> filterByKey(String pattern) {
        return filterRecords(pattern, 5000, true);
    }

    public List<KRecord> filterByKey(String pattern, int timeout) {
        printTimeOutWarning(timeout);
        return filterRecords(pattern, timeout, true);
    }

    public List<KRecord> filterByValue(String pattern) {
        return filterRecords(pattern, 5000, false);
    }

    //also supports regular expressions.
    public List<KRecord> filterByValue(String pattern, int timeout) {
        printTimeOutWarning(timeout);
        return filterRecords(pattern, timeout, false);
    }

    public boolean hasRecords() {
        return !kafkaRecordQueue.isEmpty();
    }

    public List<KRecord> getRecords() {
        return new ArrayList<>(kafkaRecordQueue);
    }

    public int getRecordsCount() {
        return kafkaRecordQueue.size();
    }

    public KRecord getLastRecord() {
        return getRecords().get(getRecordsCount()-1);
    }

    private List<KRecord> filterRecords(String pattern, int timeout, boolean mode) {
        // has to be an array of 1 list, because the filteredList attribute inside the lambda expression has to be final.
        final List<KRecord>[] filteredList = new List[]{new ArrayList<>()};
        with().pollInterval(Duration.ofMillis(500)).await().atMost(Duration.ofMillis(timeout)).until( () -> {
            if(mode){
                filteredList[0] = getRecords().stream().filter(r -> Pattern.compile(pattern).matcher(r.key).find())
                        .collect(Collectors.toList());
            } else {
                filteredList[0] = getRecords().stream().filter(r -> Pattern.compile(pattern).matcher(r.value).find())
                        .collect(Collectors.toList());
            }
            return !filteredList[0].isEmpty();
        });
        return filteredList[0];
    }

    private void printTimeOutWarning(int timeout) {
        if (timeout < 5000) {
            logger.warn("WARNING: Consumer might need more time to consume than the set timeout. " +
                    "You might want to set the timeout to at lease 5000 ms.");
        }
    }

    private String getCurrentTimeStamp() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
    }

    private class HandleRebalance implements ConsumerRebalanceListener {

        public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
            // commit offsets, so we won't calculate avg of data we've already read
            logger.warn("Lost partitions in rebalance. Comitting current offsets: " + currentOffsets);
            consumer.commitSync(currentOffsets);
        }

        public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
            // nothing to do, since we have no state-store from which to recover previous buffers
        }
    }
}
