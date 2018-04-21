package algorithms;

import model.Action;
import model.Goal;
import model.Plan;
import model.State;

import java.util.List;

/**
 * Created by Daniel Riissanen on 16.3.2018.
 */
public interface PlanningAlgorithm {
    /**
     * Returns the best plan from a list of available plans to choose from.
     * @param plans The list of plans to choose from
     * @return The best plan
     * @see Plan
     */
    Plan getBestPlan(List<Plan> plans);

    /**
     * Returns a list of plans
     * @param start The start state
     * @param goal The goal
     * @param actions The available actions to use
     * @return A list of plans
     * @see Plan
     */
    List<Plan> formulatePlans(State start, Goal goal, Action[] actions);
}
