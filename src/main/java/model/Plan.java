package model;

/**
 * A plan consists of a set of actions and a cost.
 * <p/>
 * Created by Daniel Riissanen on 21.3.2018.
 */
public class Plan {

    /** An empty action used with NullObject pattern */
    private static Action empty = new Action("", 0, state -> 0, state -> {});

    /** The actions that the plan consists of */
    private Action[] actions;

    /** The total cost of the plan */
    private int cost;

    /**
     * Class constructor with no actions.
     */
    public Plan() {
        actions = new Action[0];
    }

    /**
     * Class constructor specifying actions and an additional cost.
     * @param actions The actions
     * @param additionalCost Any additional cost
     */
    public Plan(Action[] actions, int additionalCost) {
        this.actions = actions;
        for (Action action : actions)
            cost += action.getCost();
        cost += additionalCost;
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

    @Override
    public String toString() {
        if (actions.length == 0)
            return "No plan";

        StringBuilder builder = new StringBuilder();
        builder.append("[Start] -> ");
        for (Action a : actions)
            builder.append(a).append(" -> ");
        builder.append("[Goal]");
        builder.append(" (cost: ").append(cost).append(", actions: ").append(actions.length).append(")");
        return builder.toString();
    }
}
