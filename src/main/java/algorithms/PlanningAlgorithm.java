package algorithms;

import model.Action;
import model.Plan;
import model.State;

import java.util.List;

/**
 * Created by Daniel Riissanen on 16.3.2018.
 */
public interface PlanningAlgorithm {
    Plan getBestPlan(List<Plan> plans);
    List<Plan> formulatePlans(State start, State goal, Action[] actions);
}
