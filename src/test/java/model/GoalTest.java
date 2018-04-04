package model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Daniel Riissanen on 4.4.2018.
 */
public class GoalTest {

    @Test
    public void testGetUnsatisfiedRequirementCountEmpty() {
        Goal testSubject = new Goal();
        assertEquals(0, testSubject.getUnsatisfiedRequirementCost(new MockState()), 0.00001f);
    }

    @Test
    public void testGetUnsatisfiedRequirementCountZeroRequirementZero() {
        Goal testSubject = new Goal();
        testSubject.addAdditionalRequirement(state -> 0);
        assertEquals(0, testSubject.getUnsatisfiedRequirementCost(new MockState()), 0.00001f);
    }

    @Test
    public void testGetUnsatisfiedRequirementCountNotZeroRequirementNotZero() {
        Goal testSubject = new Goal();
        testSubject.addAdditionalRequirement(state -> 1);
        assertEquals(1, testSubject.getUnsatisfiedRequirementCost(new MockState()), 0.00001f);
    }

    @Test
    public void testGetUnsatisfiedRequirementCountMixedRequirements() {
        Goal testSubject = new Goal();
        testSubject.addAdditionalRequirement(state -> 1);
        testSubject.addAdditionalRequirement(state -> 0);
        testSubject.addAdditionalRequirement(state -> 0);
        testSubject.addAdditionalRequirement(state -> 1);
        testSubject.addAdditionalRequirement(state -> 0);
        assertEquals(2, testSubject.getUnsatisfiedRequirementCost(new MockState()), 0.00001f);
    }

    @Test
    public void testGetUnsatisfiedRequirementCountWithState() {
        Goal testSubject = new Goal();
        testSubject.addAdditionalRequirement(state -> Math.abs(1 - state.query("a")));
        testSubject.addAdditionalRequirement(state -> Math.abs(1 - state.query("b")));
        assertEquals(1, testSubject.getUnsatisfiedRequirementCost(new MockState("a", 1)), 0.00001f);
    }

    @Test
    public void testGetDeficitZeroEmpty() {
        Goal testSubject = new Goal();
        assertEquals(0, testSubject.getDeficitCost(new MockState()), 0.00001f);
    }

    @Test
    public void testGetDeficitZeroRequirement() {
        Goal testSubject = new Goal();
        testSubject.setRequirement(state -> 0);
        assertEquals(0, testSubject.getDeficitCost(new MockState()), 0.00001f);
    }

    @Test
    public void testGetDeficitZeroState() {
        Goal testSubject = new Goal();
        testSubject.setRequirement(state -> Math.abs(1 - state.query("a")));
        assertEquals(0, testSubject.getDeficitCost(new MockState("a", 1)), 0.00001f);
    }

    @Test
    public void testGetDeficitNotZeroRequirement() {
        Goal testSubject = new Goal();
        testSubject.setRequirement(state -> 1);
        assertNotEquals(0, testSubject.getDeficitCost(new MockState()), 0.00001f);
    }

    @Test
    public void testGetDeficitNotZeroState() {
        Goal testSubject = new Goal();
        testSubject.setRequirement(state -> Math.abs(1 - state.query("a")));
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
        testSubject.setRequirement(state -> 1);
        assertFalse(testSubject.isEqual(new Goal(), new MockState()));
    }

    @Test
    public void testIsEqualFalseRequirementDifference() {
        Goal testHelper = new Goal();
        testHelper.setRequirement(state -> 1);
        Goal testSubject = new Goal();
        testSubject.setRequirement(state -> 0);
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
