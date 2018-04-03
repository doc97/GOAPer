package model.operations;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;

/**
 * Created by Daniel Riissanen on 3.4.2018.
 */
public class AddOperationTest {

    @Test
    public void testApplyOrder() {
        Random random = new Random();
        AddOperation testSubject = new AddOperation();
        for (int i = 0; i < 10; i++) {
            int a = random.nextInt();
            int b = random.nextInt();
            assertEquals(a + b, testSubject.apply(a, b));
            assertEquals(a + b, testSubject.apply(b, a));
        }
    }

    @Test
    public void testApplyNegative() {
        Random random = new Random();
        AddOperation testSubject = new AddOperation();
        for (int i = 0; i < 10; i++) {
            int a = random.nextInt();
            int b = -random.nextInt();
            assertEquals(a + b, testSubject.apply(a, b));
            assertEquals(a + b, testSubject.apply(b, a));
        }
    }
}
