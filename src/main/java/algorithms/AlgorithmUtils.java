package algorithms;

import model.Action;
import model.Goal;
import model.Plan;
import model.State;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Daniel Riissanen on 2.4.2018.
 */
public class AlgorithmUtils {

    public SubPlan getNextSubPlan(SubPlan current, Action action) {
        State newState = new State(current.getState());
        Goal newGoal = new Goal(current.getGoal());
        int newCost = current.getCost() + 1;

        action.execute(newState);
        List<Action> newActions = new ArrayList<>(current.getActions());
        newActions.add(action);

        newGoal.addAdditionalRequirement(action.getPrecondition());
        return new SubPlan(newState, newGoal, newActions, newCost);
    }

    public boolean isGoodAction(SubPlan current, Action action) {
        State newState = new State(current.getState());
        action.execute(newState);

        float oldRequirements = current.getGoal().getTotalDeficitCost(current.getState());
        float newRequirements = current.getGoal().getTotalDeficitCost(newState);
        return newRequirements < oldRequirements;
    }

    public boolean isValidSubPlan(SubPlan plan, State start) {
        State testState = new State(start);
        List<Action> actions = plan.getActions();
        for (int i = actions.size() - 1; i >= 0; i--) {
            Action action = actions.get(i);
            if (!action.canExecute(testState))
                return false;

            action.execute(testState);
        }
        return plan.getGoal().getDeficitCost(testState) == 0;
    }

    public boolean isUniqueSubPlan(SubPlan plan, List<SubPlan> existingPlans) {
        for (SubPlan existingPlan : existingPlans) {
            if (existingPlan.equals(plan))
                return false;
        }

        return true;
    }

    public Plan convertToPlan(SubPlan subPlan) {
        if (subPlan == null)
            return new Plan();
        Collections.reverse(subPlan.getActions());
        Action[] actionArray = new Action[subPlan.getActions().size()];
        subPlan.getActions().toArray(actionArray);
        return new Plan(actionArray, subPlan.getCost());
    }
}
