package datastructures;

import java.util.Collection;

/**
 * A custom implementation of ArrayList.
 * <p/>
 * Created by Daniel Riissanen on 10.4.2018.
 * @see java.util.ArrayList
 */
public class DynamicArray<E> {

    /** The max capacity of the element array, taken directly from ArrayList */
    private static final int MAX_CAPACITY = 2147483639;

    /** The default capacity of the element array */
    private static final int DEFAULT_CAPACITY = 10;

    /** To avoid duplicate objects an empty static array is used */
    private static final Object[] EMPTY_ELEMENTS = new Object[0];

    /** The array containing the elements */
    private Object[] elements;

    /** The element count */
    private int count;

    /** The current element array capacity */
    private int capacity;

    /**
     * Class constructor using the default capacity.
     */
    public DynamicArray() {
        capacity = DEFAULT_CAPACITY;
        elements = new Object[DEFAULT_CAPACITY];
    }

    /**
     * Class constructor specifying a capacity to use.
     * @param capacity The capacity
     * @throws IllegalArgumentException If capacity is negative
     */
    public DynamicArray(int capacity) {
        if (capacity < 0)
            throw new IllegalArgumentException("Illegal capacity: " + capacity);

        if (capacity > 0)
            elements = new Object[capacity];
        else
            elements = EMPTY_ELEMENTS;

        this.capacity = capacity;
    }

    /**
     * Adds an element to the array. Will resize automatically to ensure enough capacity.
     * @param element The element to add
     * @throws OutOfMemoryError If the array is full and it has reached the max capacity
     */
    public void add(E element) {
        ensureCapacity();
        elements[count++] = element;
    }

    /**
     * Provides support for using Java's own collections. Will resize automatically to ensure
     * enough capacity.
     * @param collection The collection containing elements
     * @throws OutOfMemoryError If the array is full and it has reached the max capacity
     */
    public void addAll(Collection<? extends E> collection) {
        Object[] addArray = collection.toArray();
        ensureCapacity(count + addArray.length);
        System.arraycopy(addArray, 0, elements, count, addArray.length);
        this.count += addArray.length;
    }

    /**
     * Removes an element at a specified index.
     * @param index The index of the element
     * @throws IndexOutOfBoundsException If the index is out of bounds
     */
    public void remove(int index) {
        rangeCheck(index);
        System.arraycopy(elements, index + 1, elements, index, count - index - 1);
        elements[--count] = null;
    }

    /**
     * Clears the array.
     */
    public void removeAll() {
        for (int i = 0; i < count; ++i)
            elements[i] = null;
        count = 0;
    }

    /**
     * Sets the element at a specified index.
     * @param index The index
     * @param element The element to set
     * @throws IndexOutOfBoundsException If the index is out of bounds
     */
    public void set(int index, E element) {
        rangeCheck(index);
        elements[index] = element;
    }

    /**
     * Returns the element at a specified index.
     * @param index The index
     * @return The element at the index
     * @throws IndexOutOfBoundsException If the index is out of bounds
     */
    public E get(int index) {
        rangeCheck(index);
        return element(index);
    }

    /**
     * Returns the index of an object
     * @param object The object to look for
     * @return The index if the array contains the object, -1 otherwise.
     */
    public int indexOf(E object) {
        for (int i = 0; i < count; ++i) {
            if (elements[i].equals(object))
                return i;
        }
        return -1;
    }

    /**
     * Returns whether the array contains the object. Short-hand for <code>indexOf(object) >= 0</code>.
     * @param object The object
     * @return True if it exists, false otherwise
     */
    public boolean contains(E object) {
        return indexOf(object) >= 0;
    }

    /**
     * Returns the element count in the array.
     * @return The element count
     */
    public int count() {
        return count;
    }

    /**
     * Returns the capacity of the array.
     * @return The capacity
     */
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
            for (int i = 0; i < count; ++i)
                newElements[i] = elements[i];
            elements = newElements;
        }
    }

    private void rangeCheck(int index) {
        if (index < 0 || index >= count)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + count);
    }
}
