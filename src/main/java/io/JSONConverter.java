package io;

import io.requirements.*;
import model.*;
import io.operations.AddOperation;
import io.operations.AssignOperation;
import io.operations.Operation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Daniel Riissanen on 17.3.2018.
 */
public class JSONConverter {

    private HashMap<Character, Operation> operations;
    private HashMap<Character, Requirement> requirements;

    public JSONConverter() {
        operations = new HashMap<>();
        operations.put('=', new AssignOperation());
        operations.put('+', new AddOperation());
        requirements = new HashMap<>();
        requirements.put('=', new EqualRequirement());
        requirements.put('<', new LessThanRequirement());
        requirements.put('>', new GreaterThanRequirement());
    }

    public void addOperation(char code, Operation operation) {
        operations.put(code, operation);
    }

    public void addRequirement(char code, Requirement requirement) {
        requirements.put(code, requirement);
    }

    public void removeOperation(char code) {
        operations.remove(code);
    }

    public void removeRequirement(char code) {
        requirements.remove(code);
    }

    public boolean isOpCodeReserved(char code) {
        return operations.containsKey(code);
    }

    public boolean isReqCodeReserved(char code) {
        return requirements.containsKey(code);
    }

    public Scenario convertScenario(JSONScenario jsonScenario) throws ScenarioLoadFailedException {
        Scenario scenario = new Scenario();
        scenario.start = convertState(jsonScenario.start);
        scenario.goal = convertGoal(jsonScenario.goal);
        scenario.actions = convertActions(jsonScenario.actions);
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

    public Precondition convertPrecondition(JSONRequirement[] jsonRequirements) throws ScenarioLoadFailedException {
        if (jsonRequirements == null) throw new ScenarioLoadFailedException("Precondition requirements must not be null");

        return state -> {
            float deficit = 0;
            for (JSONRequirement req : jsonRequirements) {
                Requirement requirement = requirements.getOrDefault(req.reqCode, new EqualRequirement());
                if (requirement instanceof WeightedRequirement)
                    ((WeightedRequirement) requirement).setWeight(req.weight);
                deficit += requirement.getDeficitCost(state.query(req.key), req.value);
            }
            return deficit;
        };
    }

    public Postcondition convertPostcondition(JSONOperation[] jsonOperations) throws ScenarioLoadFailedException {
        if (jsonOperations == null) throw new ScenarioLoadFailedException("Postcondition operations must not be null");

        return state -> {
            for (JSONOperation op : jsonOperations) {
                Operation operation = operations.getOrDefault(op.opCode, new AssignOperation());
                int result = operation.apply(state.query(op.key), op.value);
                state.apply(op.key, result);
            }
        };
    }

    public Goal convertGoal(JSONRequirement[] jsonRequirements) throws ScenarioLoadFailedException {
        if (jsonRequirements == null) throw new ScenarioLoadFailedException("Goal requirements must not be null");
        Goal goal = new Goal();
        Precondition precondition = convertPrecondition(jsonRequirements);
        goal.setRequirement(precondition);
        return goal;
    }

    private Action[] convertActions(JSONAction[] jsonActions) throws ScenarioLoadFailedException {
        List<Action> actions = new ArrayList<>();
        for (JSONAction jsonAction : jsonActions) {
            if (jsonAction != null)
                actions.add(convertAction(jsonAction));
        }
        Action[] array = new Action[actions.size()];
        actions.toArray(array);
        return array;
    }
}
