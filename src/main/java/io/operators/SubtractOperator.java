package io.operators;

/**
 * Subtraction operator.
 * <p/>
 * Created by Daniel Riissanen on 27.4.2018.
 */
public class SubtractOperator implements Operator {
    /**
     * Returns the difference of the inputs.
     * @param a The first input
     * @param b The second input
     * @return The difference
     */
    @Override
    public int apply(int a, int b) {
        return a - b;
    }
}
