package io.requirements;

/**
 * Created by Daniel Riissanen on 3.4.2018.
 */
public class GreaterThanRequirement extends WeightedRequirement {
    @Override
    public int getDeficit(int a, int b) {
        return a > b ? 0 : b - a + 1;
    }
}
