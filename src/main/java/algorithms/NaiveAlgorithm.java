package algorithms;

import model.Action;
import model.Plan;
import model.State;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Daniel Riissanen on 16.3.2018.
 */
public class NaiveAlgorithm implements PlanningAlgorithm {

    @Override
    public Plan getBestPlan(List<Plan> plans) {
        return plans.isEmpty() ? new Plan() : plans.get(0);
    }

    /**
     * Formulates a plan by working it's way backwards from the goal to the starting position.
     * For every sub-plan it checks all actions for an action that would achieve the current sub-goal.
     * It continues until all sub-goals have been achieved (the chain of actions can be performed) or
     * until there are no actions that can achieve all sub goals.<br>
     * <br>
     * It will choose the plan with the least actions required that works, regardless the cost.
     * @param start The start state
     * @param goal The goal state
     * @param actions The action space, all actions that can be performed
     * @return The plan to reach the goal, or an empty Plan if no plan could be formed
     */
    @Override
    public List<Plan> formulatePlans(State start, State goal, Action[] actions) {
        List<SubPlan> subPlans = new ArrayList<>();
        List<SubPlan> plansToAdd = new ArrayList<>();

        subPlans.add(new SubPlan(start, goal, new ArrayList<>(), 0));

        do {
            for (SubPlan currentSubPlan : subPlans) {
                for (Action action : actions) {
                    if (AlgorithmUtils.isGoodAction(currentSubPlan, action)) {
                        SubPlan newSubPlan = AlgorithmUtils.getNextPlan(currentSubPlan, action);

                        if (AlgorithmUtils.isValidPlan(newSubPlan)) {
                            Collections.reverse(newSubPlan.getActions());
                            Action[] actionArray = new Action[newSubPlan.getActions().size()];
                            newSubPlan.getActions().toArray(actionArray);
                            List<Plan> plans = new ArrayList<>();
                            plans.add(new Plan(actionArray, newSubPlan.getCost()));
                            return plans;
                        }

                        // Only add a plan that results in a distinct state
                        boolean planExists = false;
                        for (SubPlan existingPlan : plansToAdd) {
                            if (existingPlan.equals(newSubPlan)) {
                                planExists = true;
                                break;
                            }
                        }

                        if (!planExists)
                            plansToAdd.add(newSubPlan);
                    }
                }
            }

            subPlans.clear();
            subPlans.addAll(plansToAdd);
            plansToAdd.clear();
        } while (!subPlans.isEmpty());

        return new ArrayList<>();
    }
}
