package algorithms;

import datastructures.DynamicArray;
import datastructures.MinHeap;
import model.Action;
import model.Goal;
import model.Plan;
import model.State;

/**
 * Algorithm that uses a heap to optimize searching.
 * <p/>
 * Created by Daniel Riissanen on 2.4.2018.
 */
public class HeapAlgorithm implements PlanningAlgorithm {

    /**
     * Returns the best plan from a list of available plans to choose from.
     * @param plans The list of plans to choose from
     * @return The plan with the minimal cost or
     * an empty plan if no plans were given
     * @see Plan
     */
    @Override
    public Plan getBestPlan(Plan[] plans) {
        MinHeap<Plan> planQueue = new MinHeap<>();
        planQueue.addAll(plans);
        return plans.length == 0 ? new Plan(false) : planQueue.poll();
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
        MinHeap<SubPlan> plans = new MinHeap<>(2);

        SubPlan startPlan = new SubPlan(start, goal, new DynamicArray<>(), new DynamicArray<>(), new DynamicArray<>(),
                0);
        plans.add(startPlan);

        while (!plans.isEmpty()) {
            SubPlan current = plans.poll();

            if (!startPlan.equals(current) && current.isValidPlan(start)) {
                DynamicArray<Plan> returnPlans = new DynamicArray<>();
                Plan plan = new Plan(current, true);
                returnPlans.add(plan);
                returnPlans.addAll(getPlans(plans, start));
                return returnPlans.asArray(new Plan[returnPlans.count()]);
            }

            for (Action action : actions) {
                if (current.isGoodAction(action)) {
                    SubPlan newSubPlan = new SubPlan(current);
                    newSubPlan.addAction(action);
                    plans.add(newSubPlan);
                }
            }

            plans = trimPlans(plans, 1000);
        }

        return new Plan[0];
    }

    /**
     * Converts a heap containing sub plans to a list containing plans
     * @param subPlans The heap to convert
     * @param start The start state to check plan completeness with
     * @return The converted list
     */
    private DynamicArray<Plan> getPlans(MinHeap<SubPlan> subPlans, State start) {
        DynamicArray<Plan> plans = new DynamicArray<>();
        while (!subPlans.isEmpty()) {
            SubPlan subPlan = subPlans.poll();
            Plan plan = new Plan(subPlan, subPlan.isValidPlan(start));
            if (!plan.isEmpty())
                plans.add(plan);
        }
        return plans;
    }

    /**
     * Trims the heap to a certain size.
     * @param subPlans The heap to trim
     * @param limit The max size of the heap
     * @return The trimmed heap
     */
    private MinHeap<SubPlan> trimPlans(MinHeap<SubPlan> subPlans, int limit) {
        if (subPlans.count() <= limit)
            return subPlans;

        MinHeap<SubPlan> newQueue = new MinHeap<>();
        for (int i = 0; i < limit; i++)
            newQueue.add(subPlans.poll());
        return newQueue;
    }
}
