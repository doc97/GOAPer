package algorithms;

import model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * A sub plan is different from a {@link model.Plan} by also containing the current state and goal.
 * The class is used as a node in the planning algorithms.
 * <p/>
 * Created by Daniel Riissanen on 2.4.2018.
 */
public class SubPlan {

    /** The cost of the current sub plan */
    private int cost;

    /** The current state after executing all actions on the start state */
    private State state;

    /** The goal that the algorithm is trying to reach */
    private Goal goal;

    /** A list of actions that the sub plan currently has */
    private List<Action> actions;

    /** The additional requirements added by actions */
    private List<List<Precondition>> requirements;

    /** The effects added by actions */
    private List<Postcondition> effects;

    /**
     * Class constructor initializing variables.
     * @param state The current state
     * @param goal The goal
     * @param actions The list of actions
     * @param requirements A list of preconditions for each action
     * @param effects A list of postconditions, one for each action
     * @param cost The cost
     */
    public SubPlan(State state, Goal goal, List<Action> actions,
                   List<List<Precondition>> requirements, List<Postcondition> effects, int cost) {
        this.cost = cost;
        this.state = state == null ? new State() : state;
        this.goal = goal == null ? new Goal() : goal;
        this.actions = actions == null ? new ArrayList<>() : actions;
        this.requirements = requirements == null ? new ArrayList<>() : requirements;
        this.effects = effects == null ? new ArrayList<>() : effects;

        if (this.requirements.isEmpty()) {
            this.requirements.add(new ArrayList<>());
            this.requirements.get(0).add(this.goal);
        }
        if (this.effects.isEmpty())
            this.effects.add(s -> {});
    }

    /**
     * Copy constructor.
     * @param other The other object to copy
     */
    public SubPlan(SubPlan other) {
        if (other == null) {
            this.cost = 0;
            this.state = new State();
            this.goal = new Goal();
            this.actions = new ArrayList<>();
            this.requirements = new ArrayList<>();
            this.effects = new ArrayList<>();
            this.requirements.add(new ArrayList<>());
            this.requirements.get(0).add(goal);
            this.effects.add(s -> {});
        } else {
            this.cost = other.cost;
            this.state = new State(other.state);
            this.goal = new Goal(other.goal);
            this.actions = new ArrayList<>(other.actions);
            this.requirements = new ArrayList<>(other.requirements);
            this.effects = new ArrayList<>(other.effects);
        }
    }

    /**
     * Adds the action, requirements and effects to the plan.
     * @param action The action to add
     */
    public void addAction(Action action) {
        actions.add(action);
        requirements.add(new ArrayList<>());
        for (Precondition condition : action.getPreconditions())
            requirements.get(requirements.size() - 1).add(condition);
        cost += action.getCost() + 1;
    }

    /**
     * Determines if the action would benefit the current requirement.
     * @param action The action to check
     * @return <code>true</code> if the action contributes towards the current requirement, <code>false</code>
     * otherwise
     */
    public boolean isGoodAction(Action action) {
        if (requirements.isEmpty())
            return false;

        Precondition currentReq = goal;
        State oldState = new State(state);
        State currentState = oldState;
        for (int i = actions.size() - 1; i >= 0; i--) {
            actions.get(i).getPostcondition().activate(oldState);
            float totalDeficit = 0;
            for (Precondition requirement : requirements.get(i + 1)) {
                float deficit = requirement.getDeficitCost(oldState);
                totalDeficit += deficit;
                if (deficit != 0) {
                    currentReq = requirement;
                    currentState = new State(oldState);
                }
            }

            if (totalDeficit == 0)
                actions.get(i).getConsumption().activate(oldState);
        }

        State newState = new State(currentState);
        action.getPostcondition().activate(newState);

        float oldDeficit = currentReq.getDeficitCost(currentState);
        float newDeficit = currentReq.getDeficitCost(newState);

        return newDeficit < oldDeficit;
    }

    /**
     * Returns the sum of the deficit cost of all of the requirements.
     * @return The total deficit
     */
    public float getDeficitCost() {
        float total = 0;
        for (List<Precondition> reqList : requirements) {
            for (Precondition precondition : reqList) {
                total += precondition.getDeficitCost(state);
            }
        }
        return total;
    }

    public int getCost() {
        return cost;
    }

    public Goal getGoal() {
        return goal;
    }

    public State getState() {
        return state;
    }

    public List<Action> getActions() {
        return actions;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof SubPlan))
            return false;

        SubPlan o = (SubPlan) other;

        if (requirements.size() != o.requirements.size())
            return false;

        return getCost() == o.getCost() && state.equals(o.state) && goal.isEqual(o.goal, state);
    }
}
