package io.operations;

/**
 * Describes an operator between two integers.
 * <p/>
 * Created by Daniel Riissanen on 3.4.2018.
 */
public interface Operator {
    /**
     * Applies the operations to the inputs. Should be used by assigning the result to the first operand.
     * @param a The first operand
     * @param b The second operand
     * @return The result of the operation
     */
    int apply(int a, int b);
}
