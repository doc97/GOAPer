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
        return preCondition.checkState(state);
    }

    public void execute(State state) {
        effect.activate(state);
    }

    @Override
    public String toString() {
        return name;
    }
}
