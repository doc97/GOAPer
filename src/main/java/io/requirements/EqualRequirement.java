package io.requirements;

/**
 * Equality requirement.
 * <p/>
 * Created by Daniel Riissanen on 3.4.2018.
 */
public class EqualRequirement extends WeightedRequirement {
    /**
     * The arguments should match.
     * @param a The first argument
     * @param b The second argument
     * @return Returns the difference between the arguments
     */
    @Override
    public int getDeficit(int a, int b) {
        return a == b ? 0 : Math.abs(a - b);
    }
}
