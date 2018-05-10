package algorithms;

import model.Action;
import model.Goal;
import model.State;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
        List<Action> actions = new ArrayList<>();
        SubPlan testSubject = new SubPlan(state, goal, actions, null, null, 7);
        assertEquals(state, testSubject.getState());
        assertEquals(goal, testSubject.getGoal());
        assertEquals(actions, testSubject.getActions());
        assertEquals(7, testSubject.getCost());
    }

    @Test
    public void testEqualsNull() {
        SubPlan testSubject = new SubPlan(new State(), new Goal(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0);
        assertNotEquals(null, testSubject);
    }

    @Test
    public void testEqualsNotInstanceOf() {
        SubPlan testSubject = new SubPlan(new State(), new Goal(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0);
        assertNotEquals(testSubject, new MockNotSubPlan());
    }

    @Test
    public void testEqualsDifferentCost() {
        SubPlan testHelper = new SubPlan(new State(), new Goal(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 1);
        SubPlan testSubject = new SubPlan(new State(), new Goal(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0);
        assertNotEquals(testHelper, testSubject);
    }

    @Test
    public void testEqualsDifferentState() {
        HashMap<String, Integer> keys = new HashMap<>();
        keys.put("a", 1);
        SubPlan testHelper = new SubPlan(new State(keys), new Goal(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0);
        SubPlan testSubject = new SubPlan(new State(), new Goal(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0);
        assertNotEquals(testHelper, testSubject);
    }

    @Test
    public void testEqualsDifferentGoal() {
        Goal goal = new Goal(state -> Math.abs(1 - state.query("a")));
        SubPlan testHelper = new SubPlan(new State(), goal, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0);
        SubPlan testSubject = new SubPlan(new State(), new Goal(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0);
        assertNotEquals(testHelper, testSubject);
    }

    private class MockNotSubPlan { }

}
