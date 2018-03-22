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
     * @return The plan to reach the goal, or an empty Plan if no plan could be formed
     */
    @Override
    public Plan formulatePlan(State start, State goal, Action[] actions) {
        List<SubPlan> subPlans = new ArrayList<>();
        List<SubPlan> plansToAdd = new ArrayList<>();

        subPlans.add(new SubPlan(start, goal, new ArrayList<>(), 0));

        do {
            for (SubPlan currentSubPlan : subPlans) {
                for (Action action : actions) {
                    if (isGoodAction(currentSubPlan, action)) {
                        State newState = new State(currentSubPlan.state);
                        State newGoal = new State(currentSubPlan.goal);
                        int newCost = currentSubPlan.cost + action.getCost();

                        action.execute(newState);
                        List<Action> newActions = new ArrayList<>(currentSubPlan.actions);
                        newActions.add(action);

                        State requiredState = action.getPrecondition().getState();
                        for (String key : requiredState.getKeys())
                            newGoal.addKey(key, requiredState.query(key));

                        SubPlan newSubPlan = new SubPlan(newState, newGoal, newActions, newCost);

                        if (isValidPlan(newSubPlan)) {
                            Collections.reverse(newActions);
                            Action[] actionArray = new Action[newActions.size()];
                            newActions.toArray(actionArray);
                            return new Plan(actionArray, newCost);
                        }

                        // Only add a plan that results in a distinct state
                        boolean planExists = false;
                        for (SubPlan existingPlan : plansToAdd) {
                            if (existingPlan.state.equals(newSubPlan.state)) {
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

        return new Plan();
    }

    private boolean isGoodAction(SubPlan current, Action action) {
        State newState = new State(current.state);
        action.execute(newState);

        for (String key : current.goal.getKeys()) {
            if (current.goal.query(key) == newState.query(key) && current.state.query(key) != current.goal.query(key))
                return true;
        }

        return false;
    }

    private boolean isValidPlan(SubPlan plan) {
        for (String key : plan.goal.getKeys()) {
            if (plan.state.query(key) != plan.goal.query(key))
                return false;
        }

        return true;
    }
}
