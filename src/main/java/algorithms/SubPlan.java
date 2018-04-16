package algorithms;

import model.Action;
import model.Goal;
import model.State;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel Riissanen on 2.4.2018.
 */
public class SubPlan {

    private int cost;
    private State state;
    private Goal goal;
    private List<Action> actions;

    SubPlan(State state, Goal goal, List<Action> actions, int cost) {
        this.cost = cost;
        this.state = state == null ? new State() : state;
        this.goal = goal == null ? new Goal() : goal;
        this.actions = actions == null ? new ArrayList<>() : actions;
    }

    public int getCost() {
        return cost;
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
        int cost = getCost();
        for (Action a : actions)
            cost += a.getCost();

        int otherCost = o.getCost();
        for (Action a : o.actions)
            otherCost += a.getCost();

        return cost == otherCost && state.equals(o.state) && goal.isEqual(o.goal, state);
    }
}
