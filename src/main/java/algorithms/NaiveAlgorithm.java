package algorithms;

import model.Action;
import model.Goal;
import model.Plan;
import model.State;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel Riissanen on 16.3.2018.
 */
public class NaiveAlgorithm implements PlanningAlgorithm {

    private AlgorithmUtils utilities;

    public NaiveAlgorithm() {
        this(null);
    }

    public NaiveAlgorithm(AlgorithmUtils utilities) {
        this.utilities = utilities == null ? new AlgorithmUtils() : utilities;
    }

    /**
     * @param plans The list of plans to choose from
     * @return The plan with the least actions required, regardless of the cost
     * or an empty plan if no plans were given
     */
    @Override
    public Plan getBestPlan(List<Plan> plans) {
        Plan minActionPlan = null;
        for (Plan plan : plans) {
            if (minActionPlan == null || plan.getActions().length < minActionPlan.getActions().length) {
                minActionPlan = plan;
            }
        }
        return plans.isEmpty() ? new Plan() : minActionPlan;
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
        List<Plan> plans = new ArrayList<>();
        List<SubPlan> subPlans = new ArrayList<>();
        List<SubPlan> plansToAdd = new ArrayList<>();

        subPlans.add(new SubPlan(start, goal, new ArrayList<>(), 0));

        do {
            for (SubPlan currentSubPlan : subPlans) {
                for (Action action : actions) {
                    if (utilities.isGoodAction(currentSubPlan, action)) {
                        SubPlan newSubPlan = utilities.getNextSubPlan(currentSubPlan, action);

                        if (utilities.isValidSubPlan(start, newSubPlan)) {
                            plans.add(utilities.convertToPlan(newSubPlan));
                            continue;
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

        return plans;
    }
}
