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
        for (int i = 0; i < 100; i++) {
            int a = random.nextInt();
            int b = random.nextInt();
            assertEquals(a > b, testSubject.check(a, b));
        }
    }
}
