package algorithms;

import model.*;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by Daniel Riissanen on 4.4.2018.
 */
public class AlgorithmUtilsTest {

    @Test
    public void testGetNextPlan() {
        MockSubPlan plan = new MockSubPlan("count", 1, 1);
        MockAction action = new MockAction(false);
        AlgorithmUtils testSubject = new AlgorithmUtils();
        SubPlan result = testSubject.getNextPlan(plan, action);
        assertEquals(1, result.getCost());
        assertNotEquals(plan.getGoal().hashCode(), result.getGoal().hashCode());
        assertNotEquals(plan.getState().hashCode(), result.getState().hashCode());
        assertEquals(plan.getActions().size() + 1, result.getActions().size());
        assertEquals(1, plan.getGoal().getUnsatisfiedRequirementCost(plan.getState()), 0.00001f);
    }

    @Test
    public void testIsGoodActionTrue() {
        MockSubPlan plan = new MockSubPlan("count", 1, 0);
        MockAction action = new MockAction(false, state -> 0, state -> state.update("count", 0));
        AlgorithmUtils testSubject = new AlgorithmUtils();
        assertTrue(testSubject.isGoodAction(plan, action));
    }

    @Test
    public void testIsGoodActionFalseEqualCount() {
        MockSubPlan plan = new MockSubPlan("count", 0, 0);
        MockAction action = new MockAction(false, state -> 0, state -> state.update("count", 0));
        AlgorithmUtils testSubject = new AlgorithmUtils();
        assertFalse(testSubject.isGoodAction(plan, action));
    }

    @Test
    public void testIsGoodActionFalseGreaterCount() {
        MockSubPlan plan = new MockSubPlan("count", 0, 0);
        MockAction action = new MockAction(false, state -> 0, state -> state.update("count", 1));
        AlgorithmUtils testSubject = new AlgorithmUtils();
        assertFalse(testSubject.isGoodAction(plan, action));
    }

    @Test
    public void testIsValidSubPlanTrue() {
        MockSubPlan plan = new MockSubPlan(0);
        AlgorithmUtils testSubject = new AlgorithmUtils();
        assertTrue(testSubject.isValidSubPlan(plan));
    }

    @Test
    public void testIsValidSubPlanFalse() {
        MockSubPlan plan = new MockSubPlan(1);
        AlgorithmUtils testSubject = new AlgorithmUtils();
        assertFalse(testSubject.isValidSubPlan(plan));
    }

    @Test
    public void testIsValidPlanTrue() {
        MockPlan plan = new MockPlan(new MockAction(true));
        AlgorithmUtils testSubject = new AlgorithmUtils();
        assertTrue(testSubject.isValidPlan(new State(), new MockGoal(0), plan));
    }

    @Test
    public void testIsValidFalseGoal() {
        MockPlan plan = new MockPlan(new MockAction(true));
        AlgorithmUtils testSubject = new AlgorithmUtils();
        assertFalse(testSubject.isValidPlan(new State(), new MockGoal(1), plan));
    }

    @Test
    public void testIsValidFalseActions() {
        MockPlan plan = new MockPlan(new MockAction(false));
        AlgorithmUtils testSubject = new AlgorithmUtils();
        assertFalse(testSubject.isValidPlan(new State(), new MockGoal(0), plan));
    }

    @Test
    public void testIsValidFalseGoalAndActions() {
        MockPlan plan = new MockPlan(new MockAction(false));
        AlgorithmUtils testSubject = new AlgorithmUtils();
        assertFalse(testSubject.isValidPlan(new State(), new MockGoal(1), plan));
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

    private class MockGoal extends Goal {
        private float deficit;

        MockGoal(float deficit) {
            super();
            this.deficit = deficit;
        }

        @Override
        public float getUnsatisfiedRequirementCost(State state) {
            return state.query("count");
        }

        @Override
        public float getDeficitCost(State state) {
            return deficit;
        }
    }

    private class MockAction extends Action {
        private boolean canExecute;

        MockAction(boolean canExecute) {
            super("", 0, state -> 0, state -> {});
            this.canExecute = canExecute;
        }

        MockAction(boolean canExecute, Precondition precondition, Postcondition postcondition) {
            super("", 0, precondition, postcondition);
            this.canExecute = canExecute;
        }

        @Override
        public boolean canExecute(State state) {
            return canExecute;
        }
    }

    private class MockSubPlan extends SubPlan {
        MockSubPlan(int deficit) {
            super(new MockState(), new MockGoal(deficit), new ArrayList<>(), 0);
        }

        MockSubPlan(String key, int value, int deficit) {
            super(new MockState(key, value), new MockGoal(deficit), new ArrayList<>(), 0);
        }
    }

    private class MockPlan extends Plan {
        MockPlan(MockAction... actions) {
            super(actions, 0);
        }
    }
}
