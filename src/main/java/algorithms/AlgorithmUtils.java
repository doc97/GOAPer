package algorithms;

import model.Action;
import model.State;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel Riissanen on 2.4.2018.
 */
class AlgorithmUtils {
    static SubPlan getNextPlan(SubPlan current, Action action) {
        State newState = new State(current.getState());
        State newGoal = new State(current.getGoal());
        int newCost = current.getCost() + 1;

        action.execute(newState);
        List<Action> newActions = new ArrayList<>(current.getActions());
        newActions.add(action);

        State requiredState = action.getPrecondition().getState();
        for (String key : requiredState.getKeys())
            newGoal.addKey(key, requiredState.query(key));

        return new SubPlan(newState, newGoal, newActions, newCost);
    }

    static boolean isGoodAction(SubPlan current, Action action) {
        State newState = new State(current.getState());
        action.execute(newState);

        for (String key : current.getGoal().getKeys()) {
            if (newState.query(key) == current.getGoal().query(key) &&
                    current.getState().query(key) != current.getGoal().query(key))
                return true;
        }

        return false;
    }

    static boolean isValidPlan(SubPlan plan) {
        for (String key : plan.getGoal().getKeys()) {
            if (plan.getState().query(key) != plan.getGoal().query(key))
                return false;
        }

        return true;
    }
}
