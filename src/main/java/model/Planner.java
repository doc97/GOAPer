package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Daniel Riissanen on 16.3.2018.
 */
public class Planner {

    private class Plan {
        State state;
        List<Action> actions;

        private Plan(State state, List<Action> actions) {
            this.state = state;
            this.actions = actions;
        }
    }

    public Action[] formulatePlan(State start, State end, Action[] actions) {
        List<Plan> plans = new ArrayList<>();
        List<Plan> plansToAdd = new ArrayList<>();

        plans.add(new Plan(start, new ArrayList<>()));

        int counter = 0;
        do {
            for (Plan currentPlan : plans) {
                for (Action action : actions) {
                    State temp = new State(currentPlan.state);
                    boolean canExecute = action.canExecute(temp);
                    action.execute(temp);
                    if (isValidPlan(temp, end)) {
                        List<Action> currentActions = new ArrayList<>(currentPlan.actions);
                        currentActions.add(action);

                        if (canExecute) {
                            Collections.reverse(currentActions);
                            return currentActions.toArray(new Action[currentActions.size()]);
                        }

                        plansToAdd.add(new Plan(temp, currentActions));
                    }
                }
            }

            plans.clear();
            plans.addAll(plansToAdd);
            plansToAdd.clear();


        } while (!plans.isEmpty() && ++counter < 5);

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
