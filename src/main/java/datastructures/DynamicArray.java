package datastructures;

import java.util.Collection;

/**
 * Created by Daniel Riissanen on 10.4.2018.
 */
public class DynamicArray<E> {

    private static final int MAX_CAPACITY = 2147483639;
    private static final int DEFAULT_CAPACITY = 10;
    private static final Object[] EMPTY_ELEMENTS = new Object[0];

    private Object[] elements;
    private int count;
    private int capacity;

    public DynamicArray() {
        capacity = DEFAULT_CAPACITY;
        elements = new Object[DEFAULT_CAPACITY];
    }

    public DynamicArray(int capacity) {
        if (capacity < 0)
            throw new IllegalArgumentException("Illegal capacity: " + capacity);

        if (capacity > 0)
            elements = new Object[capacity];
        else
            elements = EMPTY_ELEMENTS;

        this.capacity = capacity;
    }

    public void add(E element) {
        ensureCapacity();
        elements[count++] = element;
    }

    public void addAll(Collection<? extends E> collection) {
        Object[] addArray = collection.toArray();
        ensureCapacity(count + addArray.length);
        System.arraycopy(addArray, 0, elements, count, addArray.length);
        this.count += addArray.length;
    }

    public void remove(int index) {
        rangeCheck(index);
        System.arraycopy(elements, index + 1, elements, index, count - index - 1);
        elements[--count] = null;
    }

    public void removeAll() {
        for (int i = 0; i < count; ++i)
            elements[i] = null;
        count = 0;
    }

    public void set(int index, E element) {
        rangeCheck(index);
        elements[index] = element;
    }

    public E get(int index) {
        rangeCheck(index);
        return element(index);
    }

    public int indexOf(E object) {
        for (int i = 0; i < count; ++i) {
            if (elements[i].equals(object))
                return i;
        }
        return -1;
    }

    public boolean contains(E object) {
        return indexOf(object) >= 0;
    }

    public int count() {
        return count;
    }

    public int capacity() {
        return capacity;
    }

    @SuppressWarnings("unchecked")
    private E element(int index) {
        return (E) elements[index];
    }

    private void ensureCapacity() {
        ensureCapacity(count + 1);
    }

    private void ensureCapacity(int size) {
        if (size > capacity) {
            if (capacity == MAX_CAPACITY)
                throw new OutOfMemoryError();
            else if (capacity < 2) {
                ++capacity;
            } else {
                int newCapacity = capacity + (capacity >> 1);
                capacity = newCapacity < 0 ? MAX_CAPACITY : newCapacity;
            }

            if (size > capacity)
                capacity = size;

            Object[] newElements = new Object[capacity];
            System.arraycopy(elements, 0, newElements, 0, elements.length);
            elements = newElements;
        }
    }

    private void rangeCheck(int index) {
        if (index < 0 || index >= count)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + count);
    }
}
