package model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Daniel Riissanen on 1.4.2018.
 */
public class ScenarioTest {

    @Test
    public void testConstructor() {
        Scenario testSubject = new Scenario();
        assertNotNull(testSubject.start);
        assertNotNull(testSubject.goal);
        assertNotNull(testSubject.actions);
    }

    @Test
    public void testIsFinishedEmpty() {
        Scenario testSubject = new Scenario();
        assertTrue(testSubject.isFinished());
    }

    @Test
    public void testIsFinishedFalseOneGoal() {
        Scenario testSubject = new Scenario();
        testSubject.goal.addRequirement(state -> Math.abs(1 - state.query("a")));
        assertFalse(testSubject.isFinished());
    }

    @Test
    public void testIsFinishedFalseDifferentValue() {
        Scenario testSubject = new Scenario();
        testSubject.goal.addRequirement(state -> Math.abs(1 - state.query("a")));
        testSubject.start.apply("a", 0);
        assertFalse(testSubject.isFinished());
    }

    @Test
    public void testIsFinishedTrueExactly() {
        Scenario testSubject = new Scenario();
        testSubject.goal.addRequirement(state -> Math.abs(1 - state.query("a")));
        testSubject.start.apply("a", 1);
        assertTrue(testSubject.isFinished());
    }

    @Test
    public void testIsFinishedTrueExtraStart() {
        Scenario testSubject = new Scenario();
        testSubject.goal.addRequirement(state -> Math.abs(1 - state.query("a")));
        testSubject.start.apply("a", 1);
        testSubject.start.apply("b", 1);
        assertTrue(testSubject.isFinished());
    }
}
