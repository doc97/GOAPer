import algorithms.NaiveAlgorithm;
import model.Action;
import model.Planner;
import model.Scenario;
import model.State;

public class Main {
    public static void main(String[] args) {
        Simulation simple = createSimpleSimulation2();

        System.out.println("=====================\n");
        System.out.println("Starting state:\n" + simple.start);
        System.out.println("Goal state:\n" + simple.goal);
        System.out.println("Formulating plan...");

        Planner planner = new Planner();
        Action[] plan = planner.execute(simple.start, simple.goal, simple.actions, new NaiveAlgorithm());

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

    public static Scenario createSimpleScenario1() {
        Scenario scenario = new Scenario();
        scenario.start = new State();
        scenario.start.addKey("hasApple", false);
        scenario.start.addKey("isAtTree", false);

        scenario.goal = new State();
        scenario.goal.addKey("hasApple", true);

        scenario.actions = new Action[] {
                new Action(
                        "Go to tree",
                        () -> {
                            State state = new State();
                            state.addKey("isAtTree", false);
                            return state;
                        },
                        state -> state.apply("isAtTree", true)
                ),
                new Action(
                        "Pick apple",
                        () -> {
                            State state = new State();
                            state.addKey("isAtTree", true);
                            state.addKey("hasApple", false);
                            return state;
                        },
                        state -> state.apply("hasApple", true)
                )
        };

        return scenario;
    }

    public static Scenario createSimpleScenario2() {
        Scenario scenario = new Scenario();
        scenario.start = new State();
        scenario.goal = new State();
        scenario.goal.addKey("targetIsDead", true);

        scenario.actions = new Action[] {
                new Action(
                        "Attack",
                        () -> {
                            State state = new State();
                            state.addKey("weaponIsLoaded", true);
                            return state;
                        },
                        state -> state.apply("targetIsDead", true)
                ),
                new Action(
                        "Load weapon",
                        () -> {
                            State state = new State();
                            state.addKey("weaponIsArmed", true);
                            return state;
                        },
                        state -> state.apply("weaponIsLoaded", true)
                ),
                new Action(
                        "Draw weapon",
                        State::new,
                        state -> state.apply("weaponIsArmed", true)
                )
        };

        return scenario;
    }
}
