package io.operators;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;

/**
 * Created by Daniel Riissanen on 3.4.2018.
 */
public class SubtractOperatorTest {

    @Test
    public void testApplyPositive() {
        Random random = new Random();
        SubtractOperator testSubject = new SubtractOperator();
        for (int i = 0; i < 10; i++) {
            int a = random.nextInt();
            int b = random.nextInt();
            assertEquals(a - b, testSubject.apply(a, b));
        }
    }

    @Test
    public void testApplyNegative() {
        Random random = new Random();
        SubtractOperator testSubject = new SubtractOperator();
        for (int i = 0; i < 10; i++) {
            int a = random.nextInt();
            int b = -random.nextInt();
            assertEquals(a - b, testSubject.apply(a, b));
        }
    }
}
