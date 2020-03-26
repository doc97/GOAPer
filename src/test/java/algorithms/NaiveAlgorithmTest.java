package algorithms;

import io.JSONLoader;
import io.ScenarioLoadFailedException;
import model.Action;
import model.Plan;
import model.Precondition;
import model.Scenario;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Daniel Riissanen on 18.3.2018.
 */
public class NaiveAlgorithmTest {

    @Test
    public void testGetBestPlan() {
        Plan[] plans = new Plan[3];
        plans[0] = new Plan(new Action[] { new MockAction(), new MockAction() }, true);
        plans[1] = new Plan(new Action[] { new MockAction(), new MockAction(), new MockAction() },true);
        plans[2] = new Plan(new Action[] { new MockAction() }, true);
        NaiveAlgorithm testSubject = new NaiveAlgorithm();
        Plan result = testSubject.getBestPlan(plans);
        assertEquals(plans[2], result);
    }

    @Test (timeout = 2000)
    public void testFormulatePlansSolveable() {
        JSONLoader loader = new JSONLoader();
        Scenario scenario = null;
        try {
            scenario = loader.loadScenarioFromFile("res/test/scenarioSolveable.json");
        } catch (ScenarioLoadFailedException e) {
            fail("Exception: " + e.getMessage());
        }

        NaiveAlgorithm testSubject = new NaiveAlgorithm();
        Plan[] results = testSubject.formulatePlans(scenario.start, scenario.goal, scenario.actions);
        assertEquals(1, results.length);
        Plan result = testSubject.getBestPlan(results);
        assertNotEquals(0, result.getActions().length);
    }

    @Test (timeout = 2000)
    public void testFormulatePlansUnsolveable() {
        JSONLoader loader = new JSONLoader();
        Scenario scenario = null;
        try {
            scenario = loader.loadScenarioFromFile("res/test/scenarioUnsolveable.json");
        } catch (ScenarioLoadFailedException e) {
            fail("Exception: " + e.getMessage());
        }

        NaiveAlgorithm testSubject = new NaiveAlgorithm();
        Plan[] results = testSubject.formulatePlans(scenario.start, scenario.goal, scenario.actions);
        assertEquals(0, results.length);
        Plan result = testSubject.getBestPlan(results);
        assertEquals(0, result.getActions().length);
    }

    @Test (timeout = 2000)
    public void testFormulatePlansLeastActions() {
        JSONLoader loader = new JSONLoader();
        Scenario scenario = null;
        try {
            scenario = loader.loadScenarioFromFile("res/test/scenarioTwoChoices.json");
        } catch (ScenarioLoadFailedException e) {
            fail("Exception: " + e.getMessage());
        }

        NaiveAlgorithm testSubject = new NaiveAlgorithm();
        Plan[] results = testSubject.formulatePlans(scenario.start, scenario.goal, scenario.actions);
        assertEquals(2, results.length);
        Plan result = testSubject.getBestPlan(results);
        assertEquals(1, result.getActions().length);
    }

    @Test (timeout = 2000)
    public void testFormulatePlansOptimization() {
        JSONLoader loader = new JSONLoader();
        Scenario scenario = null;
        try {
            scenario = loader.loadScenarioFromFile("res/test/scenarioOptimization.json");
        } catch (ScenarioLoadFailedException e) {
            fail("Exception: " + e.getMessage());
        }

        NaiveAlgorithm testSubject = new NaiveAlgorithm();
        Plan[] results = testSubject.formulatePlans(scenario.start, scenario.goal, scenario.actions);
        assertEquals(1, results.length);
        Plan result = testSubject.getBestPlan(results);
        assertEquals(10, result.getActions().length);
    }

    @Test (timeout = 2000)
    public void testFormulatePlansHeuristics() {
        JSONLoader loader = new JSONLoader();
        Scenario scenario = null;
        try {
            scenario = loader.loadScenarioFromFile("res/test/scenarioCostHeuristics.json");
        } catch (ScenarioLoadFailedException e) {
            fail("Exception: " + e.getMessage());
        }

        NaiveAlgorithm testSubject = new NaiveAlgorithm();
        Plan[] results = testSubject.formulatePlans(scenario.start, scenario.goal, scenario.actions);
        assertEquals(3, results.length);
        Plan result = testSubject.getBestPlan(results);
        assertEquals(20, result.getCost());
    }

    @Test (timeout = 2000)
    public void testFormulatePlanOne() {
        JSONLoader loader = new JSONLoader();
        Scenario scenario = null;
        try {
            scenario = loader.loadScenarioFromFile("res/scenario1.json");
        } catch (ScenarioLoadFailedException e) {
            fail("Exception: " + e.getMessage());
        }

        NaiveAlgorithm testSubject = new NaiveAlgorithm();
        Plan[] results = testSubject.formulatePlans(scenario.start, scenario.goal, scenario.actions);
        assertEquals(1, results.length);
        Plan result = testSubject.getBestPlan(results);
        assertEquals(0, result.getCost());
        assertEquals(2, result.getActions().length);
        assertTrue(result.isComplete());
    }

    @Test (timeout = 2000)
    public void testFormulatePlanTwo() {
        JSONLoader loader = new JSONLoader();
        Scenario scenario = null;
        try {
            scenario = loader.loadScenarioFromFile("res/scenario2.json");
        } catch (ScenarioLoadFailedException e) {
            fail("Exception: " + e.getMessage());
        }

        NaiveAlgorithm testSubject = new NaiveAlgorithm();
        Plan[] results = testSubject.formulatePlans(scenario.start, scenario.goal, scenario.actions);
        assertEquals(1, results.length);
        Plan result = testSubject.getBestPlan(results);
        assertEquals(0, result.getCost());
        assertEquals(3, result.getActions().length);
        assertTrue(result.isComplete());
    }

    @Test (timeout = 2000)
    public void testFormulatePlanResource() {
        JSONLoader loader = new JSONLoader();
        Scenario scenario = null;
        try {
            scenario = loader.loadScenarioFromFile("res/scenarioResource.json");
        } catch (ScenarioLoadFailedException e) {
            fail("Exception: " + e.getMessage());
        }

        NaiveAlgorithm testSubject = new NaiveAlgorithm();
        Plan[] results = testSubject.formulatePlans(scenario.start, scenario.goal, scenario.actions);
        assertEquals(1, results.length);
        Plan result = testSubject.getBestPlan(results);
        assertEquals(42, result.getCost());
        assertEquals(25, result.getActions().length);
        assertTrue(result.isComplete());
    }

    @Test (timeout = 2000)
    public void testFormulatePlanTraffic() {
        JSONLoader loader = new JSONLoader();
        Scenario scenario = null;
        try {
            scenario = loader.loadScenarioFromFile("res/scenarioTraffic.json");
        } catch (ScenarioLoadFailedException e) {
            fail("Exception: " + e.getMessage());
        }

        NaiveAlgorithm testSubject = new NaiveAlgorithm();
        Plan[] results = testSubject.formulatePlans(scenario.start, scenario.goal, scenario.actions);
        assertEquals(5, results.length);
        Plan result = testSubject.getBestPlan(results);
        assertEquals(40, result.getCost());
        assertEquals(2, result.getActions().length);
        assertTrue(result.isComplete());
    }

    private static class MockAction extends Action {
        MockAction() {
            super("", 0, new Precondition[] { state -> 0 }, state -> {}, state -> {});
        }
    }
}
