package algorithms;

import model.Goal;
import model.Precondition;
import model.State;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertNotEquals;

/**
 * Created by Daniel Riissanen on 2.4.2018.
 */
public class SubPlanTest {

    private class MockNotSubPlan { }

    @Test
    public void testEqualsNull() {
        SubPlan testSubject = new SubPlan(new State(), new Goal(), new ArrayList<>(), 0);
        assertNotEquals(null, testSubject);
    }

    @Test
    public void testEqualsNotInstanceOf() {
        SubPlan testSubject = new SubPlan(new State(), new Goal(), new ArrayList<>(), 0);
        assertNotEquals(testSubject, new MockNotSubPlan());
    }

    @Test
    public void testEqualsDifferentCost() {
        SubPlan testHelper = new SubPlan(new State(), new Goal(), new ArrayList<>(), 1);
        SubPlan testSubject = new SubPlan(new State(), new Goal(), new ArrayList<>(), 0);
        assertNotEquals(testHelper, testSubject);
    }

    @Test
    public void testEqualsDifferentState() {
        HashMap<String, Integer> keys = new HashMap<>();
        keys.put("a", 1);
        SubPlan testHelper = new SubPlan(new State(keys), new Goal(), new ArrayList<>(), 0);
        SubPlan testSubject = new SubPlan(new State(), new Goal(), new ArrayList<>(), 0);
        assertNotEquals(testHelper, testSubject);
    }

    @Test
    public void testEqualsDifferentGoal() {
        List<Precondition> requirements = new ArrayList<>();
        requirements.add(state -> state.query("a") == 1);
        SubPlan testHelper = new SubPlan(new State(), new Goal(requirements), new ArrayList<>(), 0);
        SubPlan testSubject = new SubPlan(new State(), new Goal(), new ArrayList<>(), 0);
        assertNotEquals(testHelper, testSubject);
    }
}
