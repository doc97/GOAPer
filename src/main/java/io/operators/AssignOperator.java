package io.operators;

/**
 * Assignment operator.
 * <p/>
 * Created by Daniel Riissanen on 3.4.2018.
 */
public class AssignOperator implements Operator {
    /**
     * Returns the second operand.
     * @param a The first operand
     * @param b The second operand
     * @return The second operand
     */
    @Override
    public int apply(int a, int b) {
        return b;
    }
}
