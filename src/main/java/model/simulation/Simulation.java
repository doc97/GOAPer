package model.simulation;

import algorithms.HeapAlgorithm;
import model.Plan;
import model.Planner;
import model.Scenario;

import java.util.List;

/**
 * The class is the primary interface that UI components will interact with.
 * <p/>
 * Created by Daniel Riissanen on 28.3.2018.
 */
public class Simulation {

    /** The scenario that this simulation will use */
    private Scenario scenario;

    /** The planner that this simulation will use */
    private Planner planner;

    /** A flag indicating whether to use a cached result of {@link #isFinished()} */
    private boolean isDirty;

    /** The cached result of {@link #isFinished()} */
    private boolean isFinished;

    /**
     * Class constructor specifying a scenario and planner.
     * @param scenario The scenario, must not be null
     * @param planner The planner
     */
    public Simulation(Scenario scenario, Planner planner) {
        if (scenario == null) throw new IllegalArgumentException("No scenario");
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
