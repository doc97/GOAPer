package model;

/**
 * Created by Daniel Riissanen on 16.3.2018.
 */
public class Scenario {
    public State start;
    public Goal goal;
    public Action[] actions;

    public Scenario() {
        start = new State();
        goal = new Goal();
        actions = new Action[0];
    }

    public boolean isFinished() {
        return goal.isSatisfied(start);
    }
}
