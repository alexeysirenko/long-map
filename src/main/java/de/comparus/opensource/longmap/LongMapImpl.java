package de.comparus.opensource.longmap;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * LongMap implementation. Mutable.
 */
public class LongMapImpl<V> implements LongMap<V> {

    private int HASH_TABLE_SIZE = 4096;

    private List<List<Cons<V>>> hashTable;

    public LongMapImpl() {
        init();
    }

    public V put(long key, V value) {
        int hash = hash(key);
        List<Cons<V>> items = hashTable.get(hash);
        if (items == null) {
            items = new LinkedList<>();
            hashTable.set(hash, items);
        }
        boolean exists = false;
        for (Cons<V> item: items) {
            if (item.key == key) {
                exists = true;
                item.value = value;
            }
        }
        if (!exists) {
            items.add(new Cons<>(key, value));
        }
        return value;
    }

    public V get(long key) {
        V result = null;
        int hash = hash(key);
        List<Cons<V>> items = hashTable.get(hash);
        if (items != null) {
            for (Cons<V> item: items) {
                if (item.key == key) {
                    result = item.value;
                    break;
                }
            }
        }
        return result;
    }

    public V remove(long key) {
        V result = null;
        int hash = hash(key);
        List<Cons<V>> items = hashTable.get(hash);
        if (items != null) {
            Iterator<Cons<V>> iterator = items.iterator();
            while (iterator.hasNext()) {
                Cons<V> item = iterator.next();
                if (item.key == key) {
                    result = item.value;
                    iterator.remove();
                    break;
                }
            }
            if (items.isEmpty()) {
                hashTable.set(hash, null);
            }
        }
        return result;
    }

    public boolean isEmpty() {
        boolean isEmpty = true;
        for (List<Cons<V>> items: hashTable) {
            if (items != null && !items.isEmpty()) {
                isEmpty = false;
                break;
            }
        }
        return isEmpty;
    }

    public boolean containsKey(long key) {
        boolean containsKey = false;
        int hash = hash(key);
        List<Cons<V>> items = hashTable.get(hash);
        if (items != null) {
            for (Cons<V> item: items) {
                if (item.key == key) {
                    containsKey = true;
                    break;
                }
            }
        }
        return containsKey;
    }

    public boolean containsValue(V value) {
        boolean containsValue = false;
        for (List<Cons<V>> items: hashTable) {
            if (items != null) {
                for (Cons<V> item: items) {
                    if (item.value.equals(value)) {
                        containsValue = true;
                        break;
                    }
                }
            }
        }
        return containsValue;
    }

    public long[] keys() {
        List<Long> keys = new ArrayList<>();
        for (List<Cons<V>> items: hashTable) {
            if (items != null) {
                for (Cons<V> item: items) {
                    keys.add(item.key);
                }
            }
        }
        long[] result = new long[keys.size()];
        for (int i = 0; i < keys.size(); i++) {
            result[i] = keys.get(i);
        }
        return result;
    }

    public V[] values() {
        List<V> values = new ArrayList<>();
        for (List<Cons<V>> items: hashTable) {
            if (items != null) {
                for (Cons<V> item: items) {
                    values.add(item.value);
                }
            }
        }

        @SuppressWarnings("unchecked")
        V[] result = (V[]) new Object[values.size()];
        for (int i = 0; i < values.size(); i++) {
            result[i] = values.get(i);
        }
        return result;
    }

    public long size() {
        long size = 0;
        for (List<Cons<V>> items: hashTable) {
            if (items != null) {
                size = size + items.size();
            }
        }
        return size;
    }

    public void clear() {
        init();
    }

    private int hash(long key) {
        return (int) (key % HASH_TABLE_SIZE);
    }

    private void init() {
        hashTable = new ArrayList<>(HASH_TABLE_SIZE);
    }

    private class Cons<V> {

        public final long key;

        public V value;

        public Cons(long key, V value) {
            this.key = key;
            this.value = value;
        }
    }

}
