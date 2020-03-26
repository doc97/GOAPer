package algorithms;

import datastructures.DynamicArray;
import datastructures.IntComparable;
import model.*;
import org.omg.PortableServer.DynamicImplementation;

/**
 * The class is used as a node in the planning algorithms.
 * <p/>
 * Created by Daniel Riissanen on 2.4.2018.
 */
public class SubPlan implements IntComparable<SubPlan> {

    /** The cost of the current sub plan */
    private int cost;

    /** The current state after executing all actions on the start state */
    private State state;

    /** The goal that the algorithm is trying to reach */
    private Goal goal;

    /** A list of actions that the sub plan currently has */
    private DynamicArray<Action> actions;

    /** The additional requirements added by actions */
    private DynamicArray<DynamicArray<Precondition>> requirements;

    /** The effects added by actions */
    private DynamicArray<Postcondition> effects;

    /**
     * Class constructor initializing empty sub plan.
     * @param state The current state
     * @param goal The goal
     */
    public SubPlan(State state, Goal goal) {
        this(state, goal,
                new DynamicArray<>(),
                new DynamicArray<>(),
                new DynamicArray<>(),
                0);
    }

    /**
     * Class constructor initializing variables.
     * @param state The current state
     * @param goal The goal
     * @param actions The list of actions
     * @param requirements A list of preconditions for each action
     * @param effects A list of postconditions, one for each action
     * @param cost The cost
     */
    public SubPlan(State state, Goal goal, DynamicArray<Action> actions,
                   DynamicArray<DynamicArray<Precondition>> requirements,
                   DynamicArray<Postcondition> effects, int cost) {
        this.cost = cost;
        this.state = state == null ? new State() : state;
        this.goal = goal == null ? new Goal() : goal;
        this.actions = actions == null ? new DynamicArray<>() : actions;
        this.requirements = requirements == null ? new DynamicArray<>() : requirements;
        this.effects = effects == null ? new DynamicArray<>() : effects;

        if (this.requirements.isEmpty()) {
            this.requirements.add(new DynamicArray<>());
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
        this.cost = other.cost;
        this.state = new State(other.state);
        this.goal = new Goal(other.goal);
        this.actions = new DynamicArray<>(other.actions);
        this.requirements = new DynamicArray<>(other.requirements);
        this.effects = new DynamicArray<>(other.effects);
    }

    /**
     * Adds the action, requirements and effects to the plan.
     * @param action The action to add
     */
    public void addAction(Action action) {
        actions.add(action);
        requirements.add(new DynamicArray<>());
        for (Precondition condition : action.getPreconditions())
            requirements.get(requirements.count() - 1).add(condition);
        cost += action.getCost() + 1;
    }

    /**
     * Determines if the action would benefit the current requirement.
     * @param action The action to check
     * @return <code>true</code> if the action contributes towards the current requirement, <code>false</code>
     * otherwise
     */
    public boolean isGoodAction(Action action) {
        Precondition currentReq = goal;
        State oldState = new State(state);
        State currentState = oldState;
        for (int i = actions.count() - 1; i >= 0; i--) {
            actions.get(i).getPostcondition().activate(oldState);
            float totalDeficit = 0;
            for (int j = 0; j < requirements.get(i + 1).count(); j++) {
                Precondition requirement = requirements.get(i + 1).get(j);
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
     * Checks if the plan is executable. The conditions are that all requirements should be met and that
     * all actions are valid, meaning that their preconditions are met before executing them.
     * @param start The start state from which the sub plan will be executed
     * @return <code>true</code> if the sub plan is valid, <code>false</code> otherwise
     */
    public boolean isValidPlan(State start) {
        State testState = new State(start);
        Action[] actions = getActions();
        for (int i = actions.length - 1; i >= 0; i--) {
            Action action = actions[i];
            if (!action.canExecute(testState))
                return false;

            action.execute(testState);
        }
        return goal.getDeficitCost(testState) == 0;
    }

    /**
     * Returns the sum of the deficit cost of all of the requirements.
     * @return The total deficit
     */
    public float getDeficitCost() {
        float total = 0;
        for (int i = 0; i < requirements.count(); i++) {
            for (int j = 0; j < requirements.get(i).count(); j++) {
                total += requirements.get(i).get(j).getDeficitCost(state);
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

    public Action[] getActions() {
        return actions.asArray(new Action[actions.count()]);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof SubPlan))
            return false;

        SubPlan o = (SubPlan) other;

        if (requirements.count() != o.requirements.count())
            return false;

        for (int i = 0; i < requirements.count(); i++) {
            if (requirements.get(i).count() != o.requirements.get(i).count())
                return false;
        }

        return getCost() == o.getCost() &&
                getDeficitCost() == o.getDeficitCost() &&
                state.equals(o.state) &&
                goal.isEqual(o.goal, state);
    }

    /**
     * Compares the sub plan to another one, based on the cost.
     * @param other The object to compare with
     * @return 1 if the cost is higher than other's, 0 if equal, -1 if lower
     */
    @Override
    public int compare(SubPlan other) {
        float xValue = getDeficitCost() + getCost();
        float yValue = other.getDeficitCost() + other.getCost();
        return (int) Math.signum(xValue - yValue);
    }
}
