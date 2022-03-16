package de.p7s1.qa.sevenfacette.kafka;

import org.jetbrains.annotations.Nullable;

/**
 * TODO: Add Description
 *
 * @author Patrick Doering
 */
public class KRecord {
    public String key;
    public String value;
    public long offset;
    public int partition;

    public KRecord(@Nullable String key, @Nullable String value, long offset, int partition) {
        this.key = key;
        this.value = value;
        this.offset = offset;
        this.partition = partition;
    }

    @Override
    public String toString() {
        return "key: " + this.key + ", value: " + this.value + ", offset: " + this.offset + ", partition: " + this.partition;
    }
}
