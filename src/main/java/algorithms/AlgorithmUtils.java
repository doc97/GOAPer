package algorithms;

import model.Action;
import model.Goal;
import model.Plan;
import model.State;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel Riissanen on 2.4.2018.
 */
public class AlgorithmUtils {

    public SubPlan getNextPlan(SubPlan current, Action action) {
        State newState = new State(current.getState());
        Goal newGoal = new Goal(current.getGoal());
        int newCost = current.getCost() + 1;

        action.execute(newState);
        List<Action> newActions = new ArrayList<>(current.getActions());
        newActions.add(action);

        newGoal.addRequirement(action.getPrecondition());
        return new SubPlan(newState, newGoal, newActions, newCost);
    }

    public boolean isGoodAction(SubPlan current, Action action) {
        State newState = new State(current.getState());
        action.execute(newState);

        float oldRequirements = current.getGoal().getUnsatisfiedRequirementCost(current.getState());
        float newRequirements = current.getGoal().getUnsatisfiedRequirementCost(newState);
        return newRequirements < oldRequirements;
    }

    public boolean isValidSubPlan(SubPlan plan) {
        return plan.getGoal().getDeficitCost(plan.getState()) == 0;
    }

    public boolean isValidPlan(State start, Goal goal, Plan plan) {
        State testState = new State(start);
        for (Action a : plan.getActions()) {
            if (!a.canExecute(testState))
                return false;

            a.execute(testState);
        }

        return goal.getDeficitCost(testState) == 0;
    }
}
