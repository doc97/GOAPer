package algorithms;

import model.Action;
import model.Goal;
import model.Plan;
import model.State;

import java.util.*;

/**
 * Created by Daniel Riissanen on 2.4.2018.
 */
public class HeapAlgorithm implements PlanningAlgorithm {

    private class PlanComparator implements Comparator<Plan> {
        @Override
        public int compare(Plan x, Plan y) {
            return x.getCost() - y.getCost();
        }
    }

    private class SubPlanComparator implements Comparator<SubPlan> {
        @Override
        public int compare(SubPlan x, SubPlan y) {
            return x.getCost() - y.getCost();
        }
    }

    private AlgorithmUtils utilities;

    public HeapAlgorithm() {
        this(null);
    }

    public HeapAlgorithm(AlgorithmUtils utilities) {
        this.utilities = utilities == null ? new AlgorithmUtils() : utilities;
    }

    /**
     * @param plans The list of plans to choose from
     * @return The plan with the minimal cost or
     * an empty plan if no plans were given
     */
    @Override
    public Plan getBestPlan(List<Plan> plans) {
        PriorityQueue<Plan> planQueue = new PriorityQueue<>(new PlanComparator());
        planQueue.addAll(plans);
        return plans.isEmpty() ? new Plan() : planQueue.poll();
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
        PriorityQueue<SubPlan> readyPlans = new PriorityQueue<>(2, new SubPlanComparator());
        List<SubPlan> plansToAdd = new ArrayList<>();

        plans.add(new SubPlan(start, goal, new ArrayList<>(), 0));

        while (!plans.isEmpty()) {
            while (!plans.isEmpty()) {
                SubPlan current = plans.poll();

                for (Action action : actions) {
                    if (utilities.isGoodAction(current, action)) {
                        SubPlan newSubPlan = utilities.getNextSubPlan(current, action);

                        if (utilities.isValidSubPlan(newSubPlan, start))
                            readyPlans.add(newSubPlan);
                        else if (utilities.isUniqueSubPlan(newSubPlan, plansToAdd))
                            plansToAdd.add(newSubPlan);
                    }
                }
            }

            plans.addAll(plansToAdd);
            plansToAdd.clear();
        }

        return getPlans(readyPlans);
    }

    public AlgorithmUtils getUtilities() {
        return utilities;
    }

    private List<Plan> getPlans(PriorityQueue<SubPlan> subPlans) {
        List<Plan> plans = new ArrayList<>();
        while (!subPlans.isEmpty()) {
            SubPlan subPlan = subPlans.poll();
            Plan plan = utilities.convertToPlan(subPlan);
            plans.add(plan);
        }
        return plans;
    }
}
