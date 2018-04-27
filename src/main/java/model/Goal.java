package model;

import java.util.ArrayList;
import java.util.List;

/**
 * The goal is described by a set of requirements.
 * <p/>
 * Created by Daniel Riissanen on 3.4.2018.
 */
public class Goal implements Precondition {

    /** The primary requirement of the goal */
    private Precondition primaryRequirement;

    /** The additional requirements added by actions */
    private List<Precondition> additionalRequirements;

    /**
     * Class constructor with no requirements.
     */
    public Goal() {
        primaryRequirement = state -> 0;
        additionalRequirements = new ArrayList<>();
    }

    /**
     * Class copy constructor.
     */
    public Goal(Goal other) {
        this.primaryRequirement = other.primaryRequirement;
        additionalRequirements = new ArrayList<>(other.additionalRequirements);
    }

    /**
     * Class constructor specifying additional requirements, with no primary requirement.
     * @param additionalRequirements The list of additional requirements
     */
    public Goal(List<Precondition> additionalRequirements) {
        this.additionalRequirements = additionalRequirements == null ? new ArrayList<>() : additionalRequirements;
        primaryRequirement = state -> 0;
    }

    /**
     * Set the primary requirement.
     * @param requirement The requirement
     */
    public void setRequirement(Precondition requirement) {
        primaryRequirement = requirement;
    }

    /**
     * Adds an additional requirement.
     * @param requirement The requirement to add
     */
    public void addAdditionalRequirement(Precondition requirement) {
        additionalRequirements.add(requirement);
    }

    /**
     * Returns the total deficit of all the additional requirements.
     * @param state The state to check against
     * @return An integer describing the deficit
     */
    public float getAdditionalRequirementsDeficitCost(State state) {
        float deficit = 0;
        float factor = 0.5f;
        for (int i = 0; i < additionalRequirements.size(); i++) {
            Precondition req = additionalRequirements.get(i);
            deficit += req.getDeficitCost(state) * factor;
            factor /= 2f;
        }
        return deficit;
    }

    @Override
    public float getDeficitCost(State state) {
        return primaryRequirement.getDeficitCost(state);
    }

    /**
     * Returns the sum of the deficit of the primary requirement and the additional requirements.
     * @param state The state to check against
     * @return An integer describing the deficit
     */
    public float getTotalDeficitCost(State state) {
        return getDeficitCost(state) + getAdditionalRequirementsDeficitCost(state);
    }

    /**
     * Checks whether two goals are equal.
     * @param other The other goal to check
     * @param state The state to check against
     * @return <code>true</code> if the deficits of the two goals are the same, otherwise <code>false</code>
     */
    public boolean isEqual(Object other, State state) {
        if (!(other instanceof Goal))
            return false;

        Goal o = (Goal)other;
        if (additionalRequirements.size() != o.additionalRequirements.size())
            return false;

        if (primaryRequirement.getDeficitCost(state) != o.primaryRequirement.getDeficitCost(state))
            return false;

        for (int i = 0; i < additionalRequirements.size(); i++) {
            if (additionalRequirements.get(i).getDeficitCost(state) != o.additionalRequirements.get(i).getDeficitCost(state))
                return false;
        }
        return true;
    }
}
