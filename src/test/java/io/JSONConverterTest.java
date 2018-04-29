package io;

import model.*;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Daniel Riissanen on 17.3.2018.
 */
public class JSONConverterTest {

    @Test
    public void testConvertStartNull() {
        JSONConverter testSubject = new JSONConverter();
        try {
            testSubject.convertStart(null);
            fail("Failed to handle null argument");
        } catch (ScenarioLoadFailedException ignored) {
        } catch (NullPointerException npe) {
            fail("Failed to handle null argument");
        }
    }

    @Test
    public void testConvertStartNullContent() {
        JSONStateKey stateKey = new JSONStateKey();
        stateKey.key = null;

        JSONStateKey[] start1 = null;
        JSONStateKey[] start2 = new JSONStateKey[] { null };
        JSONStateKey[] start3 = new JSONStateKey[] { stateKey };

        JSONConverter testSubject = new JSONConverter();
        try {
            testSubject.convertStart(start1);
            fail("Failed to handle null variables in argument");
        } catch (ScenarioLoadFailedException ignored) {
        } catch (NullPointerException npe) {
            fail("Failed to handle null variables in argument");
        }

        try {
            testSubject.convertStart(start2);
            fail("Failed to handle null variables in argument");
        } catch (ScenarioLoadFailedException ignored) {
        } catch (NullPointerException npe) {
            fail("Failed to handle null variables in argument");
        }

        try {
            testSubject.convertStart(start3);
            fail("Failed to handle null variables in argument");
        } catch (ScenarioLoadFailedException ignored) {
        } catch (NullPointerException npe) {
            fail("Failed to handle null variables in argument");
        }
    }

    @Test
    public void testConvertStartValidInput() {
        JSONStateKey keyA = new JSONStateKey();
        keyA.key = "a";
        keyA.value = 1;
        JSONStateKey keyB = new JSONStateKey();
        keyB.key = "b";
        keyB.value = 0;
        JSONStateKey[] start = new JSONStateKey[] { keyA, keyB };

        JSONConverter testSubject = new JSONConverter();
        State result = null;
        try { result = testSubject.convertStart(start); }
        catch (ScenarioLoadFailedException e) { fail("Exception: " + e.getMessage()); }
        assertTrue(result.queryBoolean("a"));
        assertFalse(result.queryBoolean("b"));
    }

    @Test
    public void testConvertPreconditionNull() {
        JSONConverter testSubject = new JSONConverter();
        try {
            testSubject.convertPrecondition(null);
            fail("Failed to handle null variables in argument");
        } catch (ScenarioLoadFailedException ignored) {
        } catch (NullPointerException npe) {
            fail("Failed to handle null variables in argument");
        }
    }

    @Test
    public void testConvertPreconditionDefaultRequirement() {
        JSONRequirement equalRequirement = new JSONRequirement();
        equalRequirement.key = "a";
        equalRequirement.value = 2;
        equalRequirement.reqCode = "";
        JSONRequirement[] requirements = new JSONRequirement[] { equalRequirement };

        JSONConverter testSubject = new JSONConverter();
        Precondition result = null;
        try { result = testSubject.convertPrecondition(requirements); }
        catch (ScenarioLoadFailedException e) { fail("Exception: " + e.getMessage()); }

        State testState = new State();
        testState.addKey("a", 2);
        assertEquals(0, result.getDeficitCost(testState), 0.0001f);
    }

    @Test
    public void testConvertPostconditionNull() {
        JSONConverter testSubject = new JSONConverter();
        try {
            testSubject.convertPostcondition(null);
            fail("Failed to handle null variables in argument");
        } catch (ScenarioLoadFailedException ignored) {
        } catch (NullPointerException npe) {
            fail("Failed to handle null variables in argument");
        }
    }

    @Test
    public void testConvertPostconditionDefaultOperation() {
        JSONOperator addOperation = new JSONOperator();
        addOperation.key = "a";
        addOperation.value = 2;
        addOperation.opCode = "";
        JSONOperator[] operations = new JSONOperator[] { addOperation };

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
        JSONOperator addOperation = new JSONOperator();
        addOperation.key = "a";
        addOperation.value = 2;
        addOperation.opCode = "+";
        JSONOperator[] operations = new JSONOperator[] { addOperation };

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
    public void testConvertGoalNull() {
        JSONConverter testSubject = new JSONConverter();
        try {
            testSubject.convertGoal(null);
            fail("Failed to handle null variables in argument");
        } catch (ScenarioLoadFailedException ignored) {
        } catch (NullPointerException npe) {
            fail("Failed to handle null variables in argument");
        }
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
        JSONRequirement preRequirement = new JSONRequirement();
        preRequirement.key = "pre";
        preRequirement.value = 1;
        preRequirement.reqCode = "";
        JSONOperator postOperation = new JSONOperator();
        postOperation.key = "post";
        postOperation.value = 1;
        postOperation.opCode = "";
        JSONAction action = new JSONAction();
        action.name = "a";
        action.precondition = new JSONRequirement[] { preRequirement};
        action.postcondition = new JSONOperator[] { postOperation };

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
        JSONScenario scenario = new JSONScenario();
        scenario.start = new JSONStateKey[0];
        scenario.goal = new JSONRequirement[0];
        scenario.actions = new JSONAction[] { null };

        JSONConverter testSubject = new JSONConverter();
        Scenario result = null;
        try { result = testSubject.convertScenario(scenario); }
        catch (ScenarioLoadFailedException e) { fail("Exception: " + e.getMessage()); }
        assertEquals(0, result.actions.length);
    }

    @Test
    public void testConvertScenario() {
        JSONAction action = new JSONAction();
        action.name = "a";
        action.precondition = new JSONRequirement[0];
        action.postcondition = new JSONOperator[0];

        JSONScenario scenario = new JSONScenario();
        scenario.start = new JSONStateKey[0];
        scenario.goal = new JSONRequirement[0];
        scenario.actions = new JSONAction[] { action, action, action };

        JSONConverter testSubject = new JSONConverter();
        Scenario result = null;
        try { result = testSubject.convertScenario(scenario); }
        catch (ScenarioLoadFailedException e) { fail("Exception: " + e.getMessage()); }
        assertEquals(3, result.actions.length);
    }

    @Test
    public void testAddOperation() {
        JSONConverter testSubject = new JSONConverter();
        testSubject.addOperator("a", (a, b) -> 0);
        assertTrue(testSubject.isOpCodeReserved("a"));
    }

    @Test
    public void testAddRequirement() {
        JSONConverter testSubject = new JSONConverter();
        testSubject.addRequirement("a", (a, b) -> 0);
        assertTrue(testSubject.isReqCodeReserved("a"));
    }

    @Test
    public void testRemoveOperation() {
        JSONConverter testSubject = new JSONConverter();
        testSubject.addOperator("a", (a, b) -> 0);
        testSubject.removeOperator("a");
        assertFalse(testSubject.isOpCodeReserved("a"));
    }

    @Test
    public void testRemoveRequirement() {
        JSONConverter testSubject = new JSONConverter();
        testSubject.addRequirement("a", (a, b) -> 0);
        testSubject.removeRequirement("a");
        assertFalse(testSubject.isReqCodeReserved("a"));
    }
}
