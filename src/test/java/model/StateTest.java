package model;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created by Daniel Riissanen on 16.3.2018.
 */
public class StateTest {

    private class MockNotState {

    }

    @Test
    public void testConstructorEmpty() {
        State testSubject = new State();
        assertEquals(0, testSubject.getKeys().size());
    }

    @Test
    public void testConstructorNotEmpty() {
        HashMap<String, Integer> keys = new HashMap<>();
        keys.put("a", 1);
        keys.put("b", 0);
        State testSubject = new State(keys);
        assertEquals(keys.keySet(), testSubject.getKeys());
    }

    @Test
    public void testConstructorCopy() {
        State testHelper = new State();
        testHelper.addKey("a", 1);
        testHelper.addKey("b", 0);
        State testSubject = new State(testHelper);
        testHelper.addKey("c", 1);
        assertEquals(2, testSubject.getKeys().size(), 2);
        assertTrue(testSubject.queryBoolean("a"));
        assertFalse(testSubject.queryBoolean("b"));
        assertNotEquals(testHelper.getKeys().hashCode(), testSubject.getKeys().hashCode());
    }

    @Test
    public void testConstructorNullCopy() {
        State testHelper= null;
        try {
            State testSubject = new State(testHelper);
            assertEquals(0, testSubject.getKeys().size());
        } catch (NullPointerException ignored) {
            fail("Constructor failed handle null argument");
        }
    }

    @Test
    public void testAddNullKey() {
        State testSubject = new State();
        testSubject.addKey(null, 1);
        assertEquals(0, testSubject.getKeys().size());
    }

    @Test
    public void testAddOneKey() {
        State testSubject = new State();
        testSubject.addKey("a", 1);
        assertEquals(1, testSubject.getKeys().size());
        assertTrue(testSubject.getKeys().contains("a"));
        assertTrue(testSubject.queryBoolean("a"));
    }

    @Test
    public void testAddTwoDistinctKeys() {
        State testSubject = new State();
        testSubject.addKey("a", 1);
        testSubject.addKey("b", 0);
        assertEquals(2, testSubject.getKeys().size());
        assertTrue(testSubject.getKeys().contains("a"));
        assertTrue(testSubject.getKeys().contains("b"));
        assertTrue(testSubject.queryBoolean("a"));
        assertFalse(testSubject.queryBoolean("b"));
    }

    @Test
    public void testAddTwoSameKeys() {
        State testSubject = new State();
        testSubject.addKey("a", 1);
        testSubject.addKey("a", 0);
        assertEquals(1, testSubject.getKeys().size());
        assertTrue(testSubject.getKeys().contains("a"));
        assertTrue(testSubject.queryBoolean("a"));
    }

    @Test
    public void testQueryEmpty() {
        State testSubject = new State();
        assertFalse(testSubject.queryBoolean("a"));
    }

    @Test
    public void testQueryNonExistent() {
        State testSubject = new State();
        testSubject.addKey("a", 1);
        assertFalse(testSubject.queryBoolean("b"));
    }

    @Test
    public void testQueryNull() {
        State testSubject = new State();
        testSubject.addKey("a", 1);
        assertFalse(testSubject.queryBoolean(null));
    }

    @Test
    public void testQueryExisting() {
        State testSubject = new State();
        testSubject.addKey("a", 1);
        testSubject.addKey("b", 0);
        assertTrue(testSubject.queryBoolean("a"));
        assertFalse(testSubject.queryBoolean("b"));
    }

    @Test
    public void testApplyNull() {
        State testSubject = new State();
        testSubject.addKey("a", 1);
        testSubject.apply(null, 0);
        assertEquals(1, testSubject.getKeys().size());
        assertTrue(testSubject.queryBoolean("a"));
    }

    @Test
    public void testApplyNonExistent() {
        State testSubject = new State();
        testSubject.apply("a", 1);
        assertEquals(1, testSubject.getKeys().size());
        assertTrue(testSubject.queryBoolean("a"));
    }

    @Test
    public void testApplyExisting() {
        State testSubject = new State();
        testSubject.addKey("a", 1);
        testSubject.apply("a", 0);
        assertEquals(1, testSubject.getKeys().size());
        assertFalse(testSubject.queryBoolean("a"));

        testSubject.apply("a", 1);
        assertEquals(1, testSubject.getKeys().size());
        assertTrue(testSubject.queryBoolean("a"));

        testSubject.apply("a", 1);
        assertEquals(1, testSubject.getKeys().size());
        assertTrue(testSubject.queryBoolean("a"));
    }

    @Test
    public void testUpdateNull() {
        State testSubject = new State();
        testSubject.addKey("a", 1);
        testSubject.update(null, 0);
        assertEquals(1, testSubject.getKeys().size());
        assertTrue(testSubject.queryBoolean("a"));
    }

    @Test
    public void testUpdateNonExistent() {
        State testSubject = new State();
        testSubject.update("a", 1);
        assertEquals(0, testSubject.getKeys().size());
    }

    @Test
    public void testUpdateExisting() {
        State testSubject = new State();
        testSubject.addKey("a", 1);
        testSubject.update("a", 0);
        assertEquals(1, testSubject.getKeys().size());
        assertFalse(testSubject.queryBoolean("a"));

        testSubject.update("a", 1);
        assertEquals(1, testSubject.getKeys().size());
        assertTrue(testSubject.queryBoolean("a"));

        testSubject.update("a", 1);
        assertEquals(1, testSubject.getKeys().size());
        assertTrue(testSubject.queryBoolean("a"));
    }

    @Test
    public void testToString() {
        State testSubject = new State();
        testSubject.addKey("a", 1);
        testSubject.addKey("b", 0);
        assertEquals("a: 1\nb: 0\n", testSubject.toString());
    }

    @Test
    public void testEqualsDifferentKeyCount() {
        State testSubject = new State();
        testSubject.addKey("a", 1);
        testSubject.addKey("b", 1);
        State testHelper = new State();
        testHelper.addKey("a", 1);
        assertNotEquals(testHelper, testSubject);
    }

    @Test
    public void testEqualsDifferentKeyValues() {
        State testSubject = new State();
        testSubject.addKey("a", 1);
        testSubject.addKey("b", 1);
        State testHelper = new State();
        testHelper.addKey("a", 1);
        testHelper.addKey("b", 0);
        assertNotEquals(testHelper, testSubject);
    }

    @Test
    public void testEqualsNull() {
        assertNotEquals(new State(), null);
    }

    @Test
    public void testEqualsNotState() {
        assertNotEquals(new State(), new MockNotState());
    }

    @Test
    public void testEqualsTrue() {
        State testHelper = new State(new HashMap<>());
        testHelper.addKey("a", 0);
        testHelper.addKey("b", 1);
        State testSubject = new State(new HashMap<>());
        testSubject.addKey("a", 0);
        testSubject.addKey("b", 1);
        assertEquals(testHelper, testSubject);
    }
}
