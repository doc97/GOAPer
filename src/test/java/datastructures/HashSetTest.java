package datastructures;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Daniel Riissanen on 14.4.2018.
 */
public class HashSetTest {

    @Test
    public void testConstructorEmpty() {
        HashSet<MockValue> testSubject = new HashSet<>();
        assertEquals(0, testSubject.count());
        assertEquals(16, testSubject.capacity());
        assertEquals(0.75f, testSubject.getLoadFactorLimit(), 0.000001f);
    }

    @Test
    public void testConstructorCapacityOverflow() {
        int limit = 1073741824;
        try {
            HashSet<MockValue> testSubject = new HashSet<>(limit + 1);
            assertEquals(0, testSubject.count());
            assertEquals(limit, testSubject.capacity());
            assertEquals(0.75f, testSubject.getLoadFactorLimit(), 0.000001f);
        } catch (OutOfMemoryError ignored) {}
    }

    @Test
    public void testConstructorCapacityUnderflow() {
        HashSet<MockValue> testSubject = new HashSet<>(-1);
        assertEquals(0, testSubject.count());
        assertEquals(0, testSubject.capacity());
        assertEquals(0.75f, testSubject.getLoadFactorLimit(), 0.000001f);
    }

    @Test
    public void testConstructorCapacityNormal() {
        HashSet<MockValue> testSubject = new HashSet<>(10);
        assertEquals(0, testSubject.count());
        assertEquals(10, testSubject.capacity());
        assertEquals(0.75f, testSubject.getLoadFactorLimit(), 0.000001f);
    }

    @Test
    public void testConstructorCapacityAndLoadOver() {
        HashSet<MockValue> testSubject = new HashSet<>(10, 2);
        assertEquals(0, testSubject.count());
        assertEquals(10, testSubject.capacity());
        assertEquals(1, testSubject.getLoadFactorLimit(), 0.000001f);
    }

    @Test
    public void testConstructorCapacityAndLoadUnder() {
        HashSet<MockValue> testSubject = new HashSet<>(10, -1);
        assertEquals(0, testSubject.count());
        assertEquals(10, testSubject.capacity());
        assertEquals(0, testSubject.getLoadFactorLimit(), 0.000001f);
    }

    @Test
    public void testConstructorCapacityAndLoadNormal() {
        HashSet<MockValue> testSubject = new HashSet<>(10, 0.5f);
        assertEquals(0, testSubject.count());
        assertEquals(10, testSubject.capacity());
        assertEquals(0.5f, testSubject.getLoadFactorLimit(), 0.000001f);
    }

    @Test
    public void testConstructorCopy() {
        HashSet<MockValue> testHelper = new HashSet<>(7, 0.7f);
        HashSet<MockValue> testSubject = new HashSet<>(testHelper);
        assertEquals(testHelper.count(), testSubject.count());
        assertEquals(testHelper.capacity(), testSubject.capacity());
        assertEquals(testHelper.getLoadFactorLimit(), testSubject.getLoadFactorLimit(), 0.000001f);
        MockValue[] testArray = new MockValue[testHelper.count()];
        MockValue[] subjectArray = new MockValue[testSubject.count()];
        testHelper.asArray(testArray);
        testSubject.asArray(subjectArray);
        for (int i = 0; i < testArray.length; i++)
            assertEquals(testArray[i], subjectArray[i]);
    }

    @Test
    public void testAdd() {
        MockValue value = new MockValue();
        HashSet<MockValue> testSubject = new HashSet<>();
        testSubject.add(value);
        assertEquals(1, testSubject.count());
        assertTrue(testSubject.contains(value));
    }

    @Test
    public void testAddNull() {
        HashSet<MockValue> testSubject = new HashSet<>();
        testSubject.add(null);
        testSubject.add(null);
        assertEquals(1, testSubject.count());
    }

    @Test
    public void testAddExistent() {
        MockValue value = new MockValue();
        HashSet<MockValue> testSubject = new HashSet<>();
        testSubject.add(value);
        testSubject.add(value);
        assertEquals(1, testSubject.count());
    }

    @Test
    public void testAddResize() {
        MockValue value1 = new MockValue();
        MockValue value2 = new MockValue();
        HashSet<MockValue> testSubject = new HashSet<>(2, 0.5f);
        testSubject.add(value1);
        assertEquals(2, testSubject.capacity());
        testSubject.add(value2);
        assertEquals(4, testSubject.capacity());
        assertTrue(testSubject.contains(value1));
        assertTrue(testSubject.contains(value2));
    }

    @Test
    public void testRemove() {
        MockValue value = new MockValue();
        HashSet<MockValue> testSubject = new HashSet<>();
        testSubject.add(value);
        testSubject.remove(value);
        assertEquals(0, testSubject.count());
        assertFalse(testSubject.contains(value));
    }

    @Test
    public void testRemoveNull() {
        HashSet<MockValue> testSubject = new HashSet<>();
        testSubject.add(null);
        testSubject.remove(null);
        assertEquals(0, testSubject.count());
    }

    @Test
    public void testRemoveCollision() {
        MockValueHashCode value = new MockValueHashCode(2);
        HashSet<MockValueHashCode> testSubject = new HashSet<>();
        testSubject.add(new MockValueHashCode(2));
        testSubject.add(value);
        assertEquals(2, testSubject.count());
        testSubject.remove(value);
        assertEquals(1, testSubject.count());
    }

    @Test
    public void testRemoveNonExistent() {
        HashSet<MockValue> testSubject = new HashSet<>();
        testSubject.add(new MockValue());
        testSubject.remove(new MockValue());
        assertEquals(1, testSubject.count());
    }

    @Test
    public void testContainsTrue() {
        MockValue value = new MockValue();
        HashSet<MockValue> testSubject = new HashSet<>();
        testSubject.add(value);
        testSubject.add(new MockValue());
        assertTrue(testSubject.contains(value));
    }

    @Test
    public void testContainsFalse() {
        HashSet<MockValue> testSubject = new HashSet<>();
        testSubject.add(new MockValue());
        assertFalse(testSubject.contains(new MockValue()));
    }

    @Test
    public void testAsArrayTooShort() {
        HashSet<MockValue> testSubject = new HashSet<>();
        testSubject.add(new MockValue());
        testSubject.add(new MockValue());
        testSubject.add(new MockValue());
        MockValue[] array = testSubject.asArray(new MockValue[2]);
        for (MockValue value : array)
            assertNotNull(value);
    }

    @Test
    public void testSetLoadFactorLimit() {
        HashSet<MockValue> testSubject = new HashSet<>();
        testSubject.setLoadFactorLimit(0.55f);
        assertEquals(0.55f, testSubject.getLoadFactorLimit(), 0.000001f);
    }

    private class MockValue {}

    private class MockValueHashCode {
        private int hash;

        MockValueHashCode(int hash) {
            this.hash = hash;
        }

        @Override
        public int hashCode() {
            return hash;
        }
    }
}
