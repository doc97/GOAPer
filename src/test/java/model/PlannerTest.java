package model;

import algorithms.PlanningAlgorithm;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Daniel Riissanen on 2.4.2018.
 */
public class PlannerTest {

    private class MockAlgorithm implements PlanningAlgorithm {

        @Override
        public Plan getBestPlan(List<Plan> plans) {
            return new Plan();
        }

        @Override
        public List<Plan> formulatePlans(State start, Goal goal, Action[] actions) {
            return new ArrayList<>();
        }
    }

    @Test
    public void testConstructor() {
        Planner testSubject = new Planner();
        assertEquals(2, testSubject.getAlgorithmNames().size());
        assertEquals("default", testSubject.getAlgorithmName());
        assertEquals(0, testSubject.getAllPlans().size());
    }

    @Test
    public void testAddAlgorithm() {
        Planner testSubject = new Planner();
        testSubject.addAlgorithm("a", new MockAlgorithm());
        boolean containsName = false;
        for (String name : testSubject.getAlgorithmNames()) {
            if (name.equals("a")) {
                containsName = true;
                break;
            }
        }
        assertTrue(containsName);
    }

    @Test
    public void testUseAlgorithmExists() {
        Planner testSubject = new Planner();
        testSubject.useAlgorithm("naive");
        assertNotEquals("default", testSubject.getAlgorithmName());
    }

    @Test
    public void testUseAlgorithmNotExists() {
        Planner testSubject = new Planner();
        testSubject.useAlgorithm("null");
        assertNotEquals("null", testSubject.getAlgorithmName());
    }
}
