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

    public int getUnsatisfiedRequirementCount(State state) {
        int deficit = 0;
        for (Precondition req : requirements) {
            deficit += req.getDeficit(state);
        }
        return deficit;
    }

    @Override
    public int getDeficit(State state) {
        int deficit = 0;
        for (Precondition req : requirements) {
            deficit += req.getDeficit(state);
        }
        return deficit;
    }

    public boolean isEqual(Object other, State state) {
        if (!(other instanceof Goal))
            return false;

        Goal o = (Goal)other;
        if (requirements.size() != o.requirements.size())
            return false;

        for (int i = 0; i < requirements.size(); i++) {
            if (requirements.get(i).getDeficit(state) != o.requirements.get(i).getDeficit(state))
                return false;
        }
        return true;
    }
}
