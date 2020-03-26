package model;

import algorithms.SubPlan;
import datastructures.IntComparable;

/**
 * A plan consists of a set of actions and a cost.
 * <p/>
 * Created by Daniel Riissanen on 21.3.2018.
 */
public class Plan implements IntComparable<Plan> {

    /** An empty action used with NullObject pattern */
    private static Action empty = new Action("", 0, new Precondition[0], state -> {}, state -> {});

    /** The actions that the plan consists of */
    private Action[] actions;

    /** The total cost of the plan */
    private int cost;

    /** Indicates whether the plan is complete */
    private boolean isComplete;

    /**
     * Class constructor specifying if the plan is complete
     */
    public Plan(boolean isComplete) {
        this.isComplete = isComplete;
        actions = new Action[0];
    }

    /**
     * Class constructor specifying actions, an additional cost and whether the plan is complete.
     * @param actions The actions
     * @param isComplete If the plan is complete
     */
    public Plan(Action[] actions, boolean isComplete) {
        this.actions = actions;
        this.isComplete = isComplete;
        for (Action a : actions)
            this.cost += a.getCost();
    }

    /**
     * Class constructor to initialize plan from a {@link SubPlan}.
     * @param subPlan The sub plan to initialize from
     * @param isComplete If the plan is complete
     */
    public Plan(SubPlan subPlan, boolean isComplete) {
        if (subPlan == null) {
            actions = new Action[0];
        } else {
            Action[] sourceActions = subPlan.getActions();
            actions = new Action[sourceActions.length];
            for (int i = 0; i < actions.length; ++i) {
                actions[i] = subPlan.getActions()[sourceActions.length - 1 - i];
                cost += actions[i].getCost();
            }
        }
        this.isComplete = isComplete;
    }

    public Action[] getActions() {
        return actions;
    }

    /**
     * Returns the next actions, the first one in the array or an empty action if the array is empty.
     * @return The next action in the plan
     */
    public Action getNextAction() {
        return actions.length > 0 ? actions[0] : empty;
    }

    /**
     * The cost of the plan. It consists of the sum of the cost of the actions and any additional cost.
     * @return The total cost of the plan
     */
    public int getCost() {
        return cost;
    }

    /**
     *
     * @return <code>true</code> if the plan is marked as complete
     */
    public boolean isComplete() {
        return isComplete;
    }

    /**
     *
     * @return <code>true</code> if the plan has no actions
     */
    public boolean isEmpty() {
        return actions.length == 0;
    }

    @Override
    public String toString() {
        if (actions.length == 0)
            return "No plan";

        StringBuilder builder = new StringBuilder();
        if (!isComplete)
            builder.append("[NOT COMPLETE]: ");
        builder.append("[Start] -> ");
        if (!isComplete)
            builder.append("??? -> ");
        for (Action a : actions)
            builder.append(a).append(" -> ");
        builder.append("[Goal]");
        builder.append(" (cost: ").append(cost).append(", actions: ").append(actions.length).append(")");
        return builder.toString();
    }

    @Override
    public int compare(Plan other) {
        if (isComplete() && !other.isComplete())
            return -1;
        if (other.isComplete() && !isComplete())
            return 1;

        return getCost() - other.getCost();
    }
}
