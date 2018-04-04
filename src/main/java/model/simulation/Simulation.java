package model.simulation;

import algorithms.HeapAlgorithm;
import model.Plan;
import model.Planner;
import model.Scenario;

import java.util.List;

/**
 * Created by Daniel Riissanen on 28.3.2018.
 */
public class Simulation {

    private Scenario scenario;
    private Planner planner;
    private boolean isDirty;
    private boolean isFinished;

    public Simulation(Scenario scenario, Planner planner) {
        this.scenario = scenario;
        this.planner = planner == null ? new Planner() : planner;
        this.planner.addAlgorithm("heap", new HeapAlgorithm(this.planner.getAlgorithmUtils()));
        isDirty = true;
    }

    public void useAlgorithm(String algorithmName) {
        planner.useAlgorithm(algorithmName);
    }

    public void plan() {
        planner.formulateAllPlans(scenario.start, scenario.goal, scenario.actions);
    }

    public void step(Event event) {
        planner.getBestPlan().getNextAction().execute(scenario.start);
        event.activate(scenario.start);
        isDirty = true;
    }

    public boolean isFinished() {
        if (!isDirty)
            return isFinished;

        isDirty = false;
        isFinished = scenario.isFinished();
        return isFinished;
    }

    public Scenario getScenario() {
        return scenario;
    }

    public Plan getCurrentPlan() {
        return planner.getBestPlan();
    }

    public List<Plan> getPlans() {
        return planner.getAllPlans();
    }

    public String getCurrentAlgorithm() {
        return planner.getAlgorithmName();
    }

    public List<String> getAvailableAlgorithms() {
        return planner.getAlgorithmNames();
    }
}
