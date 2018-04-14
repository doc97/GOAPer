package datastructures;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Daniel Riissanen on 10.4.2018.
 */
public class DynamicArrayTest {

    @Test
    public void testConstructorEmpty() {
        DynamicArray<MockObject> testSubject = new DynamicArray<>();
        assertEquals(0, testSubject.count());
        assertEquals(10, testSubject.capacity());
    }

    @Test
    public void testConstructorNonEmpty() {
        int capacity = 3;
        DynamicArray<MockObject> testSubject = new DynamicArray<>(capacity);
        assertEquals(0, testSubject.count());
        assertEquals(capacity, testSubject.capacity());
    }

    @Test
    public void testEnsureCapacityZero() {
        DynamicArray<MockObject> testSubject = new DynamicArray<>(0);
        assertEquals(0, testSubject.capacity());
    }

    @Test
    public void testAddOne() {
        MockObject testHelper = new MockObject();
        DynamicArray<MockObject> testSubject = new DynamicArray<>();
        testSubject.add(testHelper);
        assertEquals(1, testSubject.count());
        assertEquals(testHelper, testSubject.get(0));
    }

    @Test
    public void testAddOutOfMemory() {
        int maxCapacity = 2147483639;
        try {
            DynamicArray<MockObject> testSubject = new DynamicArray<>(maxCapacity);
            for (int i = 0; i < maxCapacity; ++i)
                testSubject.add(new MockObject());
            fail("Adding an element to a full Dynamic array does not cause an OutOfMemoryException");
        } catch (OutOfMemoryError ignored) {
        } catch (Exception e) {
            fail("Adding an element to a full Dynamic array does not cause an OutOfMemoryException");
        }
    }

    @Test
    public void testAddAll() {
        List<MockObject> collection = new ArrayList<>();
        collection.add(new MockObject());
        collection.add(new MockObject());
        DynamicArray<MockObject> testSubject = new DynamicArray<>();
        testSubject.addAll(collection);
        assertEquals(collection.size(), testSubject.count());
        for (int i = 0; i < testSubject.count(); ++i)
            assertEquals(collection.get(i), testSubject.get(i));
    }

    @Test
    public void testRemoveOne() {
        DynamicArray<MockObject> testSubject = new DynamicArray<>();
        testSubject.add(new MockObject());
        testSubject.remove(0);
        assertEquals(0, testSubject.count());
    }

    @Test
    public void testRemoveEmpty() {
        DynamicArray<MockObject> testSubject = new DynamicArray<>();
        try {
            testSubject.remove(0);
            fail("Removing from an empty DynamicArray does not cause IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException ignored) {
        } catch (Exception ignored) {
            fail("Removing from an empty DynamicArray does not cause IndexOutOfBoundsException");
        }
    }

    @Test
    public void testRemoveTooMany() {
        DynamicArray<MockObject> testSubject = new DynamicArray<>();
        testSubject.add(new MockObject());
        try {
            testSubject.remove(0);
            testSubject.remove(0);
            fail("Removing from an empty DynamicArray does not cause IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException ignored) {
        } catch (Exception ignored) {
            fail("Removing from an empty DynamicArray does not cause IndexOutOfBoundsException");
        }
    }

    @Test
    public void testRemoveOutOfBounds() {
        DynamicArray<MockObject> testSubject = new DynamicArray<>();
        testSubject.add(new MockObject());
        try {
            testSubject.remove(1);
            fail("Removing with an index that is out of bounds does not cause IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException ignored) {
        } catch (Exception ignored) {
            fail("Removing with an index that is out of bounds does not cause IndexOutOfBoundsException");
        }
    }

    @Test
    public void testRemoveAll() {
        DynamicArray<MockObject> testSubject = new DynamicArray<>();
        testSubject.add(new MockObject());
        testSubject.add(new MockObject());
        testSubject.add(new MockObject());
        testSubject.removeAll();
        assertEquals(0, testSubject.count());
    }

    @Test
    public void testSet() {
        MockObject testHelper = new MockObject();
        DynamicArray<MockObject> testSubject = new DynamicArray<>();
        testSubject.add(new MockObject());
        testSubject.set(0, testHelper);
        assertEquals(testHelper, testSubject.get(0));
    }

    @Test
    public void testSetOutOfBounds() {
        DynamicArray<MockObject> testSubject = new DynamicArray<>();
        try {
            testSubject.set(0, new MockObject());
            fail("Setting with an index that is out of bounds does not cause IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException ignored) {
        } catch (Exception ignored) {
            fail("Setting with an index that is out of bounds does not cause IndexOutOfBoundsException");
        }
    }

    @Test
    public void testIndexOfFirst() {
        MockObject testHelper = new MockObject();
        DynamicArray<MockObject> testSubject = new DynamicArray<>();
        testSubject.add(testHelper);
        testSubject.add(new MockObject());
        assertEquals(0, testSubject.indexOf(testHelper));
    }

    @Test
    public void testIndexOfSecond() {
        MockObject testHelper = new MockObject();
        DynamicArray<MockObject> testSubject = new DynamicArray<>();
        testSubject.add(new MockObject());
        testSubject.add(testHelper);
        assertEquals(1, testSubject.indexOf(testHelper));
    }

    @Test
    public void testIndexOfInvalid() {
        MockObject testHelper = new MockObject();
        DynamicArray<MockObject> testSubject = new DynamicArray<>();
        testSubject.add(new MockObject());
        assertEquals(-1, testSubject.indexOf(testHelper));
    }

    @Test
    public void testContainsTrue() {
        MockObject testHelper = new MockObject();
        DynamicArray<MockObject> testSubject = new DynamicArray<>();
        testSubject.add(testHelper);
        assertTrue(testSubject.contains(testHelper));
    }

    @Test
    public void testContainsFalse() {
        MockObject testHelper = new MockObject();
        DynamicArray<MockObject> testSubject = new DynamicArray<>();
        testSubject.add(new MockObject());
        assertFalse(testSubject.contains(testHelper));
    }

    private class MockObject {
        @SuppressWarnings("unused")
        private int a;
    }
}
