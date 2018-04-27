package io.requirements;

/**
 * Created by Daniel Riissanen on 27.4.2018.
 */
public class LessOrEqualRequirement extends WeightedRequirement {
    /**
     * The first should be smaller or equal to the second argument.
     * @param a The first argument
     * @param b The second argument
     * @return 0 if the requirement is met, otherwise the positive difference between the arguments
     */
    @Override
    protected int getDeficit(int a, int b) {
        return a <= b ? 0 : a - b;
    }
}
