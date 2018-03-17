package io;

import org.junit.Test;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

/**
 * Created by Daniel Riissanen on 17.3.2018.
 */
public class JSONLoaderTest {

    @Test
    public void testLoadScenarioNull() {
        JSONLoader testSubject = new JSONLoader();
        try {
            testSubject.loadScenario(null);
            fail("Did not throw ScenarioLoadFailedException when null was given");
        } catch (ScenarioLoadFailedException ignored) {
        } catch (Exception e) {
            fail("Did not throw ScenarioLoadFailedException when null was given");
        }
    }

    @Test
    public void testLoadScenarioEmptyJSON() {
        JSONLoader testSubject = new JSONLoader();
        try {
            testSubject.loadScenario("");
            fail("Did not throw ScenarioLoadFailedException when empty JSON was given");
        } catch (ScenarioLoadFailedException ignored) {
        } catch (Exception e) {
            fail("Did not throw ScenarioLoadFailedException when empty JSON was given");
        }
    }

    @Test
    public void testLoadScenarioInvalidJSON() {
        JSONLoader testSubject = new JSONLoader();
        try {
            testSubject.loadScenario("{");
            fail("Did not throw ScenarioLoadFailedException when invalid JSON was given");
        } catch (ScenarioLoadFailedException ignored) {
        } catch (Exception e) {
            fail("Did not throw ScenarioLoadFailedException when invalid JSON was given");
        }

        try {
            assertNull(testSubject.loadScenario("}"));
            fail("Did not throw ScenarioLoadFailedException when invalid JSON was given");
        } catch (ScenarioLoadFailedException ignored) {
        } catch (Exception e) {
            fail("Did not throw ScenarioLoadFailedException when invalid JSON was given");
        }
    }

    @Test
    public void testLoadScenarioInvalidScenario() {
        String jsonString =
                "{\n" +
                        "a: \"b\"\n" +
                "}";
        JSONLoader testSubject = new JSONLoader();
        try {
            testSubject.loadScenario(jsonString);
            fail("Did not throw ScenarioLoadFailedException when invalid scenario was given");
        } catch (ScenarioLoadFailedException ignored) {
        } catch (Exception e) {
            fail("Did not throw ScenarioLoadFailedException when invalid scenario was given");
        }
    }

    @Test
    public void testLoadScenarioFromFileInvalidFile() {
        JSONLoader testSubject = new JSONLoader();
        try {
            testSubject.loadScenarioFromFile("");
            fail("Did not throw ScenarioLoadFailedException when no file was given");
        } catch (ScenarioLoadFailedException ignored) {
        } catch (Exception e) {
            fail("Did not throw ScenarioLoadFailedException when no file was given");
        }
    }

    @Test
    public void testLoadScenarioFromFileValidFile() {
        JSONLoader testSubject = new JSONLoader();
        try {
            testSubject.loadScenarioFromFile("res/testScenarioEmpty.json");
        } catch (ScenarioLoadFailedException e) {
            fail("Exception: " + e.getMessage());
        }
    }
}
