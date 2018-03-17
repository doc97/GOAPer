package io;

import model.Action;
import model.Scenario;
import model.State;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Daniel Riissanen on 17.3.2018.
 */
public class JSONConverterTest {

    @Test
    public void testConvertStateNull() {
        JSONConverter testSubject = new JSONConverter();
        try {
            testSubject.convertState(null);
            fail("Failed to handle null argument");
        } catch (ScenarioLoadFailedException ignored) {
        } catch (NullPointerException npe) {
            fail("Failed to handle null argument");
        }
    }

    @Test
    public void testConvertStateNullContent() {
        JSONState state1 = new JSONState();
        JSONState state2 = new JSONState();
        JSONState state3 = new JSONState();

        JSONStateKey stateKey = new JSONStateKey();
        stateKey.key = null;

        state2.keys = new JSONStateKey[] { null };
        state3.keys = new JSONStateKey[] { stateKey };

        JSONConverter testSubject = new JSONConverter();
        try {
            testSubject.convertState(state1);
            fail("Failed to handle null variables in argument");
        } catch (ScenarioLoadFailedException ignored) {
        } catch (NullPointerException npe) {
            fail("Failed to handle null variables in argument");
        }

        try {
            testSubject.convertState(state2);
            fail("Failed to handle null variables in argument");
        } catch (ScenarioLoadFailedException ignored) {
        } catch (NullPointerException npe) {
            fail("Failed to handle null variables in argument");
        }

        try {
            testSubject.convertState(state3);
            fail("Failed to handle null variables in argument");
        } catch (ScenarioLoadFailedException ignored) {
        } catch (NullPointerException npe) {
            fail("Failed to handle null variables in argument");
        }
    }

    @Test
    public void testConvertStateValidInput() {
        JSONState state = new JSONState();
        JSONStateKey keyA = new JSONStateKey();
        keyA.key = "a";
        keyA.value = true;
        JSONStateKey keyB = new JSONStateKey();
        keyB.key = "b";
        keyB.value = false;
        state.keys = new JSONStateKey[] { keyA, keyB };

        JSONConverter testSubject = new JSONConverter();
        State result = null;
        try { result = testSubject.convertState(state); }
        catch (ScenarioLoadFailedException e) { fail("Exception: " + e.getMessage()); }
        assertEquals(true, result.query("a"));
        assertEquals(false, result.query("b"));
    }

    @Test
    public void testConvertActionNull() {
        JSONConverter testSubject = new JSONConverter();
        try {
            testSubject.convertAction(null);
            fail("Failed to handle null argument");
        } catch (ScenarioLoadFailedException ignored) {
        } catch (NullPointerException npe) {
            fail("Failed to handle null argument");
        }
    }

    @Test
    public void testConvertActionNullContent() {
        JSONAction action = new JSONAction();
        JSONConverter testSubject = new JSONConverter();
        try {
            testSubject.convertAction(action);
            fail("Failed to handle null argument");
        } catch (ScenarioLoadFailedException ignored) {
        } catch (NullPointerException npe) {
            fail("Failed to handle null argument");
        }
    }

    @Test
    public void testConvertActionValidInput() {
        State state = new State();
        state.addKey("pre", true);
        JSONStateKey preKey = new JSONStateKey();
        preKey.key = "pre";
        preKey.value = true;
        JSONStateKey postKey = new JSONStateKey();
        postKey.key = "post";
        postKey.value = true;
        JSONAction action = new JSONAction();
        action.name = "a";
        action.precondition = new JSONState();
        action.precondition.keys = new JSONStateKey[] { preKey };
        action.postcondition = new JSONState();
        action.postcondition.keys = new JSONStateKey[] { postKey };

        JSONConverter testSubject = new JSONConverter();
        Action result = null;
        try { result = testSubject.convertAction(action); }
        catch (ScenarioLoadFailedException e) { fail("Exception: " + e.getMessage()); }
        assertTrue(result.canExecute(state));
        result.execute(state);
        assertTrue(state.query("post"));
    }

    @Test
    public void testConvertScenarioNullActions() {
        JSONState state = new JSONState();
        state.keys = new JSONStateKey[0];

        JSONScenario scenario = new JSONScenario();
        scenario.start = state;
        scenario.goal = state;
        scenario.actions = new JSONAction[] { null };

        JSONConverter testSubject = new JSONConverter();
        Scenario result = null;
        try { result = testSubject.convertScenario(scenario); }
        catch (ScenarioLoadFailedException e) { fail("Exception: " + e.getMessage()); }
        assertEquals(0, result.actions.length);
    }

    @Test
    public void testConvertScenario() {
        JSONState state = new JSONState();
        state.keys = new JSONStateKey[0];

        JSONAction action = new JSONAction();
        action.name = "a";
        action.precondition = new JSONState();
        action.precondition.keys = new JSONStateKey[0];
        action.postcondition = new JSONState();
        action.postcondition.keys = new JSONStateKey[0];

        JSONScenario scenario = new JSONScenario();
        scenario.start = state;
        scenario.goal = state;
        scenario.actions = new JSONAction[] { action, action, action };

        JSONConverter testSubject = new JSONConverter();
        Scenario result = null;
        try { result = testSubject.convertScenario(scenario); }
        catch (ScenarioLoadFailedException e) { fail("Exception: " + e.getMessage()); }
        assertEquals(3, result.actions.length);
    }
}
