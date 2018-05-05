package io;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Daniel Riissanen on 5.5.2018.
 */
public class JSONScenarioTest {
    @Test
    public void testIsEmptyDefault() {
        JSONScenario testSubject = new JSONScenario();
        assertTrue(testSubject.isEmpty());
    }

    @Test
    public void testIsEmptyStartNotEmpty() {
        JSONScenario testSubject = new JSONScenario();
        testSubject.start = new JSONStateKey[1];
        assertFalse(testSubject.isEmpty());
    }

    @Test
    public void testIsEmptyGoalNotEmpty() {
        JSONScenario testSubject = new JSONScenario();
        testSubject.goal = new JSONRequirement[1];
        assertFalse(testSubject.isEmpty());
    }

    @Test
    public void testIsEmptyActionsNotEmpty() {
        JSONScenario testSubject = new JSONScenario();
        testSubject.actions = new JSONAction[1];
        assertFalse(testSubject.isEmpty());
    }
}
