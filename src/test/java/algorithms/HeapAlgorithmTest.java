package algorithms;

import io.JSONLoader;
import io.ScenarioLoadFailedException;
import model.Plan;
import model.Scenario;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Daniel Riissanen on 18.3.2018.
 */
public class HeapAlgorithmTest {

    @Test
    public void testConstructorEmpty() {
        HeapAlgorithm testSubject = new HeapAlgorithm();
        assertNotNull(testSubject.getUtilities());
    }

    @Test
    public void testConstructorNull() {
        HeapAlgorithm testSubject = new HeapAlgorithm(null);
        assertNotNull(testSubject.getUtilities());
    }

    @Test
    public void testConstructor() {
        AlgorithmUtils utils = new AlgorithmUtils();
        HeapAlgorithm testSubject = new HeapAlgorithm(utils);
        assertEquals(utils, testSubject.getUtilities());
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

        HeapAlgorithm testSubject = new HeapAlgorithm();
        List<Plan> results = testSubject.formulatePlans(scenario.start, scenario.goal, scenario.actions);
        assertEquals(1, results.size());
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
        List<Plan> results = testSubject.formulatePlans(scenario.start, scenario.goal, scenario.actions);
        assertEquals(0, results.size());
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
        List<Plan> results = testSubject.formulatePlans(scenario.start, scenario.goal, scenario.actions);
        assertEquals(2, results.size());
        Plan result = testSubject.getBestPlan(results);
        assertEquals(1, result.getActions().length);
        assertTrue(result.isComplete());
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

        HeapAlgorithm testSubject = new HeapAlgorithm();
        List<Plan> results = testSubject.formulatePlans(scenario.start, scenario.goal, scenario.actions);
        assertEquals(1000, results.size());
        Plan result = testSubject.getBestPlan(results);
        assertEquals(10, result.getActions().length);
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
        List<Plan> results = testSubject.formulatePlans(scenario.start, scenario.goal, scenario.actions);
        assertEquals(3, results.size());
        Plan result = testSubject.getBestPlan(results);
        assertEquals(9, result.getCost());
        assertTrue(result.isComplete());
    }
}
