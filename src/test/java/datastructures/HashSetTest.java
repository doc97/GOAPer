package datastructures;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Daniel Riissanen on 14.4.2018.
 */
public class HashSetTest {

    @Test
    public void testConstructorEmpty() {
        HashSet<MockValue> testSubject = new HashSet<>();
        assertEquals(testSubject.count(), 0);
        assertEquals(testSubject.capacity(), 16);
        assertEquals(testSubject.getLoadFactorLimit(), 0.75f, 0.000001f);
    }

    @Test
    public void testConstructorCapacityOverflow() {
        int limit = 1073741824;
        try {
            HashSet<MockValue> testSubject = new HashSet<>(limit + 1);
            assertEquals(testSubject.count(), 0);
            assertEquals(testSubject.capacity(), limit);
            assertEquals(testSubject.getLoadFactorLimit(), 0.75f, 0.000001f);
        } catch (OutOfMemoryError ignored) {}
    }

    @Test
    public void testConstructorCapacityUnderflow() {
        HashSet<MockValue> testSubject = new HashSet<>(-1);
        assertEquals(testSubject.count(), 0);
        assertEquals(testSubject.capacity(), 0);
        assertEquals(testSubject.getLoadFactorLimit(), 0.75f, 0.000001f);
    }

    @Test
    public void testConstructorCapacityNormal() {
        HashSet<MockValue> testSubject = new HashSet<>(10);
        assertEquals(testSubject.count(), 0);
        assertEquals(testSubject.capacity(), 10);
        assertEquals(testSubject.getLoadFactorLimit(), 0.75f, 0.000001f);
    }

    @Test
    public void testConstructorCapacityAndLoadOver() {
        HashSet<MockValue> testSubject = new HashSet<>(10, 2);
        assertEquals(testSubject.count(), 0);
        assertEquals(testSubject.capacity(), 10);
        assertEquals(testSubject.getLoadFactorLimit(), 1, 0.000001f);
    }

    @Test
    public void testConstructorCapacityAndLoadUnder() {
        HashSet<MockValue> testSubject = new HashSet<>(10, -1);
        assertEquals(testSubject.count(), 0);
        assertEquals(testSubject.capacity(), 10);
        assertEquals(testSubject.getLoadFactorLimit(), 0, 0.000001f);
    }

    @Test
    public void testConstructorCapacityAndLoadNormal() {
        HashSet<MockValue> testSubject = new HashSet<>(10, 0.5f);
        assertEquals(testSubject.count(), 0);
        assertEquals(testSubject.capacity(), 10);
        assertEquals(testSubject.getLoadFactorLimit(), 0.5f, 0.000001f);
    }

    @Test
    public void testAdd() {
        MockValue value = new MockValue();
        HashSet<MockValue> testSubject = new HashSet<>();
        testSubject.add(value);
        assertEquals(testSubject.count(), 1);
        assertTrue(testSubject.contains(value));
    }

    @Test
    public void testRemove() {
        MockValue value = new MockValue();
        HashSet<MockValue> testSubject = new HashSet<>();
        testSubject.add(value);
        testSubject.remove(value);
        assertEquals(testSubject.count(), 0);
        assertFalse(testSubject.contains(value));
    }

    private class MockValue {}
}
