package model;

/**
 * Created by Daniel Riissanen on 21.3.2018.
 */
public class Plan {

    private Action[] actions;
    private int cost;

    public Plan() {
        actions = new Action[0];
    }

    public Plan(Action[] actions, int cost) {
        this.actions = actions;
        this.cost = cost;
    }

    public Action[] getActions() {
        return actions;
    }

    public int getCost() {
        return cost;
    }
}
