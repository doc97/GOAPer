package io;

import datastructures.DynamicArray;
import datastructures.HashTable;
import io.operators.SubtractOperator;
import io.requirements.*;
import model.*;
import io.operators.AddOperator;
import io.operators.AssignOperator;
import io.operators.Operator;

/**
 * Created by Daniel Riissanen on 17.3.2018.
 */
public class JSONConverter {

    private HashTable<String, Operator> operators;
    private HashTable<String, Requirement> requirements;

    public JSONConverter() {
        operators = new HashTable<>();
        operators.put("=", new AssignOperator());
        operators.put("+", new AddOperator());
        operators.put("-", new SubtractOperator());
        requirements = new HashTable<>();
        requirements.put("==", new EqualRequirement());
        requirements.put("<", new LessThanRequirement());
        requirements.put(">", new GreaterThanRequirement());
        requirements.put("<=", new LessOrEqualRequirement());
        requirements.put(">=", new GreaterOrEqualRequirement());
    }

    public void addOperator(String code, Operator operator) {
        operators.put(code, operator);
    }

    public void addRequirement(String code, Requirement requirement) {
        requirements.put(code, requirement);
    }

    public void removeOperator(String code) {
        operators.remove(code);
    }

    public void removeRequirement(String code) {
        requirements.remove(code);
    }

    public boolean isOpCodeReserved(String code) {
        return operators.containsKey(code);
    }

    public boolean isReqCodeReserved(String code) {
        return requirements.containsKey(code);
    }

    public Scenario convertScenario(JSONScenario jsonScenario) throws ScenarioLoadFailedException {
        Scenario scenario = new Scenario();
        scenario.start = convertStart(jsonScenario.start);
        scenario.goal = convertGoal(jsonScenario.goal);
        scenario.actions = convertActions(jsonScenario.actions);
        return scenario;
    }

    public State convertStart(JSONStateKey[] jsonKeys) throws ScenarioLoadFailedException {
        if (jsonKeys == null) throw new ScenarioLoadFailedException("StateKey array must not be null");

        State state = new State();
        for (JSONStateKey key : jsonKeys) {
            if (key == null) throw new ScenarioLoadFailedException("State.key must not be null");
            if (key.key == null) throw new ScenarioLoadFailedException("State.key.key must not be null");
            state.addKey(key.key, key.value);
        }
        return state;
    }


    public Action convertAction(JSONAction jsonAction) throws ScenarioLoadFailedException {
        if (jsonAction == null) throw new ScenarioLoadFailedException("Action must not be null");

        Precondition[] preconditions = convertPreconditions(jsonAction.precondition);
        Postcondition postcondition = convertPostcondition(jsonAction.postcondition);
        Postcondition consumption = convertPostcondition(jsonAction.consumption);

        return new Action(
                jsonAction.name,
                jsonAction.cost,
                preconditions,
                postcondition,
                consumption
        );
    }

    public Precondition[] convertPreconditions(JSONRequirement[] jsonRequirements) throws ScenarioLoadFailedException {
        if (jsonRequirements == null) throw new ScenarioLoadFailedException("Precondition requirements must not be null");

        Precondition[] conditions = new Precondition[jsonRequirements.length];
        for (int i = 0; i < jsonRequirements.length; i++) {
            JSONRequirement req = jsonRequirements[i];
            Requirement requirement = requirements.getOrDefault(req.reqCode, new EqualRequirement());
            if (requirement instanceof WeightedRequirement)
                ((WeightedRequirement) requirement).setWeight(req.weight);

            conditions[i] = state -> requirement.getDeficitCost(state.query(req.key), req.value);
        }
        return conditions;
    }

    public Postcondition convertPostcondition(JSONOperator[] jsonOperators) throws ScenarioLoadFailedException {
        if (jsonOperators == null) throw new ScenarioLoadFailedException("Postcondition operators must not be null");

        return state -> {
            for (JSONOperator op : jsonOperators) {
                Operator operator = operators.getOrDefault(op.opCode, new AssignOperator());
                int result = operator.apply(state.query(op.key), op.value);
                state.apply(op.key, result);
            }
        };
    }

    public Goal convertGoal(JSONRequirement[] jsonRequirements) throws ScenarioLoadFailedException {
        if (jsonRequirements == null) throw new ScenarioLoadFailedException("Goal requirements must not be null");
        Precondition[] preconditions = convertPreconditions(jsonRequirements);
        return new Goal(preconditions);
    }

    private Action[] convertActions(JSONAction[] jsonActions) throws ScenarioLoadFailedException {
        DynamicArray<Action> actions = new DynamicArray<>();
        for (JSONAction jsonAction : jsonActions) {
            if (jsonAction != null)
                actions.add(convertAction(jsonAction));
        }
        Action[] array = new Action[actions.count()];
        actions.asArray(array);
        return array;
    }
}
