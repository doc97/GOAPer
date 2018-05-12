package model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Daniel Riissanen on 4.4.2018.
 */
public class GoalTest {

    @Test
    public void testConstructorEmpty() {
        Goal testSubject = new Goal();
        assertEquals(testSubject.getDeficitCost(new MockState()), 0, 0.000001f);
    }

    @Test
    public void testConstructorCopy() {
        Goal testHelper = new Goal(state -> 5);
        Goal testSubject = new Goal(testHelper);
        assertNotEquals(testHelper, testSubject);
        assertEquals(5, testSubject.getDeficitCost(new MockState()), 0.000001f);
    }

    @Test
    public void testConstructorCopyNull() {
        Goal testSubject = new Goal((Goal) null);
        assertEquals(0, testSubject.getDeficitCost(new MockState()), 0.000001f);
    }

    @Test
    public void testGetDeficitZeroEmpty() {
        Goal testSubject = new Goal();
        assertEquals(0, testSubject.getDeficitCost(new MockState()), 0.00001f);
    }

    @Test
    public void testGetDeficitZeroRequirement() {
        Goal testSubject = new Goal(state -> 0);
        assertEquals(0, testSubject.getDeficitCost(new MockState()), 0.00001f);
    }

    @Test
    public void testGetDeficitZeroState() {
        Goal testSubject = new Goal(state -> Math.abs(1 - state.query("a")));
        assertEquals(0, testSubject.getDeficitCost(new MockState("a", 1)), 0.00001f);
    }

    @Test
    public void testGetDeficitNotZeroRequirement() {
        Goal testSubject = new Goal(state -> 1);
        assertNotEquals(0, testSubject.getDeficitCost(new MockState()), 0.00001f);
    }

    @Test
    public void testGetDeficitNotZeroState() {
        Goal testSubject = new Goal(state -> Math.abs(1 - state.query("a")));
        assertNotEquals(0, testSubject.getDeficitCost(new MockState("a", 0)), 0.00001f);
    }

    @Test
    public void testIsEqualTrueEmpty() {
        Goal testSubject = new Goal();
        assertTrue(testSubject.isEqual(new Goal(), new MockState()));
    }

    @Test
    public void testIsEqualFalseInstanceOf() {
        Goal testSubject = new Goal();
        assertFalse(testSubject.isEqual(new MockNotGoal(), new MockState()));
    }

    @Test
    public void testIsEqualFalseRequirementCount() {
        Goal testSubject = new Goal(state -> 1);
        assertFalse(testSubject.isEqual(new Goal(), new MockState()));
    }

    @Test
    public void testIsEqualFalseRequirementDifference() {
        Goal testHelper = new Goal(state -> 1);
        Goal testSubject = new Goal(state -> 0);
        assertFalse(testSubject.isEqual(testHelper, new MockState()));
    }

    @Test
    public void testIsEqualFalseAdditionalRequirementDifference() {
        Goal testHelper = new Goal(state -> 1);
        Goal testSubject = new Goal(state -> 0);
        assertFalse(testSubject.isEqual(testHelper, new MockState()));
    }

    private class MockState extends State {
        MockState() {
            super();
        }

        MockState(String key, int value) {
            super();
            addKey(key, value);
        }
    }

    private class MockNotGoal { }
}
