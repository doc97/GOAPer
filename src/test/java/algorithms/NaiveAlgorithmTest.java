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
    public void testFormulatePlanSolveable() {
        JSONLoader loader = new JSONLoader();
        Scenario scenario = null;
        try {
            scenario = loader.loadScenarioFromFile("res/testScenarioSolveable.json");
        } catch (ScenarioLoadFailedException e) {
            fail("Exception: " + e.getMessage());
        }

        NaiveAlgorithm testSubject = new NaiveAlgorithm();
        List<Plan> results = testSubject.formulatePlans(scenario.start, scenario.goal, scenario.actions);
        Plan result = testSubject.getBestPlan(results);
        assertNotEquals(0, result.getActions().length);
    }

    @Test (timeout = 1000)
    public void testFormulatePlanUnsolveable() {
        JSONLoader loader = new JSONLoader();
        Scenario scenario = null;
        try {
            scenario = loader.loadScenarioFromFile("res/testScenarioUnsolveable.json");
        } catch (ScenarioLoadFailedException e) {
            fail("Exception: " + e.getMessage());
        }

        NaiveAlgorithm testSubject = new NaiveAlgorithm();
        List<Plan> results = testSubject.formulatePlans(scenario.start, scenario.goal, scenario.actions);
        Plan result = testSubject.getBestPlan(results);
        assertEquals(0, result.getActions().length);
    }

    @Test (timeout = 1000)
    public void testFormulatePlanLeastActions() {
        JSONLoader loader = new JSONLoader();
        Scenario scenario = null;
        try {
            scenario = loader.loadScenarioFromFile("res/testScenarioTwoChoices.json");
        } catch (ScenarioLoadFailedException e) {
            fail("Exception: " + e.getMessage());
        }

        NaiveAlgorithm testSubject = new NaiveAlgorithm();
        List<Plan> results = testSubject.formulatePlans(scenario.start, scenario.goal, scenario.actions);
        Plan result = testSubject.getBestPlan(results);
        assertEquals(1, result.getActions().length);
    }

    @Test (timeout = 1000)
    public void testFormulatePlanOptimization() {
        JSONLoader loader = new JSONLoader();
        Scenario scenario = null;
        try {
            scenario = loader.loadScenarioFromFile("res/testScenarioOptimization.json");
        } catch (ScenarioLoadFailedException e) {
            fail("Exception: " + e.getMessage());
        }

        NaiveAlgorithm testSubject = new NaiveAlgorithm();
        List<Plan> results = testSubject.formulatePlans(scenario.start, scenario.goal, scenario.actions);
        Plan result = testSubject.getBestPlan(results);
        assertEquals(10, result.getActions().length);
    }
}
