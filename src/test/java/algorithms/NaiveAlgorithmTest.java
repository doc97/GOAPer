package algorithms;

import io.JSONLoader;
import io.ScenarioLoadFailedException;
import model.Action;
import model.Scenario;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Daniel Riissanen on 18.3.2018.
 */
public class NaiveAlgorithmTest {

    @Test
    public void testFormulatePlanSolveable() {
        JSONLoader loader = new JSONLoader();
        Scenario scenario = null;
        try {
            scenario = loader.loadScenarioFromFile("res/testScenarioSolveable.json");
        } catch (ScenarioLoadFailedException e) {
            fail("Exception: " + e.getMessage());
        }

        NaiveAlgorithm testSubject = new NaiveAlgorithm();
        Action[] result = testSubject.formulatePlan(scenario.start, scenario.goal, scenario.actions);
        assertNotEquals(0, result.length);
    }

    @Test
    public void testFormulatePlanUnsolveable() {
        JSONLoader loader = new JSONLoader();
        Scenario scenario = null;
        try {
            scenario = loader.loadScenarioFromFile("res/testScenarioUnsolveable.json");
        } catch (ScenarioLoadFailedException e) {
            fail("Exception: " + e.getMessage());
        }

        NaiveAlgorithm testSubject = new NaiveAlgorithm();
        Action[] result = testSubject.formulatePlan(scenario.start, scenario.goal, scenario.actions);
        assertEquals(0, result.length);
    }
}
