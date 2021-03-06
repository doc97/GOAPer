package io.operators;

/**
 * Addition operator.
 * <p/>
 * Created by Daniel Riissanen on 3.4.2018.
 */
public class AddOperator implements Operator {
    /**
     * Returns the sum of the inputs.
     * @param a The first input
     * @param b The second input
     * @return The difference
     */
    @Override
    public int apply(int a, int b) {
        return a + b;
    }
}
