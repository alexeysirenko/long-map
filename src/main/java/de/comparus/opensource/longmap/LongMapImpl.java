package de.comparus.opensource.longmap;

import java.util.*;

/**
 * LongMap implementation. Mutable.
 */
public class LongMapImpl<V> implements LongMap<V> {

    /**
     * Default size of a hash table.
     */
    private int HASH_TABLE_SIZE = 4096;

    /**
     * Hash table. Contains list of items with the same hashes (collisions).
     */
    private List<List<Pair<V>>> hashTable;

    /**
     * Creates a map instance.
     */
    public LongMapImpl() {
        init(HASH_TABLE_SIZE);
    }

    /**
     * Creates a map with specified size of a hash table.
     *
     * @param size size of a hash table
     */
    public LongMapImpl(int size) {
        init(size);
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key, the old value is replaced.
     *
     * @param key key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return the previous value associated with key, or null if there was no mapping for key
     */
    public V put(long key, V value) {
        int hash = hash(key);
        List<Pair<V>> pairs = hashTable.get(hash);
        if (pairs == null) {
            pairs = new LinkedList<>();
            hashTable.set(hash, pairs);
        }
        V previousValue = null;
        for (Pair<V> pair : pairs) {
            if (pair.key == key) {
                previousValue = pair.value;
                pair.value = value;
            }
        }
        if (previousValue == null) {
            pairs.add(new Pair<>(key, value));
        }
        return previousValue;
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this map
     * contains no mapping for the key.
     *
     * @param key the key whose associated value is to be returned
     * @return the value to which the specified key is mapped, or null if this map contains no mapping for the key
     */
    public V get(long key) {
        V result = null;
        int hash = hash(key);
        List<Pair<V>> pairs = hashTable.get(hash);
        if (pairs != null) {
            for (Pair<V> pair : pairs) {
                if (pair.key == key) {
                    result = pair.value;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Removes the mapping for the specified key from this map if present.
     *
     * @param key key whose mapping is to be removed from the map
     * @return the previous value associated with key, or null if there was no mapping for key
     */
    public V remove(long key) {
        V result = null;
        int hash = hash(key);
        List<Pair<V>> pairs = hashTable.get(hash);
        if (pairs != null) {
            Iterator<Pair<V>> iterator = pairs.iterator();
            while (iterator.hasNext()) {
                Pair<V> pair = iterator.next();
                if (pair.key == key) {
                    result = pair.value;
                    iterator.remove();
                    break;
                }
            }
            if (pairs.isEmpty()) {
                hashTable.set(hash, null);
            }
        }
        return result;
    }

    /**
     * Returns true if this map contains no key-value mappings.
     *
     * @return true if this map contains no key-value mappings
     */
    public boolean isEmpty() {
        boolean isEmpty = true;
        for (List<Pair<V>> pairs : hashTable) {
            if (pairs != null && !pairs.isEmpty()) {
                isEmpty = false;
                break;
            }
        }
        return isEmpty;
    }

    /**
     * Returns true if this map contains a mapping for the specified key.
     *
     * @param key key whose presence in this map is to be tested
     * @return true if this map contains a mapping for the specified key
     */
    public boolean containsKey(long key) {
        boolean containsKey = false;
        int hash = hash(key);
        List<Pair<V>> pairs = hashTable.get(hash);
        if (pairs != null) {
            for (Pair<V> pair : pairs) {
                if (pair.key == key) {
                    containsKey = true;
                    break;
                }
            }
        }
        return containsKey;
    }

    /**
     * Returns true if this map maps one or more keys to the specified value.
     *
     * @param value value whose presence in this map is to be tested
     * @return true if this map maps one or more keys to the specified value
     */
    public boolean containsValue(V value) {
        boolean containsValue = false;
        for (List<Pair<V>> pairs : hashTable) {
            if (pairs != null) {
                for (Pair<V> pair : pairs) {
                    if (pair.value.equals(value)) {
                        containsValue = true;
                        break;
                    }
                }
            }
        }
        return containsValue;
    }

    /**
     * Returns all the keys contained in this map.
     *
     * @return array of keys
     */
    public long[] keys() {
        List<Long> keys = new ArrayList<>();
        for (List<Pair<V>> pairs : hashTable) {
            if (pairs != null) {
                for (Pair<V> pair : pairs) {
                    keys.add(pair.key);
                }
            }
        }
        long[] result = new long[keys.size()];
        for (int i = 0; i < keys.size(); i++) {
            result[i] = keys.get(i);
        }
        return result;
    }

    /**
     * Returns all the values contained in this map.
     *
     * @return array of values
     */
    public V[] values() {
        List<V> values = new ArrayList<>();
        for (List<Pair<V>> pairs : hashTable) {
            if (pairs != null) {
                for (Pair<V> pair : pairs) {
                    values.add(pair.value);
                }
            }
        }

        // Looks like that there is no better way to instantiate a generic array
        @SuppressWarnings("unchecked")
        V[] result = (V[]) new Object[values.size()];
        for (int i = 0; i < values.size(); i++) {
            result[i] = values.get(i);
        }
        return result;
    }

    /**
     * Returns the number of key-value mappings in this map.
     *
     * @return the number of key-value mappings in this map
     */
    public long size() {
        long size = 0;
        for (List<Pair<V>> pairs : hashTable) {
            if (pairs != null) {
                size = size + pairs.size();
            }
        }
        return size;
    }

    /**
     * Removes all of the mappings from this map.
     */
    public void clear() {
        init(hashTable != null ? hashTable.size() : HASH_TABLE_SIZE);
    }

    public String toString() {
        return "[" + hashTable.toString() + "]";
    }

    /**
     * Returns calculated hash of a key.
     *
     * @param key a key to be hashed
     * @return hash value
     */
    private int hash(long key) {
        return (int) (key % HASH_TABLE_SIZE);
    }

    /**
     * Initializes a hash table
     */
    private void init(int size) {
        hashTable = new ArrayList<>(Collections.nCopies(size, null));
    }

    /**
     * Class to hold key-value pairs.
     */
    private class Pair<V> {

        public final long key;

        public V value;

        public Pair(long key, V value) {
            this.key = key;
            this.value = value;
        }
    }

}
