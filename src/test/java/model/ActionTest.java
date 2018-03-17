package model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Daniel Riissanen on 16.3.2018.
 */
public class ActionTest {

    private static final Postcondition POSTCONDITION_NONE = state -> {};
    private static final Postcondition POSTCONDITION_ONE = state -> state.apply("a", !state.query("a"));
    private static final Postcondition POSTCONDITION_REVERSE = state -> {
        for (String key : state.getKeys()) {
            state.apply(key, !state.query(key));
        }
    };

    @Test
    public void testCanExecuteFalse() {
        Precondition preFalse = () -> {
            State state = new State();
            state.addKey("a", true);
            return state;
        };
        Action testSubject = new Action("", preFalse, POSTCONDITION_NONE);
        assertFalse(testSubject.canExecute(new State()));
    }

    @Test
    public void testCanExecuteAllTrueState() {
        Precondition precondition = () -> {
            State state = new State();
            state.addKey("a", true);
            state.addKey("b", true);
            state.addKey("c", true);
            return state;
        };
        State state = new State();
        state.addKey("a", true);
        state.addKey("b", true);
        state.addKey("c", true);
        Action testSubject = new Action("", precondition, POSTCONDITION_NONE);
        assertTrue(testSubject.canExecute(state));
    }

    @Test
    public void testCanExecuteAllFalseState() {
        Precondition precondition = () -> {
            State state = new State();
            state.addKey("a", false);
            state.addKey("b", false);
            state.addKey("c", false);
            return state;
        };
        State state = new State();
        state.addKey("a", false);
        state.addKey("b", false);
        state.addKey("c", false);
        Action testSubject = new Action("", precondition, POSTCONDITION_NONE);
        assertTrue(testSubject.canExecute(state));
    }

    @Test
    public void testCanExecuteMixedState() {
        Precondition precondition = () -> {
            State state = new State();
            state.addKey("a", false);
            state.addKey("b", true);
            state.addKey("c", false);
            return state;
        };
        State state = new State();
        state.addKey("a", false);
        state.addKey("b", true);
        state.addKey("c", false);
        Action testSubject = new Action("", precondition, POSTCONDITION_NONE);
        assertTrue(testSubject.canExecute(state));
    }

    @Test
    public void testExecuteEffectNone() {
        Precondition precondition = State::new;
        State state = new State();
        Action testSubject = new Action("", precondition, POSTCONDITION_NONE);
        testSubject.execute(state);
        assertEquals(0, state.getKeys().size());
    }

    @Test
    public void testExecuteEffectReverse() {
        Precondition precondition = () -> {
            State state = new State();
            state.addKey("a", false);
            state.addKey("b", true);
            return state;
        };
        State state = new State();
        state.addKey("a", false);
        state.addKey("b", true);
        Action testSubject = new Action("", precondition, POSTCONDITION_REVERSE);
        testSubject.execute(state);
        assertEquals(2, state.getKeys().size());
        assertEquals(true, state.query("a"));
        assertEquals(false, state.query("b"));
    }

    @Test
    public void testExecuteEffectOne() {
        Precondition precondition = () -> {
            State state = new State();
            state.addKey("a", false);
            state.addKey("b", true);
            state.addKey("c", false);
            return state;
        };
        State state = new State();
        state.addKey("a", false);
        state.addKey("b", true);
        state.addKey("c", false);
        Action testSubject = new Action("", precondition, POSTCONDITION_ONE);
        testSubject.execute(state);
        assertEquals(3, state.getKeys().size());
        assertEquals(true, state.query("a"));
        assertEquals(true, state.query("b"));
        assertEquals(false, state.query("c"));
    }
}
