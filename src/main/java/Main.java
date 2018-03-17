import algorithms.NaiveAlgorithm;
import io.JSONLoader;
import io.ScenarioLoadFailedException;
import model.Action;
import model.Planner;
import model.Scenario;

public class Main {
    public static void main(String[] args) {
        JSONLoader loader = new JSONLoader();
        Scenario scenario;
        try {
            scenario = loader.loadScenarioFromFile("res/testScenarioInvalid.json");
        } catch (ScenarioLoadFailedException e) {
            System.err.println("ERROR: Could not load scenario (" + e.getMessage() + ")");
            return;
        }

        System.out.println("=====================\n");
        System.out.println("Starting state:\n" + scenario.start);
        System.out.println("Goal state:\n" + scenario.goal);
        System.out.println("Formulating plan...");

        boolean finished = true;
        for (String goalKey : scenario.goal.getKeys()) {
            if (scenario.start.query(goalKey) != scenario.goal.query(goalKey))
                finished = false;
        }
        if (finished) {
            System.out.println("SUCCESS: You are already at the goal");
            return;
        }

        Planner planner = new Planner();
        Action[] plan = planner.execute(scenario.start, scenario.goal, scenario.actions, new NaiveAlgorithm());

        if (plan.length > 0) {
            System.out.println("SUCCESS: A plan has been made with " + plan.length + " steps");

            System.out.print("\n[Start] -> ");
            for (Action a : plan) {
                System.out.print(a + " -> ");
            }
            System.out.println("[Goal]");
        } else {
            System.out.println("FAILURE: Plan could not be made");
        }
    }
}
