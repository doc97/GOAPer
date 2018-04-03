package model.simulation;

import model.State;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Daniel Riissanen on 1.4.2018.
 */
public class EventTest {

    private class MockState extends State {
    }

    @Test
    public void testToStringEmpty() {
        Event testSubject = new Event();
        assertEquals("<Empty>", testSubject.toString());
    }

    @Test
    public void testToStringOneKey() {
        Event testSubject = new Event();
        testSubject.addKey("a", 1);
        assertEquals("a: 1", testSubject.toString());
    }

    @Test
    public void testToStringTwoKeys() {
        Event testSubject = new Event();
        testSubject.addKey("a", 0);
        testSubject.addKey("b", 1);
        assertEquals("a: 0\nb: 1", testSubject.toString());
    }

    @Test
    public void testAddKeyOne() {
        MockState state = new MockState();
        Event testSubject = new Event();
        testSubject.addKey("a", 1);
        testSubject.activate(state);
        assertTrue(state.queryBoolean("a"));
    }

    @Test
    public void testAddKeyTwo() {
        MockState state = new MockState();
        Event testSubject = new Event();
        testSubject.addKey("a", 1);
        testSubject.addKey("b", 0);
        testSubject.activate(state);
        assertTrue(state.queryBoolean("a"));
        assertFalse(state.queryBoolean("b"));
    }

    @Test
    public void testRemoveKeyOneOfOne() {
        Event testHelper = new Event();
        Event testSubject = new Event();
        testSubject.addKey("a", 1);
        testSubject.removeKey("a");
        assertEquals(testHelper.toString(), testSubject.toString());
    }

    @Test
    public void testRemoveKeyOneOfTwo() {
        Event testHelper = new Event();
        testHelper.addKey("b", 1);
        Event testSubject = new Event();
        testSubject.addKey("a", 1);
        testSubject.addKey("b", 1);
        testSubject.removeKey("a");
        assertEquals(testHelper.toString(), testSubject.toString());
    }
}
