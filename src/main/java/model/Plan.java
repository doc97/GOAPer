package model;

/**
 * Created by Daniel Riissanen on 21.3.2018.
 */
public class Plan {

    private Action[] actions;
    private Action empty;
    private int cost;

    public Plan() {
        actions = new Action[0];
        empty = new Action("", 0, state -> 0, state -> {});
    }

    public Plan(Action[] actions, int additionalCost) {
        this.actions = actions;
        for (Action action : actions)
            cost += action.getCost();
        cost += additionalCost;
    }

    public Action[] getActions() {
        return actions;
    }

    public Action getNextAction() {
        return actions.length > 0 ? actions[0] : empty;
    }

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
