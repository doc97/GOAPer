package io;

import model.Action;
import model.Postcondition;
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
        keyA.value = 1;
        JSONStateKey keyB = new JSONStateKey();
        keyB.key = "b";
        keyB.value = 0;
        state.keys = new JSONStateKey[] { keyA, keyB };

        JSONConverter testSubject = new JSONConverter();
        State result = null;
        try { result = testSubject.convertState(state); }
        catch (ScenarioLoadFailedException e) { fail("Exception: " + e.getMessage()); }
        assertTrue(result.queryBoolean("a"));
        assertFalse(result.queryBoolean("b"));
    }

    @Test
    public void testConvertPostconditionDefaultOperation() {
        JSONOperation addOperation = new JSONOperation();
        addOperation.key = "a";
        addOperation.value = 2;
        addOperation.opCode = '\u0000';
        JSONOperation[] operations = new JSONOperation[] { addOperation };

        JSONConverter testSubject = new JSONConverter();
        Postcondition result = null;
        try { result = testSubject.convertPostcondition(operations); }
        catch (ScenarioLoadFailedException e) { fail("Exception: " + e.getMessage()); }

        State testState = new State();
        testState.addKey("a", 1);
        result.activate(testState);
        assertEquals(2, testState.query("a"));
    }

    @Test
    public void testConvertPostconditionAddOperation() {
        JSONOperation addOperation = new JSONOperation();
        addOperation.key = "a";
        addOperation.value = 2;
        addOperation.opCode = '+';
        JSONOperation[] operations = new JSONOperation[] { addOperation };

        JSONConverter testSubject = new JSONConverter();
        Postcondition result = null;
        try { result = testSubject.convertPostcondition(operations); }
        catch (ScenarioLoadFailedException e) { fail("Exception: " + e.getMessage()); }

        State testState = new State();
        testState.addKey("a", 1);
        result.activate(testState);
        assertEquals(3, testState.query("a"));
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
        state.addKey("pre", 1);
        JSONStateKey preKey = new JSONStateKey();
        preKey.key = "pre";
        preKey.value = 1;
        JSONOperation postOperation = new JSONOperation();
        postOperation.key = "post";
        postOperation.value = 1;
        postOperation.opCode = '0';
        JSONAction action = new JSONAction();
        action.name = "a";
        action.precondition = new JSONState();
        action.precondition.keys = new JSONStateKey[] { preKey };
        action.postcondition = new JSONOperation[] { postOperation };

        JSONConverter testSubject = new JSONConverter();
        Action result = null;
        try { result = testSubject.convertAction(action); }
        catch (ScenarioLoadFailedException e) { fail("Exception: " + e.getMessage()); }
        assertTrue(result.canExecute(state));
        result.execute(state);
        assertTrue(state.queryBoolean("post"));
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
        action.postcondition = new JSONOperation[0];

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
