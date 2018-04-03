package io;

import model.*;
import model.operations.AddOperation;
import model.operations.AssignOperation;
import model.operations.Operation;
import model.requirements.EqualRequirement;
import model.requirements.GreaterThanRequirement;
import model.requirements.LessThanRequiremnt;
import model.requirements.Requirement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel Riissanen on 17.3.2018.
 */
public class JSONConverter {
    public Scenario convertScenario(JSONScenario jsonScenario) throws ScenarioLoadFailedException {
        Scenario scenario = new Scenario();
        scenario.start = convertState(jsonScenario.start);
        scenario.goal = convertGoal(jsonScenario.goal);
        List<Action> actions = new ArrayList<>();
        for (int i = 0; i < jsonScenario.actions.length; i++) {
            if (jsonScenario.actions[i] != null)
                actions.add(convertAction(jsonScenario.actions[i]));
        }
        scenario.actions = new Action[actions.size()];
        actions.toArray(scenario.actions);
        return scenario;
    }

    public State convertState(JSONState jsonState) throws ScenarioLoadFailedException {
        if (jsonState == null) throw new ScenarioLoadFailedException("State must not be null");
        if (jsonState.keys == null) throw new ScenarioLoadFailedException("State.keys must not be null");

        State state = new State();
        for (JSONStateKey key : jsonState.keys) {
            if (key == null) throw new ScenarioLoadFailedException("State.key must not be null");
            if (key.key == null) throw new ScenarioLoadFailedException("State.key.key must not be null");
            state.addKey(key.key, key.value);
        }
        return state;
    }


    public Action convertAction(JSONAction jsonAction) throws ScenarioLoadFailedException {
        if (jsonAction == null) throw new ScenarioLoadFailedException("Action must not be null");

        Precondition precondition = convertPrecondition(jsonAction.precondition);
        Postcondition postcondition = convertPostcondition(jsonAction.postcondition);

        return new Action(
                jsonAction.name,
                jsonAction.cost,
                precondition,
                postcondition
        );
    }

    public Precondition convertPrecondition(JSONRequirement[] requirements) throws ScenarioLoadFailedException {
        if (requirements == null) throw new ScenarioLoadFailedException("Precondition requirements must not be null");

        return state -> {
            for (JSONRequirement req : requirements) {
                Requirement requirement = new EqualRequirement();
                if (req.reqCode == '<')
                    requirement = new LessThanRequiremnt();
                else if (req.reqCode == '>')
                    requirement = new GreaterThanRequirement();
                if (!requirement.check(req.value, state.query(req.key)))
                    return false;
            }
            return true;
        };
    }

    public Postcondition convertPostcondition(JSONOperation[] operations) throws ScenarioLoadFailedException {
        if (operations == null) throw new ScenarioLoadFailedException("Postcondition operations must not be null");

        return state -> {
            for (JSONOperation op : operations) {
                Operation operation = new AssignOperation();
                if (op.opCode == '+')
                    operation = new AddOperation();
                state.apply(op.key, op.value, operation);
            }
        };
    }

    public Goal convertGoal(JSONRequirement[] requirements) throws ScenarioLoadFailedException {
        if (requirements == null) throw new ScenarioLoadFailedException("Goal requirements must not be null");
        Goal goal = new Goal();
        for (JSONRequirement requirement : requirements) {
            Precondition precondition = convertPrecondition(new JSONRequirement[] { requirement });
            goal.addRequirement(precondition);
        }
        return goal;
    }
}
