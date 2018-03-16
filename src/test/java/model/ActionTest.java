package model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Daniel Riissanen on 16.3.2018.
 */
public class ActionTest {

    private static final Effect effectNone = state -> {};
    private static final Effect effectOne = state -> state.apply("a", !state.query("a"));
    private static final Effect effectReverse = state -> {
        for (String key : state.getKeys()) {
            state.apply(key, !state.query(key));
        }
    };

    @Test
    public void testCanExecuteFalse() {
        PreCondition preFalse = () -> {
            State state = new State();
            state.addKey("a", true);
            return state;
        };
        Action testSubject = new Action("", preFalse, effectNone);
        assertFalse(testSubject.canExecute(new State()));
    }

    @Test
    public void testCanExecuteAllTrueState() {
        PreCondition preCondition = () -> {
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
        Action testSubject = new Action("", preCondition, effectNone);
        assertTrue(testSubject.canExecute(state));
    }

    @Test
    public void testCanExecuteAllFalseState() {
        PreCondition preCondition = () -> {
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
        Action testSubject = new Action("", preCondition, effectNone);
        assertTrue(testSubject.canExecute(state));
    }

    @Test
    public void testCanExecuteMixedState() {
        PreCondition preCondition = () -> {
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
        Action testSubject = new Action("", preCondition, effectNone);
        assertTrue(testSubject.canExecute(state));
    }

    @Test
    public void testExecuteEffectNone() {
        PreCondition preCondition = State::new;
        State state = new State();
        Action testSubject = new Action("", preCondition, effectNone);
        testSubject.execute(state);
        assertEquals(0, state.getKeys().size());
    }

    @Test
    public void testExecuteEffectReverse() {
        PreCondition preCondition = () -> {
            State state = new State();
            state.addKey("a", false);
            state.addKey("b", true);
            return state;
        };
        State state = new State();
        state.addKey("a", false);
        state.addKey("b", true);
        Action testSubject = new Action("", preCondition, effectReverse);
        testSubject.execute(state);
        assertEquals(2, state.getKeys().size());
        assertEquals(true, state.query("a"));
        assertEquals(false, state.query("b"));
    }

    @Test
    public void testExecuteEffectOne() {
        PreCondition preCondition = () -> {
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
        Action testSubject = new Action("", preCondition, effectOne);
        testSubject.execute(state);
        assertEquals(3, state.getKeys().size());
        assertEquals(true, state.query("a"));
        assertEquals(true, state.query("b"));
        assertEquals(false, state.query("c"));
    }
}
