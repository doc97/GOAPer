package algorithms;

import model.Action;
import model.Plan;
import model.State;

import java.util.List;

/**
 * Class containing common methods used by PlanningAlgorithms. This class can be extended and overridden
 * to customize the behaviour of the algorithms.
 * <p/>
 * Created by Daniel Riissanen on 2.4.2018.
 */
public class AlgorithmUtils {

    /**
     * Checks if the plan is executable. The conditions are that all requirements should be met and that
     * all actions are valid, meaning that their preconditions are met before executing them.
     * @param plan The sub plan to check
     * @param start The start state from which the sub plan will be executed
     * @return <code>true</code> if the sub plan is valid, <code>false</code> otherwise
     */
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

    /**
     * Checks whether a plan already exists in a list of plans. This is useful for optimization purposes.
     * @param plan The sub plan to check
     * @param existingPlans The list of existing sub plans
     * @return <code>true</code> if the sub plan is unique, <code>false</code> otherwise
     */
    public boolean isUniqueSubPlan(SubPlan plan, List<SubPlan> existingPlans) {
        for (SubPlan existingPlan : existingPlans) {
            if (existingPlan.equals(plan))
                return false;
        }

        return true;
    }

    /**
     * Converts a {@link SubPlan} to a {@link Plan}. If the argument is null an empty plan is returned.
     * @param subPlan The sub plan to convert
     * @param isComplete Indicates if the plan will take you to the goal
     * @return The converted {@link Plan}
     */
    public Plan convertToPlan(SubPlan subPlan, boolean isComplete) {
        if (subPlan == null)
            return new Plan(false);

        Action[] actionArray = new Action[subPlan.getActions().size()];
        for (int i = 0; i < actionArray.length; ++i)
            actionArray[i] = subPlan.getActions().get(subPlan.getActions().size() - 1 - i);

        return new Plan(actionArray, isComplete);
    }
}
