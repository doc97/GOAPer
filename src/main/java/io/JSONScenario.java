package io;

import model.Scenario;

/**
 * Data class for a JSON representation of a Scenario.
 * <p/>
 * Created by Daniel Riissanen on 17.3.2018.
 * @see Scenario
 */
public class JSONScenario {
    public JSONStateKey[] start = new JSONStateKey[0];
    public JSONRequirement[] goal = new JSONRequirement[0];
    public JSONAction[] actions = new JSONAction[0];

    public boolean isEmpty() {
        return start.length == 0 && goal.length == 0 && actions.length == 0;
    }
}
