package io.operations;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;

/**
 * Created by Daniel Riissanen on 3.4.2018.
 */
public class AssignOperatorTest {

    @Test
    public void testApply() {
        Random random = new Random();
        AssignOperator testSubject = new AssignOperator();
        for (int i = 0; i < 10; i++) {
            int value = random.nextInt();
            int input = random.nextInt();
            assertEquals(input, testSubject.apply(value, input));
        }
    }
}
