package datastructures;

/**
 * Created by Daniel Riissanen on 14.4.2018.
 */
public class HashTable<K, V> {

    private static final int MAXIMUM_CAPACITY = 1073741824;
    private static final int DEFAULT_INITIAL_CAPACITY = 64;
    private DynamicArray<DynamicArray<Entry<K, V>>> table;
    private HashSet<K> keys;

    public HashTable() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    public HashTable(int capacity) {
        int initialCapacity = Math.max(0, Math.min(MAXIMUM_CAPACITY, capacity));
        table = new DynamicArray<>(initialCapacity);
        keys = new HashSet<>(initialCapacity);

        for (int i = 0; i < table.capacity(); ++i)
            table.add(new DynamicArray<>(0));
    }

    public void put(K key, V value) {
        DynamicArray<Entry<K, V>> list = table.get(hash(key, table.capacity()));

        if (containsKey(key)) {
            for (int i = 0; i < list.count(); ++i) {
                Entry<K, V> entry = list.get(i);
                if (entry.key.equals(key))
                    entry.value = value;
            }
        } else {
            Entry<K, V> e = new Entry<>(key, value);
            list.add(e);
            keys.add(key);
        }
    }

    public void remove(K key) {
        if (containsKey(key)) {
            int index = -1;
            DynamicArray<Entry<K, V>> list = table.get(hash(key, table.capacity()));
            for (int i = 0; i < list.count(); ++i) {
                Entry<K, V> entry = list.get(i);
                if (entry.key.equals(key)) {
                    index = i;
                    break;
                }
            }

            list.remove(index);
            keys.remove(key);
        }
    }

    public V get(K key) {
        DynamicArray<Entry<K, V>> list = table.get(hash(key, table.capacity()));
        for (int i = 0; i < list.count(); ++i) {
            Entry<K, V> entry = list.get(i);
            if (entry.key.equals(key))
                return entry.value;
        }
        return null;
    }

    public boolean containsKey(K key) {
        return keys.contains(key);
    }

    public int count() {
        return keys.count();
    }

    public int capacity() {
        return table.capacity();
    }

    public HashSet<K> keys() {
        return keys;
    }

    public DynamicArray<V> values() {
        DynamicArray<V> valueArray = new DynamicArray<>(keys.count());
        for (int i = 0; i < table.count(); ++i) {
            DynamicArray<Entry<K, V>> list = table.get(i);
            for (int j = 0; j < table.get(i).count(); ++j) {
                Entry<K, V> entry = list.get(j);
                valueArray.add(entry.value);
            }
        }
        return valueArray;
    }

    private int hash(K key, int size) {
        return key.hashCode() % size;
    }

    private final class Entry<S, T> {
        private S key;
        private T value;

        private Entry(S key, T value) {
            this.key = key;
            this.value = value;
        }
    }
}
