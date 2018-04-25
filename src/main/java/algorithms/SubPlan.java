package algorithms;

import model.Action;
import model.Goal;
import model.State;

import java.util.ArrayList;
import java.util.List;

/**
 * A sub plan is different from a {@link model.Plan} by also containing the current state and goal.
 * The class is used as a node in the planning algorithms.
 * <p/>
 * Created by Daniel Riissanen on 2.4.2018.
 */
public class SubPlan {

    /** The cost of the current sub plan */
    private int cost;

    /** The sum of the cost of the actions */
    private int actionCost;

    /** The current state after executing all actions on the start state */
    private State state;

    /** The goal that the algorithm is trying to reach */
    private Goal goal;

    /** A list of actions that the sub plan currently has */
    private List<Action> actions;

    /**
     * Class constructor initializing variables.
     * @param state The current state
     * @param goal The goal
     * @param actions The list of actions
     * @param cost The cost
     */
    SubPlan(State state, Goal goal, List<Action> actions, int cost) {
        this.cost = cost;
        this.state = state == null ? new State() : state;
        this.goal = goal == null ? new Goal() : goal;
        this.actions = actions == null ? new ArrayList<>() : actions;
        for (Action a : this.actions)
            actionCost += a.getCost();
    }

    public int getTotalCost() {
        return cost + actionCost;
    }

    public int getCost() {
        return cost;
    }

    public int getActionCost() {
        return actionCost;
    }

    public Goal getGoal() {
        return goal;
    }

    public State getState() {
        return state;
    }

    public List<Action> getActions() {
        return actions;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof SubPlan))
            return false;

        SubPlan o = (SubPlan) other;
        int cost = getCost() + getActionCost();
        int otherCost = o.getCost() + o.getActionCost();

        return cost == otherCost && state.equals(o.state) && goal.isEqual(o.goal, state);
    }
}
