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

    @Override
    public Plan getBestPlan(List<Plan> plans) {
        PriorityQueue<Plan> planQueue = new PriorityQueue<>(new PlanComparator());
        planQueue.addAll(plans);
        return plans.isEmpty() ? new Plan() : planQueue.poll();
    }

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

                        if (AlgorithmUtils.isValidPlan(newSubPlan)) {
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
                returnPlans.add(new Plan(actionArray, subPlan.getCost()));
            }
        }
        return returnPlans;
    }
}
