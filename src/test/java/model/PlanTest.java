package model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Daniel Riissanen on 22.3.2018.
 */
public class PlanTest {

    @Test
    public void testConstructorEmpty() {
        Plan testSubject = new Plan();
        assertNotNull(testSubject.getActions());
        assertEquals(0, testSubject.getCost());
        assertEquals(0, testSubject.getActions().length);
    }

    @Test
    public void testConstructorNonEmpty() {
        Action[] actions = new Action[] {
            new Action("", 0, null, null)
        };
        Plan testSubject = new Plan(actions, 1);
        assertEquals(actions.length, testSubject.getActions().length);
        assertEquals(1, testSubject.getCost());
    }
}
