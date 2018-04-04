package model;

/**
 * Created by Daniel Riissanen on 16.3.2018.
 */
public class Action {

    private String name;
    private int cost;
    private Precondition precondition;
    private Postcondition postcondition;

    public Action(String name, int cost, Precondition precondition, Postcondition postcondition) {
        this.name = name;
        this.cost = cost;
        this.precondition = precondition;
        this.postcondition = postcondition;
    }

    public boolean canExecute(State state) {
        return precondition.getDeficitCost(state) == 0;
    }

    public void execute(State state) {
        postcondition.activate(state);
    }

    public Precondition getPrecondition() {
        return precondition;
    }

    public Postcondition getPostcondition() {
        return postcondition;
    }

    public int getCost() {
        return cost;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
