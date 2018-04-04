package io.requirements;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;

/**
 * Created by Daniel Riissanen on 3.4.2018.
 */
public class GreaterThanRequirementTest {
    @Test
    public void testCheck() {
        Random random = new Random();
        GreaterThanRequirement testSubject = new GreaterThanRequirement();
        int max = 1000000;
        for (int i = 0; i < 100; i++) {
            int a = random.nextInt(max) - max / 2;
            int b = random.nextInt(max) - max / 2;
            assertEquals(Math.max(0, b - a + 1), testSubject.getDeficit(a, b));
        }
    }
}
