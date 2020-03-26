package algorithms;

import datastructures.DynamicArray;
import datastructures.HashSet;
import model.Action;
import model.Goal;
import model.Plan;
import model.State;

/**
 * Basic BFS algorithm.
 * <p/>
 * Created by Daniel Riissanen on 16.3.2018.
 */
public class NaiveAlgorithm implements PlanningAlgorithm {

    /**
     * Returns the best plan from a list of available plans to choose from.
     * @param plans The list of plans to choose from
     * @return The plan with the least actions required, regardless of the cost
     * or an empty plan if no plans were given
     */
    @Override
    public Plan getBestPlan(Plan[] plans) {
        Plan bestActionPlan = null;
        for (Plan plan : plans) {
            boolean isBetter = bestActionPlan == null || plan.getActions().length < bestActionPlan.getActions().length;
            if (plan.isComplete() && isBetter)
                bestActionPlan = plan;
        }
        return bestActionPlan == null ? new Plan(false) : bestActionPlan;
    }

    /**
     * Formulates an array of plans by working it's way backwards from the goal to the starting position.
     * @param start The start state
     * @param goal The goal state
     * @param actions The action space, all actions that can be performed
     * @return An array of all valid plans to reach the goal, or an empty array if no plan could be formed
     */
    @Override
    public Plan[] formulatePlans(State start, Goal goal, Action[] actions) {
        DynamicArray<SubPlan> subPlans = new DynamicArray<>();
        DynamicArray<SubPlan> plansToAdd = new DynamicArray<>();

        subPlans.add(new SubPlan(start, goal, new DynamicArray<>(), new DynamicArray<>(), new DynamicArray<>(), 0));

        while (!subPlans.isEmpty()) {
            for (int i = 0; i < subPlans.count(); i++) {
                SubPlan currentSubPlan = subPlans.get(i);
                boolean foundGoal = expandPlan(currentSubPlan, start, actions, plansToAdd);
                if (foundGoal)
                    return createPlansFromSubPlans(start, currentSubPlan, subPlans, plansToAdd);
            }

            subPlans.removeAll();
            subPlans.addAll(plansToAdd);
            plansToAdd.removeAll();
        }

        return new Plan[0];
    }

    private boolean expandPlan(SubPlan current, State start, Action[] actions,
                            DynamicArray<SubPlan> toAdd) {
        for (Action action : actions) {
            if (current.isGoodAction(action)) {
                SubPlan newSubPlan = new SubPlan(current);
                newSubPlan.addAction(action);

                if (!toAdd.contains(newSubPlan)) {
                    toAdd.add(newSubPlan);
                }

                if (newSubPlan.isValidPlan(start)) {
                    return true;
                }
            }
        }
        return false;
    }

    private Plan[] createPlansFromSubPlans(State start,
                                           SubPlan discard,
                                           DynamicArray<SubPlan> currentSubPlans,
                                           DynamicArray<SubPlan> plansToAdd) {
        HashSet<Plan> plans = new HashSet<>();
        for (int j = 0; j < plansToAdd.count(); j++) {
            Plan plan = new Plan(plansToAdd.get(j), start);
            if (!plan.isEmpty())
                plans.add(plan);
        }
        for (int j = 0; j < currentSubPlans.count(); j++) {
            SubPlan subPlan = currentSubPlans.get(j);
            Plan plan = new Plan(subPlan, start);
            if (!plan.isEmpty() && !subPlan.equals(discard))
                plans.add(plan);
        }

        return plans.asArray(new Plan[plans.count()]);
    }
}
