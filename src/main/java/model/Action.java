package model;

/**
 * An action that can be executed.
 * <p/>
 * Created by Daniel Riissanen on 16.3.2018.
 */
public class Action {

    /** The name of the action */
    private String name;

    /** The heuristic cost of the action */
    private int cost;

    /** The precondition that must be met to be able to execute the action */
    private Precondition precondition;

    /** The postcondition describing the effects this action will have */
    private Postcondition postcondition;

    /**
     * Class constructor initializing variables.
     * @param name The name of the action, preferably unique
     * @param cost The heuristic cost of the action
     * @param precondition The precondition of this action
     * @param postcondition The postcondition
     */
    public Action(String name, int cost, Precondition precondition, Postcondition postcondition) {
        this.name = name;
        this.cost = cost;
        this.precondition = precondition;
        this.postcondition = postcondition;
    }

    /**
     * Checks whether the precondition requirements have been met.
     * @param state The state to check with
     * @return <code>true</code> if the action can be executed, <code>false</code> otherwise
     */
    public boolean canExecute(State state) {
        return precondition.getDeficitCost(state) == 0;
    }

    /**
     * Executes the action on a state, should be used in conjunction with {@link #canExecute(State)}.
     * @param state The state to execute the action on
     */
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
