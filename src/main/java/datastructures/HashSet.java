package datastructures;

/**
 * Created by Daniel Riissanen on 14.4.2018.
 */
public class HashSet<E> {

    private static final int MAXIMUM_CAPACITY = 1073741824;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;
    private DynamicArray<DynamicArray<E>> table;
    private float loadLimit;
    private int count;

    public HashSet() {
        this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public HashSet(int capacity) {
        this(capacity, DEFAULT_LOAD_FACTOR);
    }

    public HashSet(int capacity, float loadLimit) {
        table = new DynamicArray<>(Math.max(0, Math.min(capacity, MAXIMUM_CAPACITY)));
        this.loadLimit = Math.max(0, Math.min(1, loadLimit));

        for (int i = 0; i < table.capacity(); ++i)
            table.add(new DynamicArray<>(0));
    }

    public void add(E element) {
        if (contains(element))
            return;

        DynamicArray<E> list = table.get(hash(element, table.capacity()));
        list.add(element);
        ++count;

        if ((float) count / table.capacity() > loadLimit && table.capacity() < MAXIMUM_CAPACITY)
            rehash();
    }

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

    public boolean contains(E element) {
        DynamicArray<E> list = table.get(hash(element, table.capacity()));
        for (int i = 0; i < list.count(); i++) {
            if (list.get(i).equals(element)) {
                return true;
            }
        }
        return false;
    }

    public void setLoadFactorLimit(float limit) {
        loadLimit = Math.max(0, Math.min(1, limit));
    }

    public float getLoadFactorLimit() {
        return loadLimit;
    }

    public int count() {
        return count;
    }

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
