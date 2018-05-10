package model;

/**
 * The goal is described by a set of requirements.
 * <p/>
 * Created by Daniel Riissanen on 3.4.2018.
 */
public class Goal implements Precondition {

    private Precondition[] requirements;

    /**
     * Class constructor with no requirement.
     */
    public Goal() {
        requirements = new Precondition[0];
    }

    /**
     * Class constructor specifying requirements.
     * @param requirements The requirements
     */
    public Goal(Precondition... requirements) {
        this.requirements = requirements;
    }

    /**
     * Class copy constructor.
     */
    public Goal(Goal other) {
        if (other == null)
            requirements = new Precondition[0];
        else
            requirements = other.requirements;
    }

    public void setRequirements(Precondition... requirements) {
        this.requirements = requirements;
    }

    @Override
    public float getDeficitCost(State state) {
        float deficit = 0;
        for (Precondition requirement : requirements)
            deficit += requirement.getDeficitCost(state);
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
        return getDeficitCost(state) == o.getDeficitCost(state);
    }
}
