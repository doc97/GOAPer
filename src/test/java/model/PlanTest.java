package model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Daniel Riissanen on 22.3.2018.
 */
public class PlanTest {

    @Test
    public void testConstructorEmpty() {
        Plan testSubject = new Plan();
        assertNotNull(testSubject.getActions());
        assertEquals(0, testSubject.getCost());
        assertEquals(0, testSubject.getActions().length);
    }

    @Test
    public void testConstructorNonEmpty() {
        Action[] actions = new Action[] {
            new Action("", 1, null, null)
        };
        Plan testSubject = new Plan(actions, 2);
        assertEquals(actions.length, testSubject.getActions().length);
        assertEquals(3, testSubject.getCost());
    }

    @Test
    public void testGetNextActionEmpty() {
        Plan testSubject = new Plan();
        assertNotNull(testSubject.getNextAction());
    }

    @Test
    public void testGetNextActionNonEmpty() {
        Action[] actions = new Action[] {
                new Action("", 0, null, null)
        };
        Plan testSubject = new Plan(actions, 0);
        assertEquals(actions[0], testSubject.getNextAction());
    }

    @Test
    public void testToStringEmpty() {
        Plan testSubject = new Plan();
        assertEquals("No plan", testSubject.toString());
    }

    @Test
    public void testToStringNonEmpty() {
        Action[] actions = new Action[] {
                new Action("Test name", 10, null, null)
        };
        Plan testSubject = new Plan(actions, 0);
        String expected = "[Start] -> Test name -> [Goal] (cost: 10, actions: 1)";
        assertEquals(expected, testSubject.toString());
    }
}
