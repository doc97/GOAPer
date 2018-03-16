import model.*;

public class Main {
    public static void main(String[] args) {
        Simulation simple = createSimpleSimulation();

        System.out.println("=====================\n");
        System.out.println("Starting state:\n" + simple.start);
        System.out.println("Goal state:\n" + simple.goal);
        System.out.println("Formulating plan...");

        Planner planner = new Planner();
        Action[] plan = planner.formulatePlan(simple.start, simple.goal, simple.actions);

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

    public static Simulation createSimpleSimulation() {
        Simulation simulation = new Simulation();
        simulation.start = new State();
        simulation.start.addKey("hasApple", false);
        simulation.start.addKey("isAtTree", false);

        simulation.goal = new State();
        simulation.goal.addKey("hasApple", true);

        simulation.actions = new Action[]{
                new Action(
                        "Go to tree",
                        state -> !state.query("isAtTree"),
                        state -> state.apply("isAtTree", true)
                ),
                new Action(
                        "Pick apple",
                        state -> state.query("isAtTree") && !state.query("hasApple"),
                        state -> state.apply("hasApple", true)
                )
        };

        return simulation;
    }
}
