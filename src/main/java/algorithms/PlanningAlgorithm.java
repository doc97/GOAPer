package algorithms;

import model.Action;
import model.Plan;
import model.State;

/**
 * Created by Daniel Riissanen on 16.3.2018.
 */
public interface PlanningAlgorithm {
    Plan formulatePlan(State start, State goal, Action[] actions);
}
