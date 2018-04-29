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
        Goal testHelper = new Goal();
        testHelper.addRequirement(state -> 5, state -> {});
        testHelper.addRequirement(state -> 1, state -> {});
        testHelper.addRequirement(state -> 2, state -> {});
        testHelper.addRequirement(state -> 3, state -> {});
        testHelper.addRequirement(state -> 4, state -> {});
        Goal testSubject = new Goal(testHelper);
        assertNotEquals(testHelper, testSubject);
        assertEquals(5, testSubject.getDeficitCost(new MockState()), 0.000001f);
    }

    @Test
    public void testGetDeficitZeroEmpty() {
        Goal testSubject = new Goal();
        assertEquals(0, testSubject.getDeficitCost(new MockState()), 0.00001f);
    }

    @Test
    public void testGetDeficitZeroRequirement() {
        Goal testSubject = new Goal();
        testSubject.addRequirement(state -> 0, state -> {});
        assertEquals(0, testSubject.getDeficitCost(new MockState()), 0.00001f);
    }

    @Test
    public void testGetDeficitZeroState() {
        Goal testSubject = new Goal();
        testSubject.addRequirement(state -> Math.abs(1 - state.query("a")), state -> {});
        assertEquals(0, testSubject.getDeficitCost(new MockState("a", 1)), 0.00001f);
    }

    @Test
    public void testGetDeficitNotZeroRequirement() {
        Goal testSubject = new Goal();
        testSubject.addRequirement(state -> 1, state -> {});
        assertNotEquals(0, testSubject.getDeficitCost(new MockState()), 0.00001f);
    }

    @Test
    public void testGetDeficitNotZeroState() {
        Goal testSubject = new Goal();
        testSubject.addRequirement(state -> Math.abs(1 - state.query("a")), state -> {});
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
        Goal testSubject = new Goal();
        testSubject.addRequirement(state -> 1, state -> {});
        assertFalse(testSubject.isEqual(new Goal(), new MockState()));
    }

    @Test
    public void testIsEqualFalseRequirementDifference() {
        Goal testHelper = new Goal();
        testHelper.addRequirement(state -> 1, state -> {});
        Goal testSubject = new Goal();
        testSubject.addRequirement(state -> 0, state -> {});
        assertFalse(testSubject.isEqual(testHelper, new MockState()));
    }

    @Test
    public void testIsEqualFalseAdditionalRequirementDifference() {
        Goal testHelper = new Goal();
        testHelper.addRequirement(state -> 1, state -> {});
        Goal testSubject = new Goal();
        testSubject.addRequirement(state -> 0, state -> {});
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
