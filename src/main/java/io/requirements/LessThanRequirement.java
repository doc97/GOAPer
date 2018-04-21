package io.requirements;

/**
 * Created by Daniel Riissanen on 3.4.2018.
 */
public class LessThanRequirement extends WeightedRequirement {
    /**
     * The first should be smaller than the second argument.
     * @param a The first argument
     * @param b The second argument
     * @return 0 if the requirement is met, otherwise the difference between the arguments
     */
    @Override
    public int getDeficit(int a, int b) {
        return a < b ? 0 : a - b + 1;
    }
}
