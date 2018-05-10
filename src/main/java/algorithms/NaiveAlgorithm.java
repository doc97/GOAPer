package algorithms;

import model.Action;
import model.Goal;
import model.Plan;
import model.State;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    public Plan getBestPlan(List<Plan> plans) {
        Plan minActionPlan = null;
        for (Plan plan : plans) {
            if (plan.isComplete() && (minActionPlan == null || plan.getActions().length < minActionPlan.getActions().length)) {
                minActionPlan = plan;
            }
        }
        return minActionPlan == null ? new Plan(false) : minActionPlan;
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
        List<SubPlan> subPlans = new ArrayList<>();
        List<SubPlan> plansToAdd = new ArrayList<>();

        subPlans.add(new SubPlan(start, goal, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0));

        while (!subPlans.isEmpty()) {
            for (SubPlan currentSubPlan : subPlans) {
                for (Action action : actions) {
                    if (currentSubPlan.isGoodAction(action)) {
                        SubPlan newSubPlan = new SubPlan(currentSubPlan);
                        newSubPlan.addAction(action);

                        if (utilities.isValidSubPlan(newSubPlan, start)) {
                            Set<Plan> plans = new HashSet<>();
                            plans.add(utilities.convertToPlan(newSubPlan, true));
                            for (SubPlan subPlan : plansToAdd) {
                                Plan plan = utilities.convertToPlan(subPlan, false);
                                if (!plan.isEmpty())
                                    plans.add(utilities.convertToPlan(subPlan, false));
                            }
                            for (SubPlan subPlan : subPlans) {
                                Plan plan = utilities.convertToPlan(subPlan, false);
                                if (!plan.isEmpty() && !subPlan.equals(currentSubPlan))
                                    plans.add(utilities.convertToPlan(subPlan, false));
                            }
                            return new ArrayList<>(plans);
                        } else if (utilities.isUniqueSubPlan(newSubPlan, plansToAdd)) {
                            plansToAdd.add(newSubPlan);
                        }
                    }
                }
            }

            subPlans.clear();
            subPlans.addAll(plansToAdd);
            plansToAdd.clear();
        }

        return new ArrayList<>();
    }

    public AlgorithmUtils getUtilities() {
        return utilities;
    }
}
