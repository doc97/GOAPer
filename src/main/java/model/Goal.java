package model;

import java.util.ArrayList;
import java.util.List;

/**
 * The goal is described by a set of requirements.
 * <p/>
 * Created by Daniel Riissanen on 3.4.2018.
 */
public class Goal implements Precondition {

    /** The additional requirements added by actions */
    private List<Precondition> requirements;

    /** The additional requirements also have a consumption connected to them */
    private List<Postcondition> consumptions;

    private List<Postcondition> consumptionsInEffect;

    private boolean primaryRequirementAchived;

    /**
     * Class constructor with no requirements.
     */
    public Goal() {
        requirements = new ArrayList<>();
        consumptions = new ArrayList<>();
        consumptionsInEffect = new ArrayList<>();
    }

    /**
     * Class copy constructor.
     */
    public Goal(Goal other) {
        requirements = new ArrayList<>(other.requirements);
        consumptions = new ArrayList<>(other.consumptions);
        consumptionsInEffect = new ArrayList<>(other.consumptionsInEffect);
        primaryRequirementAchived = other.primaryRequirementAchived;
    }

    /**
     * Adds an additional requirement.
     * @param requirement The requirement to add
     */
    public void addRequirement(Precondition requirement, Postcondition consumption) {
        if (requirements.isEmpty()) {
            requirements.add(requirement);
            consumptions.add(consumption);
        } else if (!requirements.get(requirements.size() - 1).equals(requirement)) {
            requirements.add(requirement);
            consumptions.add(consumption);
        }
    }

    @Override
    public float getDeficitCost(State state) {
        if (requirements.isEmpty())
            return 0;

        float deficit = requirements.get(0).getDeficitCost(state);
        if (requirements.size() == 1 || deficit != 0)
            return deficit;

        if (!primaryRequirementAchived) {
            primaryRequirementAchived = true;
            return deficit;
        }

        do {
            State testState = new State(state);
            for (Postcondition consumption : consumptionsInEffect)
                consumption.activate(testState);

            Precondition req = requirements.get(1);
            deficit = req.getDeficitCost(testState);

            if (deficit == 0) {
                requirements.remove(1);
                consumptionsInEffect.add(consumptions.remove(1));
                if (requirements.size() == 1)
                    return 0;
            }
        } while (deficit == 0);
        return deficit;
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
        if (requirements.size() != o.requirements.size())
            return false;

        for (int i = 0; i < requirements.size(); i++) {
            if (requirements.get(i).getDeficitCost(state) != o.requirements.get(i).getDeficitCost(state))
                return false;
        }
        return true;
    }
}
