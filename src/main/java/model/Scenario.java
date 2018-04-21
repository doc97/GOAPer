package model;

/**
 * A scenario consists of a start state, a goal and a set of actions.
 * This class is mostly a data container.
 * <p/>
 * Created by Daniel Riissanen on 16.3.2018.
 */
public class Scenario {

    /** The start state */
    public State start;

    /** The goal that should be reached */
    public Goal goal;

    /** The set of actions */
    public Action[] actions;

    /**
     * Class constructor initializing everything to defaults.
     */
    public Scenario() {
        start = new State();
        goal = new Goal();
        actions = new Action[0];
    }

    /**
     * Checks whether the goal has been reached.
     * @return <code>true</code> if the scenario is finished, <code>false</code> otherwise
     */
    public boolean isFinished() {
        return goal.getDeficitCost(start) == 0;
    }
}
