package model.simulation;

import algorithms.PlanningAlgorithm;
import model.Plan;
import model.Planner;
import model.Scenario;

/**
 * Created by Daniel Riissanen on 28.3.2018.
 */
public class Simulation {

    private Scenario scenario;
    private Planner planner;
    private Plan plan;
    private boolean isDirty;
    private boolean isFinished;

    public Simulation(Scenario scenario) {
        this.scenario = scenario;
        planner = new Planner();
        plan = new Plan();
        isDirty = true;
    }

    public void plan(PlanningAlgorithm algorithm) {
        plan = planner.execute(scenario.start, scenario.goal, scenario.actions, algorithm);
    }

    public void step(Event event) {
        plan.getNextAction().execute(scenario.start);
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

    public Plan getPlan() {
        return plan;
    }
}
