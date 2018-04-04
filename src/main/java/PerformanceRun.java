import algorithms.HeapAlgorithm;
import io.JSONLoader;
import io.ScenarioLoadFailedException;
import model.Planner;
import model.Scenario;

/**
 * Created by Daniel Riissanen on 4.4.2018.
 */
public class PerformanceRun {
    public static void main(String[] args) {
        JSONLoader loader = new JSONLoader();
        Planner planner = new Planner();
        planner.addAlgorithm("heap", new HeapAlgorithm());
        planner.useAlgorithm("heap");

        String[] scenarioNames = new String[] {
                "res/performance/scenarioExplode1.json",
                "res/performance/scenarioExplode2.json",
                "res/performance/scenarioExplode3.json",
                "res/performance/scenarioExplode4.json",
                "res/performance/scenarioExplode5.json",
                "res/performance/scenarioExplode6.json",
                "res/performance/scenarioExplode7.json",
                "res/performance/scenarioExplode8.json",
                "res/performance/scenarioExplode9.json",
                "res/performance/scenarioExplode10.json",
                "res/performance/scenarioExplode11.json",
                "res/performance/scenarioExplode12.json",
                "res/performance/scenarioExplode13.json"
        };

        Scenario[] scenarios = new Scenario[scenarioNames.length];

        for (int i = 0; i < scenarioNames.length; i++) {
            try {
                scenarios[i] = loader.loadScenarioFromFile(scenarioNames[i]);
            } catch (ScenarioLoadFailedException e) {
                System.out.println("Failed to load scenario: " + e.getMessage());
            }
        }


        float[] results = new float[scenarios.length];
        for (int n = 0; n < 10; n++) {
            for (int i = 0; i < scenarioNames.length; i++) {
                Scenario scenario = scenarios[i];
                if (scenario != null) {
                    long start = System.nanoTime();
                    planner.formulateAllPlans(scenario.start, scenario.goal, scenario.actions);
                    long end = System.nanoTime();
                    results[i] += (end - start) / 1000000f;
                }
            }
        }

        for (int i = 0; i < results.length; i++) {
            System.out.println(scenarioNames[i]);
            System.out.println(planner.getBestPlan().getActions().length == 0 ? "FAIL" : "SUCCESS");
            System.out.println("Time elapsed: " + results[i] / 10 + " ms\n");
        }
    }
}
