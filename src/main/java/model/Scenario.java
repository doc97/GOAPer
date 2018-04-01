package model;

/**
 * Created by Daniel Riissanen on 16.3.2018.
 */
public class Scenario {
    public State start;
    public State goal;
    public Action[] actions;

    public Scenario() {
        start = new State();
        goal = new State();
        actions = new Action[0];
    }

    public boolean isFinished() {
        for (String goalKey : goal.getKeys()) {
            if (start.query(goalKey) != goal.query(goalKey))
                return false;
        }
        return true;
    }
}
