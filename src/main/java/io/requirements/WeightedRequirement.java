package io.requirements;

/**
 * A requirement that is scaled according to a weight.
 * <p/>
 * Created by Daniel Riissanen on 4.4.2018.
 */
public abstract class WeightedRequirement implements Requirement {

    /** The weight to scale with */
    private float weight;

    /**
     * Class constructor initializing weight to 1.
     */
    public WeightedRequirement() {
        weight = 1;
    }

    /**
     * Clamps the weight between [0, 1].
     * @param weight The weight
     */
    public void setWeight(float weight) {
        this.weight = Math.max(0, Math.min(1, weight));
    }

    /**
     * Returns the raw deficit.
     * @param a The first argument
     * @param b The second argument
     * @return The deficit
     */
    protected abstract int getDeficit(int a, int b);

    /**
     * Returns the weighted deficit.
     * @param a The first argument
     * @param b The second argument
     * @return The weighted deficit
     */
    @Override
    public float getDeficitCost(int a, int b) {
        return weight * getDeficit(a, b);
    }
}
