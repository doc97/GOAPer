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
class AlgorithmUtils {
    static SubPlan getNextPlan(SubPlan current, Action action) {
        State newState = new State(current.getState());
        Goal newGoal = new Goal(current.getGoal());
        int newCost = current.getCost() + 1;

        action.execute(newState);
        List<Action> newActions = new ArrayList<>(current.getActions());
        newActions.add(action);

        newGoal.addRequirement(action.getPrecondition());
        return new SubPlan(newState, newGoal, newActions, newCost);
    }

    static boolean isGoodAction(SubPlan current, Action action) {
        State newState = new State(current.getState());
        action.execute(newState);

        int oldRequirements = current.getGoal().getUnsatisfiedRequirementCount(current.getState());
        int newRequirements = current.getGoal().getUnsatisfiedRequirementCount(newState);
        return newRequirements < oldRequirements;
    }

    static boolean isValidSubPlan(SubPlan plan) {
        return plan.getGoal().isSatisfied(plan.getState());
    }

    static boolean isValidPlan(State start, Goal goal, Plan plan) {
        State testState = new State(start);
        for (Action a : plan.getActions()) {
            if (!a.canExecute(testState))
                return false;

            a.execute(testState);
        }

        return goal.isSatisfied(testState);
    }
}
