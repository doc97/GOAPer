package algorithms;

import io.JSONLoader;
import io.ScenarioLoadFailedException;
import model.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Daniel Riissanen on 18.3.2018.
 */
public class NaiveAlgorithmTest {

    @Test
    public void testConstructorEmpty() {
        NaiveAlgorithm testSubject = new NaiveAlgorithm();
        assertNotNull(testSubject.getUtilities());
    }

    @Test
    public void testConstructorNull() {
        NaiveAlgorithm testSubject = new NaiveAlgorithm(null);
        assertNotNull(testSubject.getUtilities());
    }

    @Test
    public void testConstructor() {
        AlgorithmUtils utils = new AlgorithmUtils();
        NaiveAlgorithm testSubject = new NaiveAlgorithm(utils);
        assertEquals(utils, testSubject.getUtilities());
    }

    @Test
    public void testGetBestPlan() {
        List<Plan> plans = new ArrayList<>();
        Plan plan1 = new Plan(new Action[] { new MockAction(), new MockAction() }, 0, true);
        Plan plan2 = new Plan(new Action[] { new MockAction(), new MockAction(), new MockAction() }, 0, true);
        Plan plan3 = new Plan(new Action[] { new MockAction() }, 0, true);
        plans.add(plan1);
        plans.add(plan2);
        plans.add(plan3);
        NaiveAlgorithm testSubject = new NaiveAlgorithm();
        Plan result = testSubject.getBestPlan(plans);
        assertEquals(plan3, result);
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
        List<Plan> results = testSubject.formulatePlans(scenario.start, scenario.goal, scenario.actions);
        assertEquals(1, results.size());
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
        List<Plan> results = testSubject.formulatePlans(scenario.start, scenario.goal, scenario.actions);
        assertEquals(0, results.size());
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
        List<Plan> results = testSubject.formulatePlans(scenario.start, scenario.goal, scenario.actions);
        assertEquals(2, results.size());
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
        List<Plan> results = testSubject.formulatePlans(scenario.start, scenario.goal, scenario.actions);
        assertEquals(10, results.size());
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
        List<Plan> results = testSubject.formulatePlans(scenario.start, scenario.goal, scenario.actions);
        assertEquals(3, results.size());
        Plan result = testSubject.getBestPlan(results);
        assertEquals(21, result.getCost());
    }

    private class MockAction extends Action {
        MockAction() {
            super("", 0, state -> 0, state -> {});
        }
    }
}
