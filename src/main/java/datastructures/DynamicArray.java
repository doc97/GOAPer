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
     * Copy constructor.
     * @param other The other list
     */
    public DynamicArray(DynamicArray<? extends E> other) {
        capacity = other.capacity;
        count = other.count;
        this.elements = new Object[other.elements.length];
        for (int i = 0; i < other.elements.length; i++)
            this.elements[i] = other.elements[i];
    }

    /**
     * Constructor initialized with an array of elements.
     * @param elements The array of elements
     */
    public DynamicArray(E[] elements) {
        count = elements.length;
        capacity = elements.length;
        this.elements = new Object[capacity];
        for (int i = 0; i < elements.length; i++)
            this.elements[i] = elements[i];
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
        for (int i = 0; i < addArray.length; i++)
            elements[count++] = addArray[i];
    }

    /**
     * Add a list of elements all at once.
     * @param list The list of elements to add
     */
    public void addAll(DynamicArray<? extends E> list) {
        ensureCapacity(count + list.count);
        for (int i = 0; i < list.count; i++)
            elements[count++] = list.get(i);
    }

    /**
     * Removes an element at a specified index.
     * @param index The index of the element
     * @throws IndexOutOfBoundsException If the index is out of bounds
     */
    public void remove(int index) {
        rangeCheck(index);
        for (int i = index + 1; i < count; i++)
            elements[i - 1] = elements[i];
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
     * Converts the list to an array.
     * @param array The array to store it in
     * @param <T> The generic type (must be able to cast from E)
     * @return The array
     */
    @SuppressWarnings("unchecked")
    public <T> T[] asArray(T[] array) {
        for (int i = 0; i < count; i++) {
            if (i == array.length)
                return array;
            array[i] = (T) elements[i];
        }
        return array;
    }

    /**
     * Returns the index of an object.
     * @param object The object to look for
     * @return The index if the array contains the object, -1 otherwise.
     */
    public int indexOf(E object) {
        for (int i = 0; i < count; ++i) {
            if (elements[i] == null && object == null || elements[i] != null && elements[i].equals(object))
                return i;
        }
        return -1;
    }

    /**
     * Will reverse the array.
     */
    public void reverse() {
        for (int i = 0; i < count / 2; ++i) {
            Object tmp = elements[i];
            elements[i] = elements[count - 1 - i];
            elements[count - 1 - i] = tmp;
        }
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

    /**
     * Checks if the list is empty.
     * @return <code>true</code> if the list is empty
     */
    public boolean isEmpty() {
        return count == 0;
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
