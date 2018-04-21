package datastructures;

/**
 * A custom implementation of {@link java.util.HashSet}.
 * <p/>
 * Created by Daniel Riissanen on 14.4.2018.
 */
public class HashSet<E> {

    /** The max capacity of the set, taken directly from HashSet */
    private static final int MAXIMUM_CAPACITY = 1073741824;

    /** The default capacity of the element array */
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    /** The default load factor limit */
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    /** A list of overflow lists */
    private DynamicArray<DynamicArray<E>> table;

    /** The load factor limit */
    private float loadLimit;

    /** The element count in the set */
    private int count;

    /**
     * Class constructor using the default capacity and load factor.
     */
    public HashSet() {
        this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    /**
     * Class constructor specifying a capacity.
     * @param capacity The initial capacity
     */
    public HashSet(int capacity) {
        this(capacity, DEFAULT_LOAD_FACTOR);
    }

    /**
     * Class constructor specifying a capacity and a load factor limit.
     * @param capacity The initial capacity
     * @param loadLimit The load factor limit
     */
    public HashSet(int capacity, float loadLimit) {
        table = new DynamicArray<>(Math.max(0, Math.min(capacity, MAXIMUM_CAPACITY)));
        this.loadLimit = Math.max(0, Math.min(1, loadLimit));

        for (int i = 0; i < table.capacity(); ++i)
            table.add(new DynamicArray<>(0));
    }

    /**
     * Adds an element into the set, if it doesn't already exist. Will resize automatically to ensure capacity.
     * If the set has reached it's max capacity it will not rehash the elements.
     * @param element The element to add
     */
    public void add(E element) {
        if (contains(element))
            return;

        DynamicArray<E> list = table.get(hash(element, table.capacity()));
        list.add(element);
        ++count;

        if ((float) count / table.capacity() > loadLimit && table.capacity() < MAXIMUM_CAPACITY)
            rehash();
    }

    /**
     * Removes an element from the set. Does nothing if the element does not exist.
     * @param element The element to remove
     */
    public void remove(E element) {
        DynamicArray<E> list = table.get(hash(element, table.capacity()));
        int index = -1;
        for (int i = 0; i < list.count(); i++) {
            if (list.get(i).equals(element)) {
                index = i;
                --count;
                break;
            }
        }
        if (index != -1)
            list.remove(index);
    }

    /**
     * Checks whether an element exists in the set.
     * @param element The element to check
     * @return <code>true</code> if it exists, <code>false</code> otherwise
     */
    public boolean contains(E element) {
        DynamicArray<E> list = table.get(hash(element, table.capacity()));
        for (int i = 0; i < list.count(); i++) {
            if (list.get(i).equals(element)) {
                return true;
            }
        }
        return false;
    }

    /**
     * The load factor limit will be clamped to [0, 1].
     * @param limit The new limit
     */
    public void setLoadFactorLimit(float limit) {
        loadLimit = Math.max(0, Math.min(1, limit));
    }

    public float getLoadFactorLimit() {
        return loadLimit;
    }

    /**
     * The element count in the set.
     * @return The element count
     */
    public int count() {
        return count;
    }

    /**
     * The capacity of the set, in other words the amount of overflow lists.
     * @return The capacity
     */
    public int capacity() {
        return table.capacity();
    }

    private int hash(E element, int size) {
        return element.hashCode() % size;
    }

    private void rehash() {
        int newCapacity = Math.min(table.capacity() * 2, MAXIMUM_CAPACITY);
        DynamicArray<DynamicArray<E>> newTable = new DynamicArray<>(newCapacity);

        for (int i = 0; i < newTable.capacity(); ++i)
            newTable.add(new DynamicArray<>(0));

        for (int i = 0; i < table.count(); ++i) {
            DynamicArray<E> collisionList = table.get(i);
            for (int j = 0; j < collisionList.count(); ++j) {
                E e = collisionList.get(j);
                DynamicArray<E> overflowList = newTable.get(hash(e, newTable.capacity()));
                overflowList.add(e);
            }
        }

        table = newTable;
    }
}
