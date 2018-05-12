package model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Daniel Riissanen on 22.3.2018.
 */
public class PlanTest {

    @Test
    public void testConstructorIsCompleteTrue() {
        Plan testSubject = new Plan(true);
        assertNotNull(testSubject.getActions());
        assertEquals(0, testSubject.getCost());
        assertEquals(0, testSubject.getActions().length);
        assertTrue(testSubject.isComplete());
    }

    @Test
    public void testConstructorIsCompleteFalse() {
        Plan testSubject = new Plan(false);
        assertNotNull(testSubject.getActions());
        assertEquals(0, testSubject.getCost());
        assertEquals(0, testSubject.getActions().length);
        assertFalse(testSubject.isComplete());
    }

    @Test
    public void testConstructorNonEmpty() {
        Action[] actions = new Action[] {
            new Action("", 3, null, null, null)
        };
        Plan testSubject = new Plan(actions, true);
        assertEquals(actions.length, testSubject.getActions().length);
        assertEquals(3, testSubject.getCost());
        assertTrue(testSubject.isComplete());
    }

    @Test
    public void testGetNextActionEmpty() {
        Plan testSubject = new Plan(false);
        assertNotNull(testSubject.getNextAction());
    }

    @Test
    public void testGetNextActionNonEmpty() {
        Action[] actions = new Action[] {
                new Action("", 0, null, null, null)
        };
        Plan testSubject = new Plan(actions, false);
        assertEquals(actions[0], testSubject.getNextAction());
    }

    @Test
    public void testToStringEmpty() {
        Plan testSubject = new Plan(false);
        assertEquals("No plan", testSubject.toString());
    }

    @Test
    public void testToStringNonEmptyNotComplete() {
        Action[] actions = new Action[] {
                new Action("Test name", 10, null, null, null)
        };
        Plan testSubject = new Plan(actions, false);
        String expected = "[NOT COMPLETE]: [Start] -> ??? -> Test name -> [Goal] (cost: 10, actions: 1)";
        assertEquals(expected, testSubject.toString());
    }

    @Test
    public void testToStringNonEmptyComplete() {
        Action[] actions = new Action[] {
                new Action("Test name", 10, null, null, null)
        };
        Plan testSubject = new Plan(actions, true);
        String expected = "[Start] -> Test name -> [Goal] (cost: 10, actions: 1)";
        assertEquals(expected, testSubject.toString());
    }

    @Test
    public void testCompareVsNonComplete() {
        Plan testHelper = new Plan(new Action[0], false);
        Plan testSubject = new Plan(new Action[0], true);
        assertEquals(-1, testSubject.compare(testHelper));
    }

    @Test
    public void testCompareVsComplete() {
        Plan testHelper = new Plan(new Action[0], true);
        Plan testSubject = new Plan(new Action[0], false);
        assertEquals(1, testSubject.compare(testHelper));
    }

    @Test
    public void testCompareCost() {
        Plan testHelper = new Plan(new Action[] { new Action("", 1,
                null, null, null )}, false);
        Plan testSubject = new Plan(new Action[] { new Action("", 3,
                null, null, null )}, false);
        assertEquals(2, testSubject.compare(testHelper));
    }
}
