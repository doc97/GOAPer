package algorithms;

import datastructures.DynamicArray;
import datastructures.HashTable;
import model.Action;
import model.Goal;
import model.Precondition;
import model.State;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Daniel Riissanen on 2.4.2018.
 */
public class SubPlanTest {

    @Test
    public void testConstructorNull() {
        SubPlan testSubject = new SubPlan(null, null, null, null, null, 0);
        assertNotNull(testSubject.getState());
        assertNotNull(testSubject.getGoal());
        assertNotNull(testSubject.getActions());
    }

    @Test
    public void testConstructor() {
        State state = new State();
        Goal goal = new Goal();
        DynamicArray<Action> actions = new DynamicArray<>();
        SubPlan testSubject = new SubPlan(state, goal, actions, null, null, 7);
        assertEquals(state, testSubject.getState());
        assertEquals(goal, testSubject.getGoal());
        assertArrayEquals(actions.asArray(new Action[actions.count()]), testSubject.getActions());
        assertEquals(7, testSubject.getCost());
    }

    @Test
    public void testEqualsNull() {
        SubPlan testSubject = new SubPlan(new State(), new Goal(), new DynamicArray<>(), new DynamicArray<>(),
                new DynamicArray<>(), 0);
        assertNotEquals(null, testSubject);
    }

    @Test
    public void testEqualsNotInstanceOf() {
        SubPlan testSubject = new SubPlan(new State(), new Goal(), new DynamicArray<>(), new DynamicArray<>(),
                new DynamicArray<>(), 0);
        assertNotEquals(testSubject, new MockNotSubPlan());
    }

    @Test
    public void testIsValidSubPlanTrue() {
        MockSubPlan testSubject = new MockSubPlan();
        assertTrue(testSubject.isValidPlan(new State()));
    }

    @Test
    public void testIsValidSubPlanFalse() {
        MockSubPlan testSubject = new MockSubPlan(s -> 1);
        assertFalse(testSubject.isValidPlan(new State()));
    }

    @Test
    public void testEqualsDifferentCost() {
        SubPlan testHelper = new SubPlan(new State(), new Goal(), new DynamicArray<>(), new DynamicArray<>(),
                new DynamicArray<>(), 1);
        SubPlan testSubject = new SubPlan(new State(), new Goal(), new DynamicArray<>(), new DynamicArray<>(),
                new DynamicArray<>(), 0);
        assertNotEquals(testHelper, testSubject);
    }

    @Test
    public void testEqualsDifferentState() {
        HashTable<String, Integer> keys = new HashTable<>();
        keys.put("a", 1);
        SubPlan testHelper = new SubPlan(new State(keys), new Goal(), new DynamicArray<>(), new DynamicArray<>(),
                new DynamicArray<>(), 0);
        SubPlan testSubject = new SubPlan(new State(), new Goal(), new DynamicArray<>(), new DynamicArray<>(),
                new DynamicArray<>(), 0);
        assertNotEquals(testHelper, testSubject);
    }

    @Test
    public void testEqualsDifferentGoal() {
        Goal goal = new Goal(state -> Math.abs(1 - state.query("a")));
        SubPlan testHelper = new SubPlan(new State(), goal, new DynamicArray<>(), new DynamicArray<>(),
                new DynamicArray<>(), 0);
        SubPlan testSubject = new SubPlan(new State(), new Goal(), new DynamicArray<>(), new DynamicArray<>(),
                new DynamicArray<>(), 0);
        assertNotEquals(testHelper, testSubject);
    }

    private static class MockNotSubPlan { }

    private static class MockSubPlan extends SubPlan {
        MockSubPlan(Precondition... preconditions) {
            super(new State(), new Goal(preconditions), new DynamicArray<>(), new DynamicArray<>(), new DynamicArray<>(), 0);
        }
    }

}
