package com.rosemite.models;
import java.util.Map;

public class ImportMapEntry<K, V> implements Map.Entry<K, V> {
    // This constructor is not being used but required for gson to create this object to read and write properly.
    public ImportMapEntry() {}
    public ImportMapEntry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K key;
    private V value;

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public V setValue(V value) {
        V old = this.value;
        this.value = value;
        return old;
    }
}