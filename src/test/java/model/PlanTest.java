package model;

import algorithms.SubPlan;
import datastructures.DynamicArray;
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
    public void testConstructorSubPlanNull() {
        Plan testSubject = new Plan((SubPlan)null, null);
        assertTrue(testSubject.isEmpty());
        assertFalse(testSubject.isComplete());
    }

    @Test
    public void testConstructorSubPlan() {
        DynamicArray<Action> actions = new DynamicArray<>();
        actions.add(new MockAction(true));
        actions.add(new MockAction(false));
        MockSubPlan subPlan = new MockSubPlan(7, actions);

        Plan testSubject = new Plan(subPlan, null);

        assertEquals(subPlan.getActions().length, testSubject.getActions().length);
        for (int i = 0; i < testSubject.getActions().length; ++i)
            assertEquals(subPlan.getActions()[subPlan.getActions().length - i - 1], testSubject.getActions()[i]);
        assertFalse(testSubject.isComplete());
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

    private static class MockAction extends Action {
        private boolean canExecute;

        MockAction(boolean canExecute) {
            super("", 0, new Precondition[] { state -> 0 }, state -> {}, state -> {});
            this.canExecute = canExecute;
        }

        @Override
        public boolean canExecute(State state) {
            return canExecute;
        }
    }

    private static class MockSubPlan extends SubPlan {
        MockSubPlan(int deficit) {
            super(new State(), new MockGoal(deficit), new DynamicArray<>(), new DynamicArray<>(), new DynamicArray<>(), 0);
        }

        MockSubPlan(int deficit, DynamicArray<Action> actions) {
            super(new State(), new MockGoal(deficit), actions, new DynamicArray<>(), new DynamicArray<>(), 0);
        }
    }

    private static class MockGoal extends Goal {
        private float deficit;

        MockGoal(float deficit) {
            super();
            this.deficit = deficit;
        }

        @Override
        public float getDeficitCost(State state) {
            return deficit;
        }
    }
}
