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

    /** A mapping of known operators */
    private HashTable<String, Operator> operators;

    /** A mapping of know requirements */
    private HashTable<String, Requirement> requirements;

    /**
     * Constructor initializing operators and requirements.
     */
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

    /**
     * Adds an operator. Will overwrite an existing operator.
     * @param code The code to identify the operator by
     * @param operator The operator to add
     */
    public void addOperator(String code, Operator operator) {
        operators.put(code, operator);
    }

    /**
     * Adds a requirement. Will overwrite an existing requirement.
     * @param code The code to identify the requirement by
     * @param requirement The requirement to add
     */
    public void addRequirement(String code, Requirement requirement) {
        requirements.put(code, requirement);
    }

    /**
     * Removes an operator.
     * @param code The code to the operator
     */
    public void removeOperator(String code) {
        operators.remove(code);
    }

    /**
     * Removes a requirement.
     * @param code The code to the requirement
     */
    public void removeRequirement(String code) {
        requirements.remove(code);
    }

    /**
     * Checks whether the code has already been assigned to an operator.
     * @param code The code to check
     * @return <code>true</code> if the code is reserved
     */
    public boolean isOpCodeReserved(String code) {
        return operators.containsKey(code);
    }

    /**
     * Checks whether the code has already been assigned to a requirement.
     * @param code The code to check
     * @return <code>true</code> if the code is reserved
     */
    public boolean isReqCodeReserved(String code) {
        return requirements.containsKey(code);
    }

    /**
     * The main method to use. Converts a json formatted scenario to a scenario.
     * @param jsonScenario The scenario to convert
     * @return The converted scenario
     * @throws ScenarioLoadFailedException If anything goes wrong
     */
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
