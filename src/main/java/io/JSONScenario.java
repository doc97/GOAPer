package io;

import model.Scenario;

/**
 * Data class for a JSON representation of a Scenario.
 * <p/>
 * Created by Daniel Riissanen on 17.3.2018.
 * @see Scenario
 */
public class JSONScenario {
    public JSONState start;
    public JSONRequirement[] goal;
    public JSONAction[] actions;
}
