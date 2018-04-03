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
public class NaiveAlgorithmTest {

    @Test (timeout = 1000)
    public void testFormulatePlansSolveable() {
        JSONLoader loader = new JSONLoader();
        Scenario scenario = null;
        try {
            scenario = loader.loadScenarioFromFile("res/testScenarioSolveable.json");
        } catch (ScenarioLoadFailedException e) {
            fail("Exception: " + e.getMessage());
        }

        NaiveAlgorithm testSubject = new NaiveAlgorithm();
        List<Plan> results = testSubject.formulatePlans(scenario.start, scenario.goal, scenario.actions);
        assertEquals(1, results.size());
        Plan result = testSubject.getBestPlan(results);
        assertNotEquals(0, result.getActions().length);
    }

    @Test (timeout = 1000)
    public void testFormulatePlansUnsolveable() {
        JSONLoader loader = new JSONLoader();
        Scenario scenario = null;
        try {
            scenario = loader.loadScenarioFromFile("res/testScenarioUnsolveable.json");
        } catch (ScenarioLoadFailedException e) {
            fail("Exception: " + e.getMessage());
        }

        NaiveAlgorithm testSubject = new NaiveAlgorithm();
        List<Plan> results = testSubject.formulatePlans(scenario.start, scenario.goal, scenario.actions);
        assertEquals(0, results.size());
        Plan result = testSubject.getBestPlan(results);
        assertEquals(0, result.getActions().length);
    }

    @Test (timeout = 1000)
    public void testFormulatePlansLeastActions() {
        JSONLoader loader = new JSONLoader();
        Scenario scenario = null;
        try {
            scenario = loader.loadScenarioFromFile("res/testScenarioTwoChoices.json");
        } catch (ScenarioLoadFailedException e) {
            fail("Exception: " + e.getMessage());
        }

        NaiveAlgorithm testSubject = new NaiveAlgorithm();
        List<Plan> results = testSubject.formulatePlans(scenario.start, scenario.goal, scenario.actions);
        assertEquals(2, results.size());
        Plan result = testSubject.getBestPlan(results);
        assertEquals(1, result.getActions().length);
    }

    @Test (timeout = 1000)
    public void testFormulatePlansOptimization() {
        JSONLoader loader = new JSONLoader();
        Scenario scenario = null;
        try {
            scenario = loader.loadScenarioFromFile("res/testScenarioOptimization.json");
        } catch (ScenarioLoadFailedException e) {
            fail("Exception: " + e.getMessage());
        }

        NaiveAlgorithm testSubject = new NaiveAlgorithm();
        List<Plan> results = testSubject.formulatePlans(scenario.start, scenario.goal, scenario.actions);
        assertEquals(10, results.size());
        Plan result = testSubject.getBestPlan(results);
        assertEquals(10, result.getActions().length);
    }

    @Test (timeout = 1000)
    public void testFormulatePlansHeuristics() {
        JSONLoader loader = new JSONLoader();
        Scenario scenario = null;
        try {
            scenario = loader.loadScenarioFromFile("res/testScenarioCostHeuristics.json");
        } catch (ScenarioLoadFailedException e) {
            fail("Exception: " + e.getMessage());
        }

        NaiveAlgorithm testSubject = new NaiveAlgorithm();
        List<Plan> results = testSubject.formulatePlans(scenario.start, scenario.goal, scenario.actions);
        assertEquals(3, results.size());
        Plan result = testSubject.getBestPlan(results);
        assertEquals(21, result.getCost());
    }
}
