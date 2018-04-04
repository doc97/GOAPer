package model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Daniel Riissanen on 4.4.2018.
 */
public class GoalTest {

    @Test
    public void testGetUnsatisfiedRequirementCountEmpty() {
        Goal testSubject = new Goal();
        assertEquals(0, testSubject.getUnsatisfiedRequirementCount(new MockState()));
    }

    @Test
    public void testGetUnsatisfiedRequirementCountTrueRequirement() {
        Goal testSubject = new Goal();
        testSubject.addRequirement(state -> true);
        assertEquals(0, testSubject.getUnsatisfiedRequirementCount(new MockState()));
    }

    @Test
    public void testGetUnsatisfiedRequirementCountFalseRequirement() {
        Goal testSubject = new Goal();
        testSubject.addRequirement(state -> false);
        assertEquals(1, testSubject.getUnsatisfiedRequirementCount(new MockState()));
    }

    @Test
    public void testGetUnsatisfiedRequirementCountMixedRequirements() {
        Goal testSubject = new Goal();
        testSubject.addRequirement(state -> false);
        testSubject.addRequirement(state -> true);
        testSubject.addRequirement(state -> true);
        testSubject.addRequirement(state -> false);
        testSubject.addRequirement(state -> true);
        assertEquals(2, testSubject.getUnsatisfiedRequirementCount(new MockState()));
    }

    @Test
    public void testGetUnsatisfiedRequirementCountWithState() {
        Goal testSubject = new Goal();
        testSubject.addRequirement(state -> state.query("a") == 1);
        testSubject.addRequirement(state -> state.query("b") == 1);
        assertEquals(1, testSubject.getUnsatisfiedRequirementCount(new MockState("a", 1)));
    }

    @Test
    public void testIsSatisfiedTrueEmpty() {
        Goal testSubject = new Goal();
        assertTrue(testSubject.isSatisfied(new MockState()));
    }

    @Test
    public void testIsSatisfiedTrueRequirement() {
        Goal testSubject = new Goal();
        testSubject.addRequirement(state -> true);
        assertTrue(testSubject.isSatisfied(new MockState()));
    }

    @Test
    public void testIsSatisfiedTrueState() {
        Goal testSubject = new Goal();
        testSubject.addRequirement(state -> state.query("a") == 1);
        assertTrue(testSubject.isSatisfied(new MockState("a", 1)));
    }

    @Test
    public void testIsSatisfiedFalseRequirement() {
        Goal testSubject = new Goal();
        testSubject.addRequirement(state -> false);
        assertFalse(testSubject.isSatisfied(new MockState()));
    }

    @Test
    public void testIsSatisfiedFalseState() {
        Goal testSubject = new Goal();
        testSubject.addRequirement(state -> state.query("a") == 1);
        assertFalse(testSubject.isSatisfied(new MockState("a", 0)));
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
        testSubject.addRequirement(state -> false);
        assertFalse(testSubject.isEqual(new Goal(), new MockState()));
    }

    @Test
    public void testIsEqualFalseRequirementDifference() {
        Goal testHelper = new Goal();
        testHelper.addRequirement(state -> false);
        testHelper.addRequirement(state -> false);
        Goal testSubject = new Goal();
        testSubject.addRequirement(state -> false);
        testSubject.addRequirement(state -> true);
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
