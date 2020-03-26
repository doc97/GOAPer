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

    private AlgorithmUtils utilities;

    /**
     * Class constructor using the default algorithm utilities.
     */
    public NaiveAlgorithm() {
        this(null);
    }

    /**
     *
     * Class constructor specifying an algorithm utility class to use.
     * @param utilities The algorithm utilities to use
     * @see AlgorithmUtils
     */
    public NaiveAlgorithm(AlgorithmUtils utilities) {
        this.utilities = utilities == null ? new AlgorithmUtils() : utilities;
    }

    /**
     * Returns the best plan from a list of available plans to choose from.
     * @param plans The list of plans to choose from
     * @return The plan with the least actions required, regardless of the cost
     * or an empty plan if no plans were given
     */
    @Override
    public Plan getBestPlan(Plan[] plans) {
        Plan minActionPlan = null;
        for (int i = 0; i < plans.length; i++) {
            Plan plan = plans[i];
            if (plan.isComplete() && (minActionPlan == null || plan.getActions().length < minActionPlan.getActions().length)) {
                minActionPlan = plan;
            }
        }
        return minActionPlan == null ? new Plan(false) : minActionPlan;
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
                for (Action action : actions) {
                    if (currentSubPlan.isGoodAction(action)) {
                        SubPlan newSubPlan = new SubPlan(currentSubPlan);
                        newSubPlan.addAction(action);

                        if (newSubPlan.isValidPlan(start)) {
                            HashSet<Plan> plans = new HashSet<>();
                            plans.add(utilities.convertToPlan(newSubPlan, true));
                            for (int j = 0; j < plansToAdd.count(); j++) {
                                Plan plan = utilities.convertToPlan(plansToAdd.get(j), false);
                                if (!plan.isEmpty())
                                    plans.add(plan);
                            }
                            for (int j = 0; j < subPlans.count(); j++) {
                                SubPlan subPlan = subPlans.get(j);
                                Plan plan = utilities.convertToPlan(subPlan, false);
                                if (!plan.isEmpty() && !subPlan.equals(currentSubPlan))
                                    plans.add(plan);
                            }

                            return plans.asArray(new Plan[plans.count()]);
                        } else if (!plansToAdd.contains(newSubPlan)) {
                            plansToAdd.add(newSubPlan);
                        }
                    }
                }
            }

            subPlans.removeAll();
            subPlans.addAll(plansToAdd);
            plansToAdd.removeAll();
        }

        return new Plan[0];
    }

    public AlgorithmUtils getUtilities() {
        return utilities;
    }
}
