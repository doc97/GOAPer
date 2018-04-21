package datastructures;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by Daniel Riissanen on 21.4.2018.
 */
public class MinHeapTest {

    @Test
    public void testConstructorEmpty() {
        MinHeap<MockElement> testSubject = new MinHeap<>();
        assertEquals(10, testSubject.capacity());
        assertEquals(0, testSubject.count());
    }

    @Test
    public void testConstructorCapacity() {
        MinHeap<MockElement> testSubject = new MinHeap<>(7);
        assertEquals(7, testSubject.capacity());
        assertEquals(0, testSubject.count());
    }

    @Test
    public void testConstructorIllegalCapacity() {
        try {
            new MinHeap<>(-1);
            fail("Failed to throw IllegalArgumentException when given a negative capacity");
        } catch (IllegalArgumentException ignored) { }
    }

    @Test
    public void testAddOne() {
        MinHeap<MockElement> testSubject = new MinHeap<>();
        testSubject.add(new MockElement(0));
        assertEquals(1, testSubject.count());
    }

    @Test
    public void testAddTwo() {
        MinHeap<MockElement> testSubject = new MinHeap<>();
        testSubject.add(new MockElement(0));
        testSubject.add(new MockElement(1));
        assertEquals(2, testSubject.count());
    }

    @Test
    public void testPeekEmpty() {
        MinHeap<MockElement> testSubject = new MinHeap<>();
        try {
            testSubject.peek();
            fail("Failed to throw IllegalStateException when trying to peek an empty heap");
        } catch (IllegalStateException ignored) { }
    }

    @Test
    public void testPeek() {
        MockElement smallest = new MockElement(0);
        MinHeap<MockElement> testSubject = new MinHeap<>();
        testSubject.add(new MockElement(2));
        testSubject.add(smallest);
        testSubject.add(new MockElement(1));
        assertEquals(smallest, testSubject.peek());
    }

    @Test
    public void testPollEmpty() {
        MinHeap<MockElement> testSubject = new MinHeap<>();
        try {
            testSubject.poll();
            fail("Failed to throw IllegalStateException when trying to poll an empty heap");
        } catch (IllegalStateException ignored) { }
    }

    @Test
    public void testPoll() {
        MockElement smallest = new MockElement(0);
        MockElement small = new MockElement(1);
        MockElement medium = new MockElement(2);
        MinHeap<MockElement> testSubject = new MinHeap<>();
        testSubject.add(medium);
        testSubject.add(smallest);
        testSubject.add(small);
        assertEquals(3, testSubject.count());
        assertEquals(smallest, testSubject.poll());
        assertEquals(2, testSubject.count());
        assertEquals(small, testSubject.poll());
        assertEquals(1, testSubject.count());
        assertEquals(medium, testSubject.peek());
    }

    private class MockElement implements IntComparable<MockElement> {

        private int value;

        MockElement(int value) {
            this.value = value;
        }

        @Override
        public int compare(MockElement other) {
            return value - other.value;
        }
    }
}
