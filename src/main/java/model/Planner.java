package model;

import algorithms.AlgorithmUtils;
import algorithms.NaiveAlgorithm;
import algorithms.PlanningAlgorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Daniel Riissanen on 16.3.2018.
 */
public class Planner {

    private HashMap<String, PlanningAlgorithm> algorithms;
    private String algorithmInUse;
    private List<Plan> plans;
    private AlgorithmUtils utilities;

    public Planner() {
        this(null);
    }

    public Planner(AlgorithmUtils utilities) {
        this.utilities = utilities == null ? new AlgorithmUtils() : utilities;
        algorithmInUse = "default";
        algorithms = new HashMap<>();
        addAlgorithm("default", new NaiveAlgorithm(this.utilities));
        addAlgorithm("naive", new NaiveAlgorithm(this.utilities));
        plans = new ArrayList<>();
    }

    public void addAlgorithm(String name, PlanningAlgorithm algorithm) {
        algorithms.put(name, algorithm);
    }

    public void useAlgorithm(String name) {
        algorithmInUse = algorithms.containsKey(name) ? name : algorithmInUse;
    }

    public void formulateAllPlans(State start, Goal goal, Action[] actions) {
        plans = algorithms.get(algorithmInUse).formulatePlans(start, goal, actions);
    }

    public Plan getBestPlan() {
        return algorithms.get(algorithmInUse).getBestPlan(plans);
    }

    public List<Plan> getAllPlans() {
        return plans;
    }

    public String getAlgorithmName() {
        return algorithmInUse;
    }

    public List<String> getAlgorithmNames() {
        return new ArrayList<>(algorithms.keySet());
    }

    public AlgorithmUtils getAlgorithmUtils() {
        return utilities;
    }
}
