package model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Daniel Riissanen on 16.3.2018.
 */
public class ActionTest {

    public static final PreCondition preFalse = state -> false;
    public static final PreCondition preTrue = state -> true;
    public static final PreCondition preAll = state -> {
        for (String key : state.getKeys()) {
            if (!state.query(key))
                return false;
        }
        return true;
    };

    public static final Effect effectNone = state -> {};
    public static final Effect effectOne = state -> state.apply("a", !state.query("a"));
    public static final Effect effectReverse = state -> {
        for (String key : state.getKeys()) {
            state.apply(key, !state.query(key));
        }
    };

    @Test
    public void testCanExecuteFalse() {
        Action testSubject = new Action("", preFalse, effectNone);
        assertFalse(testSubject.canExecute(new State()));
    }

    @Test
    public void testCanExecuteTrue() {
        Action testSubject = new Action("", preTrue, effectNone);
        assertTrue(testSubject.canExecute(new State()));
    }

    @Test
    public void testCanExecuteAllTrueState() {
        State state = new State();
        state.addKey("a", true);
        state.addKey("b", true);
        state.addKey("c", true);
        Action testSubject = new Action("", preAll, effectNone);
        assertTrue(testSubject.canExecute(state));
    }

    @Test
    public void testCanExecuteAllFalseState() {
        State state = new State();
        state.addKey("a", false);
        state.addKey("b", false);
        state.addKey("c", false);
        Action testSubject = new Action("", preAll, effectNone);
        assertFalse(testSubject.canExecute(state));
    }

    @Test
    public void testCanExecuteMixedState() {
        State state = new State();
        state.addKey("a", false);
        state.addKey("b", true);
        state.addKey("c", false);
        Action testSubject = new Action("", preAll, effectNone);
        assertFalse(testSubject.canExecute(state));
    }

    @Test
    public void testExecuteEffectNone() {
        State state = new State();
        state.addKey("a", false);
        state.addKey("b", true);
        state.addKey("c", false);
        Action testSubject = new Action("", preTrue, effectNone);
        testSubject.execute(state);
        assertEquals(3, state.getKeys().size());
        assertEquals(false, state.query("a"));
        assertEquals(true, state.query("b"));
        assertEquals(false, state.query("c"));
    }

    @Test
    public void testExecuteEffectReverse() {
        State state = new State();
        state.addKey("a", false);
        state.addKey("b", true);
        state.addKey("c", false);
        Action testSubject = new Action("", preTrue, effectReverse);
        testSubject.execute(state);
        assertEquals(3, state.getKeys().size());
        assertEquals(true, state.query("a"));
        assertEquals(false, state.query("b"));
        assertEquals(true, state.query("c"));
    }

    @Test
    public void testExecuteEffectOne() {
        State state = new State();
        state.addKey("a", false);
        state.addKey("b", true);
        state.addKey("c", false);
        Action testSubject = new Action("", preTrue, effectOne);
        testSubject.execute(state);
        assertEquals(3, state.getKeys().size());
        assertEquals(true, state.query("a"));
        assertEquals(true, state.query("b"));
        assertEquals(false, state.query("c"));
    }
}
