package model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Daniel Riissanen on 16.3.2018.
 */
public class ActionTest {

    @Test
    public void testCanExecuteFalse() {
        Precondition preFalse = state -> state.query("a") == 1;
        Postcondition postNone = state -> {};
        Action testSubject = new Action("", 0, preFalse, postNone);
        assertFalse(testSubject.canExecute(new State()));
    }

    @Test
    public void testCanExecuteAllTrueState() {
        Precondition precondition = state -> state.query("a") == 1 &&
                state.query("b") == 1 &&
                state.query("c") == 1;
        Postcondition postNone = state -> {};
        State state = new State();
        state.addKey("a", 1);
        state.addKey("b", 1);
        state.addKey("c", 1);
        Action testSubject = new Action("", 0, precondition, postNone);
        assertTrue(testSubject.canExecute(state));
    }

    @Test
    public void testCanExecuteAllFalseState() {
        Precondition precondition = state -> state.query("a") == 0 &&
                state.query("b") == 0 &&
                state.query("c") == 0;
        Postcondition postNone = state -> {};
        State state = new State();
        state.addKey("a", 0);
        state.addKey("b", 0);
        state.addKey("c", 0);
        Action testSubject = new Action("", 0, precondition, postNone);
        assertTrue(testSubject.canExecute(state));
    }

    @Test
    public void testCanExecuteMixedState() {
        Precondition precondition = state -> state.query("a") == 0 &&
                state.query("b") == 1 &&
                state.query("c") == 0;
        Postcondition postNone = state -> {};
        State state = new State();
        state.addKey("a", 0);
        state.addKey("b", 1);
        state.addKey("c", 0);
        Action testSubject = new Action("", 0, precondition, postNone);
        assertTrue(testSubject.canExecute(state));
    }

    @Test
    public void testExecuteEffectNone() {
        Precondition precondition = state -> true;
        Postcondition postNone = state -> {};
        State state = new State();
        Action testSubject = new Action("", 0, precondition, postNone);
        testSubject.execute(state);
        assertEquals(0, state.getKeys().size());
    }

    @Test
    public void testExecuteEffectReverse() {
        Precondition precondition = state -> state.query("a") == 0 && state.query("b") == 1;
        Postcondition postReverse = state -> {
            for (String key : state.getKeys()) {
                state.apply(key, state.queryBoolean(key) ? 0 : 1);
            }
        };
        State state = new State();
        state.addKey("a", 0);
        state.addKey("b", 1);
        Action testSubject = new Action("", 0, precondition, postReverse);
        testSubject.execute(state);
        assertEquals(2, state.getKeys().size());
        assertEquals(1, state.query("a"));
        assertEquals(0, state.query("b"));
    }

    @Test
    public void testExecuteEffectOne() {
        Precondition precondition = state -> state.query("a") == 0 &&
                state.query("b") == 1 &&
                state.query("c") == 0;
        Postcondition postOne = state -> state.apply("a", state.queryBoolean("a") ? 0 : 1);
        State state = new State();
        state.addKey("a", 0);
        state.addKey("b", 1);
        state.addKey("c", 0);
        Action testSubject = new Action("", 0, precondition, postOne);
        testSubject.execute(state);
        assertEquals(3, state.getKeys().size());
        assertEquals(1, state.query("a"));
        assertEquals(1, state.query("b"));
        assertEquals(0, state.query("c"));
    }

    @Test
    public void testGetPrecondition() {
        Precondition precondition = state -> true;
        Action testSubject = new Action("", 0, precondition, null);
        assertEquals(precondition, testSubject.getPrecondition());
    }

    @Test
    public void testGetPostcondition() {
        Postcondition postcondition = state -> { };
        Action testSubject = new Action("", 0, null, postcondition);
        assertEquals(postcondition, testSubject.getPostcondition());
    }

    @Test
    public void testGetCost() {
        int cost = 1;
        Action testSubject = new Action("", cost, null, null);
        assertEquals(cost, testSubject.getCost());
    }

    @Test
    public void testGetName() {
        String name = "a";
        Action testSubject = new Action(name, 0, null, null);
        assertEquals(name, testSubject.getName());
    }

    @Test
    public void testToString() {
        String name = "a";
        Action testSubject = new Action(name, 0, null, null);
        assertEquals(name, testSubject.toString());
    }
}
