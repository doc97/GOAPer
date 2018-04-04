package io.requirements;

/**
 * Created by Daniel Riissanen on 3.4.2018.
 */
public class EqualRequirement implements Requirement {
    @Override
    public int getDeficit(int a, int b) {
        return a == b ? 0 : Math.abs(a - b);
    }
}
