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
    private class SubPlan {
        State goal;
        State state;
        int cost;
        List<Action> actions;

        private SubPlan(State state, State goal, List<Action> actions, int cost) {
            this.state = state;
            this.goal = goal;
            this.actions = actions;
            this.cost = cost;
        }
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
     * @return The plan to reach the goal, or null if no plan could be formed
     */
    @Override
    public Plan formulatePlan(State start, State goal, Action[] actions) {
        List<SubPlan> subPlans = new ArrayList<>();
        List<SubPlan> plansToAdd = new ArrayList<>();

        subPlans.add(new SubPlan(start, goal, new ArrayList<>(), 0));

        do {
            for (SubPlan currentSubPlan : subPlans) {
                for (Action action : actions) {
                    State newState = new State(currentSubPlan.state);
                    State newGoal = new State(currentSubPlan.goal);
                    int newCost = currentSubPlan.cost + action.getCost();
                    action.execute(newState);

                    if (isValidPlan(newState, newGoal)) {
                        List<Action> newActions = new ArrayList<>(currentSubPlan.actions);
                        newActions.add(action);

                        if (action.canExecute(currentSubPlan.state)) {
                            Collections.reverse(newActions);
                            Action[] actionArray = new Action[newActions.size()];
                            newActions.toArray(actionArray);
                            return new Plan(actionArray, 0);
                        } else {
                            State requiredState = action.getPrecondition().getState();
                            for (String key : requiredState.getKeys())
                                newGoal.addKey(key, requiredState.query(key));
                        }

                        plansToAdd.add(new SubPlan(newState, newGoal, newActions, newCost));
                    }
                }
            }

            subPlans.clear();
            subPlans.addAll(plansToAdd);
            plansToAdd.clear();
        } while (!subPlans.isEmpty());

        return new Plan();
    }

    private boolean isValidPlan(State current, State goal) {
        for (String key : goal.getKeys()) {
            if (current.query(key) != goal.query(key))
                return false;
        }
        return true;
    }
}
