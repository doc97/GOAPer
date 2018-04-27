package io.requirements;

/**
 * Created by Daniel Riissanen on 3.4.2018.
 */
public class GreaterThanRequirement extends WeightedRequirement {
    /**
     * The first should be bigger than the second argument.
     * @param a The first argument
     * @param b The second argument
     * @return 0 if the requirement is met, otherwise the positive difference between the arguments
     */
    @Override
    public int getDeficit(int a, int b) {
        return a > b ? 0 : b - a + 1;
    }
}
