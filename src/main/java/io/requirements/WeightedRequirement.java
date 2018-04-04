package io.requirements;

/**
 * Created by Daniel Riissanen on 4.4.2018.
 */
public abstract class WeightedRequirement implements Requirement {

    private float weight;

    public WeightedRequirement() {
        weight = 1;
    }

    public void setWeight(float weight) {
        this.weight = Math.max(0, Math.min(1, weight));
    }

    protected abstract int getDeficit(int a, int b);

    @Override
    public float getDeficitCost(int a, int b) {
        return weight * getDeficit(a, b);
    }
}
