import algorithms.NaiveAlgorithm;
import io.JSONLoader;
import model.Action;
import model.Planner;
import model.Scenario;

public class Main {
    public static void main(String[] args) {
        JSONLoader loader = new JSONLoader();
        Scenario scenario = loader.loadScenario("res/scenario1.json");

        if (scenario == null) {
            System.out.println("ERROR: Could not load scenario");
            return;
        }

        System.out.println("=====================\n");
        System.out.println("Starting state:\n" + scenario.start);
        System.out.println("Goal state:\n" + scenario.goal);
        System.out.println("Formulating plan...");

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
