package datastructures;

/**
 * Custom implementation of {@link java.util.PriorityQueue}
 * <p/>
 * Created by Daniel Riissanen on 21.4.2018.
 */
public class MinHeap<E extends IntComparable<E>> {

    /** The max capacity of the element array, taken directly from PriorityQueue */
    private static final int MAX_CAPACITY = 2147483639;

    /** The default capacity of the element array */
    private static final int DEFAULT_CAPACITY = 11;

    /** The current element array capacity */
    private int capacity;

    /** The element count */
    private int count;

    /** The array containing the elements */
    private Object[] elements;

    /** Class constructor using the default capacity. */
    public MinHeap() {
        capacity = DEFAULT_CAPACITY;
        elements = new Object[DEFAULT_CAPACITY];
    }

    /**
     * Class constructor specifying the initial capacity.
     * @param capacity The initial capacity
     */
    public MinHeap(int capacity) {
        if (capacity < 0)
            throw new IllegalArgumentException("capacity must not be negative");
        this.capacity = Math.min(capacity, MAX_CAPACITY);
        elements = new Object[this.capacity];
    }

    /**
     * Adds an element.
     * @param element The element to add
     */
    public void add(E element) {
        ensureCapacity();
        elements[count++] = element;
        heapifyUp();
    }

    /**
     * Adds a list of elements
     * @param elements The list of elements
     */
    public void addAll(DynamicArray<E> elements) {
        for (int i = 0; i < elements.count(); i++)
            add(elements.get(i));
    }

    /**
     * Adds an array of elements
     * @param elements The array of elements
     */
    public void addAll(E[] elements) {
        for (int i = 0; i < elements.length; i++)
            add(elements[i]);
    }

    /**
     * Retrieves the first element without removing it.
     * @return The first element
     * @throws IllegalStateException If the heap is empty
     */
    public E peek() {
        if (count == 0)
            throw new IllegalStateException();

        return element(0);
    }

    /**
     * Retrieves the first element and removes it.
     * @return The first element
     * @throws IllegalStateException If the heap is empty
     */
    public E poll() {
        if (count == 0)
            throw new IllegalStateException();

        E element = element(0);
        elements[0] = elements[count - 1];
        --count;
        heapifyDown();
        return element;
    }

    /**
     * Checks if an element is present.
     * @param e The element to check
     * @return {@code true} if the specified element is present
     */
    public boolean contains(E e) {
        if (e == null)
            return false;
        for (int i = 0; i < count; i++) {
            if (e.equals(elements[i]))
                return true;
        }
        return false;
    }

    /**
     * Returns the capacity of the heap.
     * @return The capacity
     */
    public int capacity() {
        return capacity;
    }

    /**
     * Returns the element count in the heap.
     * @return The element count
     */
    public int count() {
        return count;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    private void heapifyUp() {
        int index = count - 1;
        while (hasParent(index) && getParent(index).compare(element(index)) > 0) {
            swap(getParentIndex(index), index);
            index = getParentIndex(index);
        }
    }

    private void heapifyDown() {
        int index = 0;
        while (hasLeftChild(index)) {
            int smallerChildIndex = getLeftChildIndex(index);

            if (hasRightChild(index) && getRightChild(index).compare(getLeftChild(index)) < 0)
                smallerChildIndex = getRightChildIndex(index);

            if (element(index).compare(element(smallerChildIndex)) < 0)
                break;

            swap(index, smallerChildIndex);
            index = smallerChildIndex;
        }
    }

    private int getLeftChildIndex(int index) {
        return 2 * index + 1;
    }

    private int getRightChildIndex(int index) {
        return 2 * index + 2;
    }

    private int getParentIndex(int index) {
        return (int) Math.ceil((double) (index - 1) / 2);
    }

    private boolean hasLeftChild(int index) {
        return getLeftChildIndex(index) < count;
    }

    private boolean hasRightChild(int index) {
        return getRightChildIndex(index) < count;
    }

    private boolean hasParent(int index) {
        return getParentIndex(index) >= 0;
    }

    private E getLeftChild(int index) {
        return element(getLeftChildIndex(index));
    }

    private E getRightChild(int index) {
        return element(getRightChildIndex(index));
    }

    private E getParent(int index) {
        return element(getParentIndex(index));
    }

    @SuppressWarnings("unchecked")
    private E element(int index) {
        rangeCheck(index);
        return (E) elements[index];
    }

    private void rangeCheck(int index) {
        if (index < 0 || index >= count)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + count);
    }

    private void swap(int indexOne, int indexTwo) {
        rangeCheck(indexOne);
        rangeCheck(indexTwo);
        Object tmp = elements[indexOne];
        elements[indexOne] = elements[indexTwo];
        elements[indexTwo] = tmp;
    }

    private void ensureCapacity() {
        if (count == capacity) {
            if (capacity == MAX_CAPACITY)
                throw new OutOfMemoryError();

            capacity = Math.min(capacity * 2, MAX_CAPACITY);
            if (capacity == 0) {
                capacity = 1;
                elements = new Object[capacity];
            } else {
                Object[] newElements = new Object[capacity];
                for (int i = 0; i < count; ++i)
                    newElements[i] = elements[i];
                elements = newElements;
            }
        }
    }
}
