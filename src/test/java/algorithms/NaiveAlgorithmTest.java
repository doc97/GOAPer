package algorithms;

import io.JSONLoader;
import model.Action;
import model.Scenario;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by Daniel Riissanen on 18.3.2018.
 */
public class NaiveAlgorithmTest {

    @Test
    public void testFormulatePlanSolveable() {
        JSONLoader loader = new JSONLoader();
        Scenario scenario = loader.loadScenarioFromFile("res/testScenarioSolveable.json");

        NaiveAlgorithm testSubject = new NaiveAlgorithm();
        Action[] result = testSubject.formulatePlan(scenario.start, scenario.goal, scenario.actions);
        assertNotEquals(0, result.length);
    }

    @Test
    public void testFormulatePlanUnsolveable() {
        JSONLoader loader = new JSONLoader();
        Scenario scenario = loader.loadScenarioFromFile("res/testScenarioUnsolveable.json");

        NaiveAlgorithm testSubject = new NaiveAlgorithm();
        Action[] result = testSubject.formulatePlan(scenario.start, scenario.goal, scenario.actions);
        assertEquals(0, result.length);
    }
}
