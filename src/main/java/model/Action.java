package model;

/**
 * Created by Daniel Riissanen on 16.3.2018.
 */
public class Action {

    private String name;
    private PreCondition preCondition;
    private Effect effect;

    public Action(String name, PreCondition preCondition, Effect effect) {
        this.name = name;
        this.preCondition = preCondition;
        this.effect = effect;
    }

    public boolean canExecute(State state) {
        State preConditionState = preCondition.getState();
        for (String key : preConditionState.getKeys()) {
            if (state.query(key) != preConditionState.query(key))
                return false;
        }
        return true;
    }

    public void execute(State state) {
        effect.activate(state);
    }

    public PreCondition getPreCondition() {
        return preCondition;
    }

    @Override
    public String toString() {
        return name;
    }
}
