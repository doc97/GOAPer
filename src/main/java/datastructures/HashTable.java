package datastructures;

/**
 * A custom implementation of {@link java.util.HashMap}.
 * <p/>
 * Created by Daniel Riissanen on 14.4.2018.
 */
public class HashTable<K, V> {

    /** The max capacity of the set, taken directly from HashSet */
    private static final int MAXIMUM_CAPACITY = 1073741824;

    /** The default capacity of the element array */
    private static final int DEFAULT_INITIAL_CAPACITY = 64;

    /** A list of overflow lists */
    private DynamicArray<DynamicArray<Entry<K, V>>> table;

    /** A set of the keys */
    private HashSet<K> keys;

    /**
     * Class constructor using the default capacity.
     */
    public HashTable() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    /**
     * Class constructor specifying a capacity. The capacity will be clamped between [0, MAX_CAPACITY]
     * @param capacity The capacity
     */
    public HashTable(int capacity) {
        int initialCapacity = Math.max(0, Math.min(MAXIMUM_CAPACITY, capacity));
        table = new DynamicArray<>(initialCapacity);
        keys = new HashSet<>(initialCapacity);

        for (int i = 0; i < table.capacity(); ++i)
            table.add(new DynamicArray<>(0));
    }

    /**
     * Adds a key-value pair to the table. If the key already exists, the value will be overwritten.
     * @param key The key
     * @param value The value
     */
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

    /**
     * Removes a key-value pair.
     * @param key The key
     */
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

    /**
     * Returns the value mapped to the key.
     * @param key The key
     * @return The value mapped to the key or <code>null</code> if the key does not exist in the table
     */
    public V get(K key) {
        DynamicArray<Entry<K, V>> list = table.get(hash(key, table.capacity()));
        for (int i = 0; i < list.count(); ++i) {
            Entry<K, V> entry = list.get(i);
            if (entry.key.equals(key))
                return entry.value;
        }
        return null;
    }

    /**
     * Checks whether a key exists in the table.
     * @param key The key
     * @return <code>true</code> if it exists, <code>false</code> otherwise
     */
    public boolean containsKey(K key) {
        return keys.contains(key);
    }

    /**
     * The element count in the table.
     * @return The count
     */
    public int count() {
        return keys.count();
    }

    /**
     * The capacity of the table.
     * @return The capacity
     */
    public int capacity() {
        return table.capacity();
    }

    /**
     * Returns a set containing the keys.
     * @return The keys
     */
    public HashSet<K> keys() {
        return keys;
    }

    /**
     * Returns a list containing the values.
     * @return The values
     */
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
