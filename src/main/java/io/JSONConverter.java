package io;

import model.Action;
import model.Scenario;
import model.State;

/**
 * Created by Daniel Riissanen on 17.3.2018.
 */
public class JSONConverter {
    public Scenario convertScenario(JSONScenario jsonScenario) {
        Scenario scenario = new Scenario();
        scenario.start = convertState(jsonScenario.start);
        scenario.goal = convertState(jsonScenario.goal);
        scenario.actions = new Action[jsonScenario.actions.length];
        for (int i = 0; i < jsonScenario.actions.length; i++) {
            if (jsonScenario.actions[i] != null)
                scenario.actions[i] = convertAction(jsonScenario.actions[i]);
        }
        return scenario;
    }

    public State convertState(JSONState jsonState) {
        if (jsonState == null) throw new IllegalStateException("State must not be null");
        if (jsonState.keys == null) throw new IllegalStateException("State.keys must not be null");

        State state = new State();
        for (JSONStateKey key : jsonState.keys) {
            if (key == null) throw new IllegalStateException("State.key must not be null");
            if (key.key == null) throw new IllegalStateException("State.key.key must not be null");
            state.addKey(key.key, key.value);
        }
        return state;
    }

    public Action convertAction(JSONAction jsonAction) {
        if (jsonAction == null) throw new IllegalStateException("Action must not be null");

        State precondition = convertState(jsonAction.precondition);
        State postcondition = convertState(jsonAction.postcondition);

        return new Action(
                jsonAction.name,
                () -> precondition,
                state -> {
                    for (String key : postcondition.getKeys())
                        state.apply(key, postcondition.query(key));
                }
        );
    }
}
