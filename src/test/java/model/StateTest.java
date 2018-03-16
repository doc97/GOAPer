package model;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created by Daniel Riissanen on 16.3.2018.
 */
public class StateTest {

    @Test
    public void testConstructorEmpty() {
        State testSubject = new State();
        assertEquals(0, testSubject.getKeys().size());
    }

    @Test
    public void testConstructorNotEmpty() {
        HashMap<String, Boolean> keys = new HashMap<>();
        keys.put("a", true);
        keys.put("b", false);
        State testSubject = new State(keys);
        assertEquals(keys.keySet(), testSubject.getKeys());
    }

    @Test
    public void testConstructorCopy() {
        State testHelper = new State();
        testHelper.addKey("a", true);
        testHelper.addKey("b", false);
        State testSubject = new State(testHelper);
        testHelper.addKey("c", true);
        assertEquals(2, testSubject.getKeys().size(), 2);
        assertEquals(true, testSubject.query("a"));
        assertEquals(false, testSubject.query("b"));
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
        testSubject.addKey(null, true);
        assertEquals(0, testSubject.getKeys().size());
    }

    @Test
    public void testAddOneKey() {
        State testSubject = new State();
        testSubject.addKey("a", true);
        assertEquals(1, testSubject.getKeys().size());
        assertTrue(testSubject.getKeys().contains("a"));
        assertEquals(true, testSubject.query("a"));
    }

    @Test
    public void testAddTwoDistinctKeys() {
        State testSubject = new State();
        testSubject.addKey("a", true);
        testSubject.addKey("b", false);
        assertEquals(2, testSubject.getKeys().size());
        assertTrue(testSubject.getKeys().contains("a"));
        assertTrue(testSubject.getKeys().contains("b"));
        assertEquals(true, testSubject.query("a"));
        assertEquals(false, testSubject.query("b"));
    }

    @Test
    public void testAddTwoSameKeys() {
        State testSubject = new State();
        testSubject.addKey("a", true);
        testSubject.addKey("a", false);
        assertEquals(1, testSubject.getKeys().size());
        assertTrue(testSubject.getKeys().contains("a"));
        assertEquals(true, testSubject.query("a"));
    }

    @Test
    public void testQueryEmpty() {
        State testSubject = new State();
        assertFalse(testSubject.query("a"));
    }

    @Test
    public void testQueryNonExistent() {
        State testSubject = new State();
        testSubject.addKey("a", true);
        assertFalse(testSubject.query("b"));
    }

    @Test
    public void testQueryNull() {
        State testSubject = new State();
        testSubject.addKey("a", true);
        assertFalse(testSubject.query(null));
    }

    @Test
    public void testQueryExisting() {
        State testSubject = new State();
        testSubject.addKey("a", true);
        testSubject.addKey("b", false);
        assertEquals(true, testSubject.query("a"));
        assertEquals(false, testSubject.query("b"));
    }

    @Test
    public void testApplyNull() {
        State testSubject = new State();
        testSubject.addKey("a", true);
        testSubject.apply(null, false);
        assertEquals(1, testSubject.getKeys().size());
        assertEquals(true, testSubject.query("a"));
    }

    @Test
    public void testApplyNonExistent() {
        State testSubject = new State();
        testSubject.apply("a", true);
        assertEquals(1, testSubject.getKeys().size());
        assertEquals(true, testSubject.query("a"));
    }

    @Test
    public void testApplyExisting() {
        State testSubject = new State();
        testSubject.addKey("a", true);
        testSubject.apply("a", false);
        assertEquals(1, testSubject.getKeys().size());
        assertEquals(false, testSubject.query("a"));

        testSubject.apply("a", true);
        assertEquals(1, testSubject.getKeys().size());
        assertEquals(true, testSubject.query("a"));

        testSubject.apply("a", true);
        assertEquals(1, testSubject.getKeys().size());
        assertEquals(true, testSubject.query("a"));
    }

    @Test
    public void testUpdateNull() {
        State testSubject = new State();
        testSubject.addKey("a", true);
        testSubject.update(null, false);
        assertEquals(1, testSubject.getKeys().size());
        assertEquals(true, testSubject.query("a"));
    }

    @Test
    public void testUpdateNonExistent() {
        State testSubject = new State();
        testSubject.update("a", true);
        assertEquals(0, testSubject.getKeys().size());
    }

    @Test
    public void testUpdateExisting() {
        State testSubject = new State();
        testSubject.addKey("a", true);
        testSubject.update("a", false);
        assertEquals(1, testSubject.getKeys().size());
        assertEquals(false, testSubject.query("a"));

        testSubject.update("a", true);
        assertEquals(1, testSubject.getKeys().size());
        assertEquals(true, testSubject.query("a"));

        testSubject.update("a", true);
        assertEquals(1, testSubject.getKeys().size());
        assertEquals(true, testSubject.query("a"));
    }
}
