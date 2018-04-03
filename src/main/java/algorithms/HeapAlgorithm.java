package algorithms;

import model.Action;
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
    public List<Plan> formulatePlans(State start, State goal, Action[] actions) {
        PriorityQueue<SubPlan> plans = new PriorityQueue<>(2, new SubPlanComparator());
        PriorityQueue<SubPlan> readyPlans = new PriorityQueue<>(2, new SubPlanComparator());
        List<SubPlan> plansToAdd = new ArrayList<>();

        plans.add(new SubPlan(start, goal, new ArrayList<>(), 0));

        while (!plans.isEmpty()) {
            while (!plans.isEmpty()) {
                SubPlan current = plans.poll();
                if (current == null)
                    continue;

                for (Action action : actions) {
                    if (AlgorithmUtils.isGoodAction(current, action)) {
                        SubPlan newSubPlan = AlgorithmUtils.getNextPlan(current, action);

                        if (AlgorithmUtils.isValidSubPlan(newSubPlan)) {
                            readyPlans.add(newSubPlan);
                            continue;
                        }

                        // Only add a plan that results in a distinct state
                        boolean isUniquePlan = true;
                        for (SubPlan existingPlan : plansToAdd) {
                            if (existingPlan.equals(newSubPlan)) {
                                isUniquePlan = false;
                                break;
                            }
                        }

                        if (isUniquePlan)
                            plansToAdd.add(newSubPlan);
                    }
                }
            }
            plans.addAll(plansToAdd);
            plansToAdd.clear();
        }

        List<Plan> returnPlans = new ArrayList<>();
        while (!readyPlans.isEmpty()) {
            SubPlan subPlan = readyPlans.poll();
            if (subPlan != null) {
                Collections.reverse(subPlan.getActions());
                Action[] actionArray = new Action[subPlan.getActions().size()];
                subPlan.getActions().toArray(actionArray);
                Plan plan = new Plan(actionArray, subPlan.getCost());
                if (AlgorithmUtils.isValidPlan(start, goal, plan))
                    returnPlans.add(plan);
            }
        }
        return returnPlans;
    }
}
