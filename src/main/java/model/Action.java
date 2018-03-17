package model;

/**
 * Created by Daniel Riissanen on 16.3.2018.
 */
public class Action {

    private String name;
    private Precondition precondition;
    private Postcondition postcondition;

    public Action(String name, Precondition precondition, Postcondition postcondition) {
        this.name = name;
        this.precondition = precondition;
        this.postcondition = postcondition;
    }

    public boolean canExecute(State state) {
        State preConditionState = precondition.getState();
        for (String key : preConditionState.getKeys()) {
            if (state.query(key) != preConditionState.query(key))
                return false;
        }
        return true;
    }

    public void execute(State state) {
        postcondition.activate(state);
    }

    public Precondition getPrecondition() {
        return precondition;
    }

    @Override
    public String toString() {
        return name;
    }
}
