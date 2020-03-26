package algorithms;

import io.JSONLoader;
import io.ScenarioLoadFailedException;
import model.Plan;
import model.Scenario;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Daniel Riissanen on 18.3.2018.
 */
public class HeapAlgorithmTest {

    @Test (timeout = 2000)
    public void testFormulatePlansSolveable() {
        JSONLoader loader = new JSONLoader();
        Scenario scenario = null;
        try {
            scenario = loader.loadScenarioFromFile("res/test/scenarioSolveable.json");
        } catch (ScenarioLoadFailedException e) {
            fail("Exception: " + e.getMessage());
        }

        HeapAlgorithm testSubject = new HeapAlgorithm();
        Plan[] results = testSubject.formulatePlans(scenario.start, scenario.goal, scenario.actions);
        assertEquals(1, results.length);
        Plan result = testSubject.getBestPlan(results);
        assertTrue(result.isComplete());
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

        HeapAlgorithm testSubject = new HeapAlgorithm();
        Plan[] results = testSubject.formulatePlans(scenario.start, scenario.goal, scenario.actions);
        assertEquals(0, results.length);
        Plan result = testSubject.getBestPlan(results);
        assertFalse(result.isComplete());
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

        HeapAlgorithm testSubject = new HeapAlgorithm();
        Plan[] results = testSubject.formulatePlans(scenario.start, scenario.goal, scenario.actions);
        assertEquals(2, results.length);
        Plan result = testSubject.getBestPlan(results);
        assertEquals(1, result.getActions().length);
        assertTrue(result.isComplete());
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

        HeapAlgorithm testSubject = new HeapAlgorithm();
        Plan[] results = testSubject.formulatePlans(scenario.start, scenario.goal, scenario.actions);
        assertEquals(3, results.length);
        Plan result = testSubject.getBestPlan(results);
        assertEquals(7, result.getCost());
        assertTrue(result.isComplete());
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

        HeapAlgorithm testSubject = new HeapAlgorithm();
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

        HeapAlgorithm testSubject = new HeapAlgorithm();
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

        HeapAlgorithm testSubject = new HeapAlgorithm();
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

        HeapAlgorithm testSubject = new HeapAlgorithm();
        Plan[] results = testSubject.formulatePlans(scenario.start, scenario.goal, scenario.actions);
        assertEquals(17, results.length);
        Plan result = testSubject.getBestPlan(results);
        assertEquals(21, result.getCost());
        assertEquals(6, result.getActions().length);
        assertTrue(result.isComplete());
    }
}
