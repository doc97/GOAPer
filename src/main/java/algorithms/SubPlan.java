package algorithms;

import model.Action;
import model.State;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel Riissanen on 2.4.2018.
 */
public class SubPlan {

    private int cost;
    private State goal;
    private State state;
    private List<Action> actions;

    SubPlan(State state, State goal, List<Action> actions, int cost) {
        this.cost = cost;
        this.state = state;
        this.goal = goal;
        this.actions = actions == null ? new ArrayList<>() : actions;
    }

    public int getCost() {
        return cost;
    }

    public State getGoal() {
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
        return getCost() == o.getCost() && state.equals(o.state) && goal.equals(o.goal);
    }
}
