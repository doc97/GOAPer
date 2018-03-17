package model;

import io.JSONLoader;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by Daniel Riissanen on 17.3.2018.
 */
public class JSONLoaderTest {

    @Test
    public void testLoadScenarioNull() {
        JSONLoader testSubject = new JSONLoader();
        assertNull(testSubject.loadScenario(null));
    }

    @Test
    public void testLoadScenarioEmptyJSON() {
        JSONLoader testSubject = new JSONLoader();
        assertNull(testSubject.loadScenario(""));
    }

    @Test
    public void testLoadScenarioInvalidJSON() {
        JSONLoader testSubject = new JSONLoader();
        assertNull(testSubject.loadScenario("{"));
        assertNull(testSubject.loadScenario("}"));
    }

    @Test
    public void testLoadScenarioInvalidScenario() {
        String jsonString =
                "{\n" +
                        "a: \"b\"\n" +
                "}";
        JSONLoader testSubject = new JSONLoader();
        assertNull(testSubject.loadScenario(jsonString));
    }

    @Test
    public void testLoadScenarioFromFileInvalidFile() {
        JSONLoader testSubject = new JSONLoader();
        assertNull(testSubject.loadScenarioFromFile(""));
    }

    @Test
    public void testLoadScenarioFromFileValidFile() {
        JSONLoader testSubject = new JSONLoader();
        assertNotNull(testSubject.loadScenarioFromFile("res/testScenario.json"));
    }
}
