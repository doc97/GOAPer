package io.requirements;

/**
 * Describes a requirement.
 * <p/>
 * Created by Daniel Riissanen on 3.4.2018.
 */
public interface Requirement {
    /**
     * Returns the deficit of the requirement.
     * @param a The first argument
     * @param b The second argument
     * @return The deficit
     */
    float getDeficitCost(int a, int b);
}
