package model;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by Daniel Riissanen on 4.4.2018.
 */
public class GoalTest {

    @Test
    public void testConstructorEmpty() {
        Goal testSubject = new Goal();
        assertEquals(testSubject.getDeficitCost(new MockState()), 0, 0.000001f);
        assertEquals(testSubject.getAdditionalRequirementsDeficitCost(new MockState()), 0, 0.000001f);
    }

    @Test
    public void testConstructorAdditionalRequirements() {
        ArrayList<Precondition> requirements = new ArrayList<>();
        requirements.add(state -> 1);
        requirements.add(state -> 2);
        requirements.add(state -> 3);
        requirements.add(state -> 4);
        Goal testSubject = new Goal(requirements);
        assertEquals(testSubject.getDeficitCost(new MockState()), 0, 0.000001f);
        assertEquals(testSubject.getAdditionalRequirementsDeficitCost(new MockState()), 10, 0.000001f);
    }

    @Test
    public void testConstructorCopy() {
        ArrayList<Precondition> requirements = new ArrayList<>();
        requirements.add(state -> 1);
        requirements.add(state -> 2);
        requirements.add(state -> 3);
        requirements.add(state -> 4);
        Goal testHelper = new Goal(requirements);
        testHelper.setRequirement(state -> 5);
        Goal testSubject = new Goal(testHelper);
        assertNotEquals(testHelper, testSubject);
        assertEquals(testSubject.getDeficitCost(new MockState()), 5, 0.000001f);
        assertEquals(testSubject.getAdditionalRequirementsDeficitCost(new MockState()), 10, 0.000001f);
    }

    @Test
    public void testGetAdditionalRequirementsDeficitCostEmpty() {
        Goal testSubject = new Goal();
        assertEquals(0, testSubject.getAdditionalRequirementsDeficitCost(new MockState()), 0.00001f);
    }

    @Test
    public void testGetAdditionalRequirementsDeficitCostZeroRequirement() {
        Goal testSubject = new Goal();
        testSubject.addAdditionalRequirement(state -> 0);
        assertEquals(0, testSubject.getAdditionalRequirementsDeficitCost(new MockState()), 0.00001f);
    }

    @Test
    public void testGetAdditionalRequirementsDeficitCostNonZeroRequirement() {
        Goal testSubject = new Goal();
        testSubject.addAdditionalRequirement(state -> 1);
        assertEquals(1, testSubject.getAdditionalRequirementsDeficitCost(new MockState()), 0.00001f);
    }

    @Test
    public void testGetAdditionalRequirementsDeficitCostMixedRequirements() {
        Goal testSubject = new Goal();
        testSubject.addAdditionalRequirement(state -> 1);
        testSubject.addAdditionalRequirement(state -> 0);
        testSubject.addAdditionalRequirement(state -> 0);
        testSubject.addAdditionalRequirement(state -> 1);
        testSubject.addAdditionalRequirement(state -> 0);
        assertEquals(2, testSubject.getAdditionalRequirementsDeficitCost(new MockState()), 0.00001f);
    }

    @Test
    public void testGetAdditionalRequirementsDeficitCostWithState() {
        Goal testSubject = new Goal();
        testSubject.addAdditionalRequirement(state -> Math.abs(1 - state.query("a")));
        testSubject.addAdditionalRequirement(state -> Math.abs(1 - state.query("b")));
        assertEquals(1, testSubject.getAdditionalRequirementsDeficitCost(new MockState("a", 1)), 0.00001f);
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

    @Test
    public void testIsEqualFalseAdditionalRequirementDifference() {
        Goal testHelper = new Goal();
        testHelper.addAdditionalRequirement(state -> 1);
        Goal testSubject = new Goal();
        testSubject.addAdditionalRequirement(state -> 0);
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
