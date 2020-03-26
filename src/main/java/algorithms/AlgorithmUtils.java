package algorithms;

import datastructures.DynamicArray;
import model.Action;
import model.Plan;
import model.State;

/**
 * Class containing common methods used by PlanningAlgorithms. This class can be extended and overridden
 * to customize the behaviour of the algorithms.
 * <p/>
 * Created by Daniel Riissanen on 2.4.2018.
 */
public class AlgorithmUtils {

    /**
     * Checks whether a plan already exists in a list of plans. This is useful for optimization purposes.
     * @param plan The sub plan to check
     * @param existingPlans The list of existing sub plans
     * @return <code>true</code> if the sub plan is unique, <code>false</code> otherwise
     */
    public boolean isUniqueSubPlan(SubPlan plan, DynamicArray<SubPlan> existingPlans) {
        for (int i = 0; i < existingPlans.count(); i++) {
            if (existingPlans.get(i).equals(plan))
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

        Action[] actionArray = new Action[subPlan.getActions().length];
        for (int i = 0; i < actionArray.length; ++i)
            actionArray[i] = subPlan.getActions()[subPlan.getActions().length - 1 - i];

        return new Plan(actionArray, isComplete);
    }
}
