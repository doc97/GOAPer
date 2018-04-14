package datastructures;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Daniel Riissanen on 11.4.2018.
 */
public class HashTableTest {

    @Test
    public void testConstructorEmpty() {
        HashTable<MockKey, MockValue> testSubject = new HashTable<>();
        assertEquals(testSubject.count(), 0);
        assertEquals(testSubject.capacity(), 64);
    }

    @Test
    public void testConstructorCapacityOverflow() {
        int limit = 1073741824;
        try {
            HashTable<MockKey, MockValue> testSubject = new HashTable<>(limit + 1);
            assertEquals(testSubject.count(), 0);
            assertEquals(testSubject.capacity(), limit);
        } catch (OutOfMemoryError ignored) {}
    }

    @Test
    public void testConstructorCapacityUnderflow() {
        HashTable<MockKey, MockValue> testSubject = new HashTable<>(-1);
        assertEquals(testSubject.count(), 0);
        assertEquals(testSubject.capacity(), 0);
    }

    @Test
    public void testConstructorCapacityNormal() {
        HashTable<MockKey, MockValue> testSubject = new HashTable<>(10);
        assertEquals(testSubject.count(), 0);
        assertEquals(testSubject.capacity(), 10);
    }

    @Test
    public void testPut() {
        MockKey key = new MockKey();
        MockValue value = new MockValue();
        HashTable<MockKey, MockValue> testSubject = new HashTable<>();
        testSubject.put(key, value);
        assertTrue(testSubject.containsKey(key));
        assertEquals(testSubject.get(key), value);
    }

    @Test
    public void testPutReplace() {
        MockKey key = new MockKey();
        MockValue value = new MockValue();
        HashTable<MockKey, MockValue> testSubject = new HashTable<>();
        testSubject.put(key, new MockValue());
        testSubject.put(key, value);
        assertTrue(testSubject.containsKey(key));
        assertEquals(testSubject.get(key), value);
    }

    @Test
    public void testRemove() {
        MockKey key = new MockKey();
        MockValue value = new MockValue();
        HashTable<MockKey, MockValue> testSubject = new HashTable<>();
        testSubject.put(key, value);
        testSubject.remove(key);
        assertFalse(testSubject.containsKey(key));
        assertNull(testSubject.get(key));
    }

    @Test
    public void testContainsKeyTrue() {
        MockKey key = new MockKey();
        MockValue value = new MockValue();
        HashTable<MockKey, MockValue> testSubject = new HashTable<>();
        testSubject.put(key, value);
        assertTrue(testSubject.containsKey(key));
    }

    @Test
    public void testContainsKeyFalse() {
        HashTable<MockKey, MockValue> testSubject = new HashTable<>();
        testSubject.put(new MockKey(), new MockValue());
        assertFalse(testSubject.containsKey(new MockKey()));
    }

    @Test
    public void testKeys() {
        MockKey[] inputKeys = new MockKey[] {
                new MockKey(),
                new MockKey()
        };
        MockValue[] inputValues = new MockValue[] {
                new MockValue(),
                new MockValue()
        };

        HashTable<MockKey, MockValue> testSubject = new HashTable<>();
        for (int i = 0; i < inputKeys.length; i++)
            testSubject.put(inputKeys[i], inputValues[i]);

        HashSet<MockKey> keys = testSubject.keys();
        assertEquals(keys.count(), inputKeys.length);

        for (MockKey inputKey : inputKeys)
            assertTrue(keys.contains(inputKey));
    }

    @Test
    public void testValues() {
        MockKey[] inputKeys = new MockKey[] {
                new MockKey(),
                new MockKey()
        };
        MockValue[] inputValues = new MockValue[] {
                new MockValue(),
                new MockValue()
        };

        HashTable<MockKey, MockValue> testSubject = new HashTable<>();
        for (int i = 0; i < inputKeys.length; i++)
            testSubject.put(inputKeys[i], inputValues[i]);

        DynamicArray<MockValue> values = testSubject.values();
        assertEquals(values.count(), inputValues.length);

        for (MockValue inputValue : inputValues)
            assertTrue(values.contains(inputValue));
    }

    private class MockKey {}
    private class MockValue {}
}
