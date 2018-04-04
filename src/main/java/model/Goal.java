package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel Riissanen on 3.4.2018.
 */
public class Goal implements Precondition {

    private Precondition originalRequirement;
    private List<Precondition> additionalRequirements;

    public Goal() {
        originalRequirement = state -> 0;
        additionalRequirements = new ArrayList<>();
    }

    public Goal(Goal other) {
        this.originalRequirement = other.originalRequirement;
        additionalRequirements = new ArrayList<>(other.additionalRequirements);
    }

    public Goal(List<Precondition> additionalRequirements) {
        this.additionalRequirements = additionalRequirements;
    }

    public void setRequirement(Precondition requirement) {
        originalRequirement = requirement;
    }

    public void addAdditionalRequirement(Precondition requirement) {
        additionalRequirements.add(requirement);
    }

    public float getUnsatisfiedRequirementCost(State state) {
        float deficit = 0;
        for (Precondition req : additionalRequirements)
            deficit += req.getDeficitCost(state);
        return deficit;
    }

    @Override
    public float getDeficitCost(State state) {
        return originalRequirement.getDeficitCost(state);
    }

    public boolean isEqual(Object other, State state) {
        if (!(other instanceof Goal))
            return false;

        Goal o = (Goal)other;
        if (additionalRequirements.size() != o.additionalRequirements.size())
            return false;

        if (originalRequirement.getDeficitCost(state) != o.originalRequirement.getDeficitCost(state))
            return false;

        for (int i = 0; i < additionalRequirements.size(); i++) {
            if (additionalRequirements.get(i).getDeficitCost(state) != o.additionalRequirements.get(i).getDeficitCost(state))
                return false;
        }
        return true;
    }
}
