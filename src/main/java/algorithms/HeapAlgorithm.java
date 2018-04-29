package algorithms;

import model.Action;
import model.Goal;
import model.Plan;
import model.State;

import java.util.*;

/**
 * Algorithm that uses a heap to optimize searching.
 * <p/>
 * Created by Daniel Riissanen on 2.4.2018.
 */
public class HeapAlgorithm implements PlanningAlgorithm {

    private class PlanComparator implements Comparator<Plan> {
        @Override
        public int compare(Plan x, Plan y) {
            if (x.isComplete() && !y.isComplete())
                return -1;
            if (y.isComplete() && !x.isComplete())
                return 1;

            return x.getCost() - y.getCost();
        }
    }

    private class SubPlanComparator implements Comparator<SubPlan> {
        @Override
        public int compare(SubPlan x, SubPlan y) {
            float xValue = x.getGoal().getDeficitCost(x.getState()) + x.getCost();
            float yValue = y.getGoal().getDeficitCost(y.getState()) + y.getCost();
            return (int) Math.signum(xValue - yValue);
        }
    }

    private AlgorithmUtils utilities;

    /**
     * Class constructor using the default algorithm utilities.
     */
    public HeapAlgorithm() {
        this(null);
    }

    /**
     * Class constructor specifying an algorithm utility class to use.
     * @param utilities The algorithm utilities to use
     * @see AlgorithmUtils
     */
    public HeapAlgorithm(AlgorithmUtils utilities) {
        this.utilities = utilities == null ? new AlgorithmUtils() : utilities;
    }

    /**
     * Returns the best plan from a list of available plans to choose from.
     * @param plans The list of plans to choose from
     * @return The plan with the minimal cost or
     * an empty plan if no plans were given
     * @see Plan
     */
    @Override
    public Plan getBestPlan(List<Plan> plans) {
        PriorityQueue<Plan> planQueue = new PriorityQueue<>(new PlanComparator());
        planQueue.addAll(plans);
        return plans.isEmpty() ? new Plan(false) : planQueue.poll();
    }

    /**
     * Formulates a list of plans by working it's way backwards from the goal to the starting position.
     * @param start The start state
     * @param goal The goal state
     * @param actions The action space, all actions that can be performed
     * @return A list of all valid plans to reach the goal, or an empty list if no plan could be formed
     */
    @Override
    public List<Plan> formulatePlans(State start, Goal goal, Action[] actions) {
        PriorityQueue<SubPlan> plans = new PriorityQueue<>(2, new SubPlanComparator());
        List<SubPlan> plansToAdd = new ArrayList<>();

        SubPlan startPlan = new SubPlan(start, goal, new ArrayList<>(), 0);
        plans.add(startPlan);

        while (!plans.isEmpty()) {
            SubPlan current = plans.poll();

            if (!startPlan.equals(current) && utilities.isValidSubPlan(current, start)) {
                List<Plan> returnPlans = new ArrayList<>();
                Plan plan = utilities.convertToPlan(current, true);
                returnPlans.add(plan);
                returnPlans.addAll(getPlans(plans, start, utilities));
                return returnPlans;
            }

            for (Action action : actions) {
                if (utilities.isGoodAction(current, action)) {
                    SubPlan newSubPlan = utilities.getNextSubPlan(current, action);
                    if (utilities.isUniqueSubPlan(newSubPlan, plansToAdd)) {
                        plans.add(newSubPlan);
                    }
                }
            }

            plans = trimPlans(plans, 1000);
        }

        return new ArrayList<>();
    }

    public AlgorithmUtils getUtilities() {
        return utilities;
    }

    /**
     * Converts a heap containing sub plans to a list containing plans
     * @param subPlans The heap to convert
     * @param start The start state to check plan completeness with
     * @param utilities Algorithm utility class
     * @return The converted list
     */
    private List<Plan> getPlans(PriorityQueue<SubPlan> subPlans, State start, AlgorithmUtils utilities) {
        List<Plan> plans = new ArrayList<>();
        while (!subPlans.isEmpty()) {
            SubPlan subPlan = subPlans.poll();
            Plan plan = utilities.convertToPlan(subPlan, utilities.isValidSubPlan(subPlan, start));
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
    private PriorityQueue<SubPlan> trimPlans(PriorityQueue<SubPlan> subPlans, int limit) {
        if (subPlans.size() <= limit)
            return subPlans;

        PriorityQueue<SubPlan> newQueue = new PriorityQueue<>(new SubPlanComparator());
        for (int i = 0; i < limit; i++)
            newQueue.add(subPlans.poll());
        return newQueue;
    }
}
