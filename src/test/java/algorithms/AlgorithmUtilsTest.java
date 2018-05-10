package algorithms;

import model.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Daniel Riissanen on 4.4.2018.
 */
public class AlgorithmUtilsTest {

    @Test
    public void testIsValidSubPlanTrue() {
        MockSubPlan plan = new MockSubPlan(0);
        AlgorithmUtils testSubject = new AlgorithmUtils();
        assertTrue(testSubject.isValidSubPlan(plan, new State()));
    }

    @Test
    public void testIsValidSubPlanFalse() {
        MockSubPlan plan = new MockSubPlan(1);
        AlgorithmUtils testSubject = new AlgorithmUtils();
        assertFalse(testSubject.isValidSubPlan(plan, new State()));
    }

    @Test
    public void testIsUniqueSubPlanEmptyList() {
        AlgorithmUtils testSubject = new AlgorithmUtils();
        assertTrue(testSubject.isUniqueSubPlan(new MockSubPlan(0), new ArrayList<>()));
    }

    @Test
    public void testIsUniqueSubPlanTrue() {
        MockSubPlan testHelper = new MockSubPlan(0);
        List<SubPlan> plans = new ArrayList<>();
        plans.add(new MockSubPlan(0));
        AlgorithmUtils testSubject = new AlgorithmUtils();
        assertFalse(testSubject.isUniqueSubPlan(testHelper, plans));
    }

    @Test
    public void testIsUniqueSubPlanFalse() {
        MockSubPlan testHelper = new MockSubPlan(0);
        List<SubPlan> plans = new ArrayList<>();
        plans.add(testHelper);
        AlgorithmUtils testSubject = new AlgorithmUtils();
        assertFalse(testSubject.isUniqueSubPlan(testHelper, plans));
    }

    @Test
    public void testConvertToPlanNull() {
        AlgorithmUtils testSubject = new AlgorithmUtils();
        assertNotNull(testSubject.convertToPlan(null, false));
    }

    @Test
    public void testConvertToPlan() {
        List<Action> actions = new ArrayList<>();
        actions.add(new MockAction(true));
        actions.add(new MockAction(false));
        MockSubPlan plan = new MockSubPlan(7, actions);

        AlgorithmUtils testSubject = new AlgorithmUtils();
        Plan result = testSubject.convertToPlan(plan, false);

        assertEquals(plan.getActions().size(), result.getActions().length);
        for (int i = 0; i < result.getActions().length; ++i)
            assertEquals(plan.getActions().get(plan.getActions().size() - i - 1), result.getActions()[i]);
        assertFalse(result.isComplete());
    }

    private class MockState extends State {
        MockState() {
            super();
        }
    }

    private class MockGoal extends Goal {
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

    private class MockAction extends Action {
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

    private class MockSubPlan extends SubPlan {
        MockSubPlan(int deficit) {
            super(new MockState(), new MockGoal(deficit), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0);
        }

        MockSubPlan(int deficit, List<Action> actions) {
            super(new MockState(), new MockGoal(deficit), actions, new ArrayList<>(), new ArrayList<>(), 0);
        }
    }
}
