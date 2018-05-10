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

    /** The preconditions that must be met to be able to execute the action */
    private Precondition[] preconditions;

    /** The postcondition describing the effects this action will have */
    private Postcondition postcondition;

    /** The postcondition that describes what is consumed when the action is performed */
    private Postcondition consumption;

    /**
     * Class constructor initializing variables.
     * @param name The name of the action, preferably unique
     * @param cost The heuristic cost of the action
     * @param preconditions The preconditions of this action
     * @param postcondition The postcondition
     * @param consumption The postcondition
     */
    public Action(String name, int cost, Precondition[] preconditions, Postcondition postcondition,
                  Postcondition consumption) {
        this.name = name;
        this.cost = cost;
        this.preconditions = preconditions;
        this.postcondition = postcondition;
        this.consumption = consumption;
    }

    /**
     * Checks whether the precondition requirements have been met.
     * @param state The state to check with
     * @return <code>true</code> if the action can be executed, <code>false</code> otherwise
     */
    public boolean canExecute(State state) {
        for (Precondition condition : preconditions) {
            if (condition.getDeficitCost(state) != 0)
                return false;
        }
        return true;
    }

    /**
     * Executes the action on a state, should be used in conjunction with {@link #canExecute(State)}.
     * @param state The state to execute the action on
     */
    public void execute(State state) {
        postcondition.activate(state);
        consumption.activate(state);
    }

    public Precondition[] getPreconditions() {
        return preconditions;
    }

    public Postcondition getPostcondition() {
        return postcondition;
    }

    public Postcondition getConsumption() {
        return consumption;
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
