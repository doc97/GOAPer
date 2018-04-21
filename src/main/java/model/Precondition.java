package model;

/**
 * Describes the requirements.
 * <p/>
 * Created by Daniel Riissanen on 16.3.2018.
 */
public interface Precondition {
    /**
     * The method returns an integer describing how much the given state is lacking for it to
     * fulfill the requirements described by the precondition.
     * <p/>
     * A deficit of 1, could mean that one requirement has not been fulfilled. But ultimately it is
     * up to the developer themselves to decide what it means.
     * @param state The state to check against
     * @return 0 if the requirement has been fulfilled, otherwise an integer describing the deficit
     */
    float getDeficitCost(State state);
}
