package model;

import algorithms.AlgorithmUtils;
import algorithms.HeapAlgorithm;
import algorithms.NaiveAlgorithm;
import algorithms.PlanningAlgorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The planner manages planning algorithms.
 * <p/>
 * Created by Daniel Riissanen on 16.3.2018.
 */
public class Planner {

    /** A mapping of saved the planning algorithms */
    private HashMap<String, PlanningAlgorithm> algorithms;

    /** The name of the algorithm that is currently in use */
    private String algorithmInUse;

    /** The result of the last algorithm run */
    private List<Plan> plans;

    /** Algorithm utilities to use */
    private AlgorithmUtils utilities;

    /**
     * Class constructor using the default algorithm utilities.
     */
    public Planner() {
        this(null);
    }

    /**
     * Class constructor specifying an algorithm utility class to use.
     * @param utilities The algorithm utilities
     */
    public Planner(AlgorithmUtils utilities) {
        this.utilities = utilities == null ? new AlgorithmUtils() : utilities;
        algorithmInUse = "default";
        algorithms = new HashMap<>();
        addAlgorithm("default", new HeapAlgorithm(this.utilities));
        addAlgorithm("naive", new NaiveAlgorithm(this.utilities));
        plans = new ArrayList<>();
    }

    /**
     * Maps a name to a planning algorithm.
     * @param name The name identifier
     * @param algorithm The algorithm
     */
    public void addAlgorithm(String name, PlanningAlgorithm algorithm) {
        algorithms.put(name, algorithm);
    }

    /**
     * If an algorithm with the name exists, use it for the next {@link #formulateAllPlans(State, Goal, Action[])}.
     * @param name The name identifier
     */
    public void useAlgorithm(String name) {
        algorithmInUse = algorithms.containsKey(name) ? name : algorithmInUse;
    }

    /**
     * Calls the algorithm in use to formulate all valid plans.
     * @param start The start state
     * @param goal The goal
     * @param actions The actions available for use
     */
    public void formulateAllPlans(State start, Goal goal, Action[] actions) {
        plans = algorithms.get(algorithmInUse).formulatePlans(start, goal, actions);
    }

    /**
     * Returns the best plan that the algorithm in use can find.
     * @return The best plan
     */
    public Plan getBestPlan() {
        return algorithms.get(algorithmInUse).getBestPlan(plans);
    }

    public List<Plan> getAllPlans() {
        return plans;
    }

    /**
     * Returns the name of the algorithm in use.
     * @return The name of the algorithm
     */
    public String getAlgorithmName() {
        return algorithmInUse;
    }

    /**
     * Returns a list of all names of all the algorithms that the planner knows of.
     * @return A list of algorithm names
     */
    public List<String> getAlgorithmNames() {
        return new ArrayList<>(algorithms.keySet());
    }

    public AlgorithmUtils getAlgorithmUtils() {
        return utilities;
    }
}
