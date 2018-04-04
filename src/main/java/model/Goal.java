package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel Riissanen on 3.4.2018.
 */
public class Goal implements Precondition {

    private List<Precondition> requirements;

    public Goal() {
        requirements = new ArrayList<>();
    }

    public Goal(Goal other) {
        requirements = new ArrayList<>(other.requirements);
    }

    public Goal(List<Precondition> requirements) {
        this.requirements = requirements;
    }

    public void addRequirement(Precondition requirement) {
        requirements.add(requirement);
    }

    public float getUnsatisfiedRequirementCost(State state) {
        float deficit = 0;
        for (Precondition req : requirements)
            deficit += req.getDeficitCost(state);
        return deficit;
    }

    @Override
    public float getDeficitCost(State state) {
        int deficit = 0;
        for (Precondition req : requirements)
            deficit += req.getDeficitCost(state);
        return deficit;
    }

    public boolean isEqual(Object other, State state) {
        if (!(other instanceof Goal))
            return false;

        Goal o = (Goal)other;
        if (requirements.size() != o.requirements.size())
            return false;

        for (int i = 0; i < requirements.size(); i++) {
            if (requirements.get(i).getDeficitCost(state) != o.requirements.get(i).getDeficitCost(state))
                return false;
        }
        return true;
    }
}
