package algorithms;

import model.Action;
import model.Goal;
import model.Plan;
import model.State;

/**
 * Created by Daniel Riissanen on 16.3.2018.
 */
public interface PlanningAlgorithm {
    /**
     * Returns the best plan from an array of available plans to choose from.
     * @param plans The array of plans to choose from
     * @return The best plan
     * @see Plan
     */
    Plan getBestPlan(Plan[] plans);

    /**
     * Returns an array of plans
     * @param start The start state
     * @param goal The goal
     * @param actions The available actions to use
     * @return An array of plans
     * @see Plan
     */
    Plan[] formulatePlans(State start, Goal goal, Action[] actions);
}
