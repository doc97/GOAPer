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
        testSubject.addKey("a", true);
        assertEquals("a: true", testSubject.toString());
    }

    @Test
    public void testToStringTwoKeys() {
        Event testSubject = new Event();
        testSubject.addKey("a", false);
        testSubject.addKey("b", true);
        assertEquals("a: false\nb: true", testSubject.toString());
    }

    @Test
    public void testAddKeyOne() {
        MockState state = new MockState();
        Event testSubject = new Event();
        testSubject.addKey("a", true);
        testSubject.activate(state);
        assertTrue(state.query("a"));
    }

    @Test
    public void testAddKeyTwo() {
        MockState state = new MockState();
        Event testSubject = new Event();
        testSubject.addKey("a", true);
        testSubject.addKey("b", false);
        testSubject.activate(state);
        assertTrue(state.query("a"));
        assertFalse(state.query("b"));
    }

    @Test
    public void testRemoveKeyOneOfOne() {
        Event testHelper = new Event();
        Event testSubject = new Event();
        testSubject.addKey("a", true);
        testSubject.removeKey("a");
        assertEquals(testHelper.toString(), testSubject.toString());
    }

    @Test
    public void testRemoveKeyOneOfTwo() {
        Event testHelper = new Event();
        testHelper.addKey("b", true);
        Event testSubject = new Event();
        testSubject.addKey("a", true);
        testSubject.addKey("b", true);
        testSubject.removeKey("a");
        assertEquals(testHelper.toString(), testSubject.toString());
    }
}
