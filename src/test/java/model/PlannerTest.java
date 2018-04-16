package model;

import algorithms.AlgorithmUtils;
import algorithms.PlanningAlgorithm;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Daniel Riissanen on 2.4.2018.
 */
public class PlannerTest {

    private class MockAlgorithm implements PlanningAlgorithm {

        private Plan bestPlan;
        private ArrayList<Plan> plans;

        public MockAlgorithm(Plan bestPlan, ArrayList<Plan> plans) {
            this.bestPlan = bestPlan;
            this.plans = plans;
        }

        public MockAlgorithm() {
            bestPlan = new Plan();
            plans = new ArrayList<>();
        }

        @Override
        public Plan getBestPlan(List<Plan> plans) {
            return bestPlan;
        }

        @Override
        public List<Plan> formulatePlans(State start, Goal goal, Action[] actions) {
            return plans;
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
    public void testConstructorUtilities() {
        AlgorithmUtils utils = new AlgorithmUtils();
        Planner testSubject = new Planner(utils);
        assertEquals(testSubject.getAlgorithmUtils(), utils);
    }

    @Test
    public void testConstructorUtilitiesNull() {
        Planner testSubject = new Planner(null);
        assertNotNull(testSubject.getAlgorithmUtils());
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

    @Test
    public void testFormulateAllPlans() {
        ArrayList<Plan> plans = new ArrayList<>();
        Plan plan = new Plan();
        Planner testSubject = new Planner();
        testSubject.addAlgorithm("test", new MockAlgorithm(plan, plans));
        testSubject.useAlgorithm("test");
        testSubject.formulateAllPlans(null, null, null);
        assertEquals(testSubject.getBestPlan(), plan);
        assertEquals(testSubject.getAllPlans(), plans);
    }
}
