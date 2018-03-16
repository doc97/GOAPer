package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Daniel Riissanen on 16.3.2018.
 */
public class Planner {

    private class Plan {
        State goal;
        State state;
        List<Action> actions;

        private Plan(State state, State goal, List<Action> actions) {
            this.state = state;
            this.goal = goal;
            this.actions = actions;
        }
    }

    public Action[] formulatePlan(State start, State goal, Action[] actions) {
        List<Plan> plans = new ArrayList<>();
        List<Plan> plansToAdd = new ArrayList<>();

        plans.add(new Plan(start, goal, new ArrayList<>()));

        do {
            for (Plan currentPlan : plans) {
                for (Action action : actions) {
                    State newState = new State(currentPlan.state);
                    State newGoal = new State(currentPlan.goal);
                    action.execute(newState);

                    if (isValidPlan(newState, newGoal)) {
                        List<Action> newActions = new ArrayList<>(currentPlan.actions);
                        newActions.add(action);

                        if (action.canExecute(currentPlan.state)) {
                            Collections.reverse(newActions);
                            return newActions.toArray(new Action[newActions.size()]);
                        } else {
                            State requiredState = action.getPreCondition().getState();
                            for (String key : requiredState.getKeys())
                                newGoal.addKey(key, requiredState.query(key));
                        }

                        plansToAdd.add(new Plan(newState, newGoal, newActions));
                    }
                }
            }

            plans.clear();
            plans.addAll(plansToAdd);
            plansToAdd.clear();
        } while (!plans.isEmpty());

        return new Action[0];
    }

    private boolean isValidPlan(State current, State goal) {
        for (String key : goal.getKeys()) {
            if (current.query(key) != goal.query(key))
                return false;
        }
        return true;
    }
}
