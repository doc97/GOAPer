package ui;

import io.JSONLoader;
import io.ScenarioLoadFailedException;
import model.Action;
import model.Plan;
import model.Planner;
import model.Scenario;
import model.simulation.Event;
import model.simulation.Simulation;

import java.util.List;
import java.util.Scanner;

/**
 * Created by Daniel Riissanen on 28.3.2018.
 */
public class Console {

    private Planner planner;
    private Simulation simulation;
    private Event event;
    private boolean isRunning;

    public Console() {
        event = new Event();
    }

    public void start() {
        isRunning = true;
        planner = new Planner();
        simulation = new Simulation(null, planner);
        Scanner scanner = new Scanner(System.in);
        System.out.println("===== | CONSOLE | =====");
        while (isRunning && scanner.hasNextLine()) {
            String line = scanner.nextLine();
            parseLine(line);
            System.out.println();
        }
    }

    private void stop() {
        isRunning = false;
    }

    private void parseLine(String line) {
        if (line.startsWith("plan")) {
            if (simulation.getScenario() == null)
                System.out.println("No scenario loaded");
            else
                simulation.plan();
        } else if (line.startsWith("step")) {
            if (simulation.getScenario() == null)
                System.out.println("No scenario loaded");
            else
                simulation.step(event);
        } else if (line.startsWith("show")) {
            String[] arr = line.split(" ", 2);
            if (arr.length < 2) {
                System.out.println("show: Invalid argument, try 'plan', 'scenario' or 'event'");
                return;
            }

            if (simulation.getScenario() == null) {
                System.out.println("No scenario loaded");
                return;
            }

            String arg = arr[1];
            switch (arg) {
                case "plans":
                    List<Plan> plans = simulation.getPlans();
                    if (plans.isEmpty())
                        System.out.println("No plans");
                    else
                        for (Plan plan : plans)
                            System.out.println(plan);
                    break;
                case "plan":
                    System.out.println(simulation.getCurrentPlan());
                    break;
                case "cost":
                    System.out.println(simulation.getCurrentPlan().getCost());
                case "algorithms":
                    for (String algorithmName : simulation.getAvailableAlgorithms())
                        System.out.println(algorithmName);
                    break;
                case "algorithm":
                    System.out.println(simulation.getCurrentAlgorithm());
                    break;
                case "situation":
                    System.out.println(simulation.getScenario().start);
                    break;
                case "goal":
                    System.out.println(simulation.getScenario().goal);
                    break;
                case "actions":
                    for (Action a : simulation.getScenario().actions)
                        System.out.println(a);
                    break;
                case "event":
                    String eventStr = event.toString();
                    if (!eventStr.isEmpty())
                        System.out.println(eventStr);
                    break;
                case "status":
                    System.out.println(simulation.isFinished() ? "Finished" : "Not finished");
                    break;
                default:
                    System.out.println("show: Invalid argument, try 'plan', 'situation' or 'event'");
                    break;
            }
        } else if (line.startsWith("set")) {
            String[] arr = line.split(" ", 3);
            if (arr.length < 3 || arr[1].isEmpty()) {
                System.out.println("set: Invalid arguments");
                return;
            }

            String key = arr[1];
            try {
                int value = Integer.parseInt(arr[2]);
                event.addKey(key, value);
                System.out.println(key + " set to " + value);
            } catch (NumberFormatException e) {
                System.out.println("Invalid format of " + key + ", should be an integer");
            }
        } else if (line.startsWith("unset")) {
            String[] arr = line.split(" ", 2);
            if (arr.length < 2 || arr[1].isEmpty()) {
                System.out.println("unset: Invalid argument");
                return;
            }

            event.removeKey(arr[1]);
        } else if (line.startsWith("use")) {
            String[] arr = line.split(" ", 2);
            String algorithmName = arr.length > 1 ? arr[1] : "";
            simulation.useAlgorithm(algorithmName);
        } else if (line.startsWith("load")) {
            String[] arr = line.split(" ", 2);
            String filename = arr.length > 1 ? arr[1] : "";
            loadScenario(filename);
        } else if (line.startsWith("help")) {
            String help =
                    "load <file>\n" +
                    "\tLoad a scenario from a file\n" +
                    "plan\n" +
                    "\tFormulate a plan from the current scenario\n" +
                    "step\n" +
                    "\tStep through the simulation, will activate and consume the event\n" +
                    "show <plan/plans/cost/situation/goal/actions/event/status>\n" +
                    "\tShow information\n" +
                    "set <key> <value>\n" +
                    "\tSet a boolean value with a key for the next event\n" +
                    "unset <key>\n" +
                    "\tUnset a value with a key from the next event\n" +
                    "help\n" +
                    "\tShows this help\n" +
                    "exit\n" +
                    "\tExits the program";
            System.out.println(help);
        } else if (line.startsWith("exit")) {
            stop();
        } else if (!line.isEmpty()) {
            System.out.println(line + ": Command not found");
        }
    }

    private void loadScenario(String filename) {
        JSONLoader loader = new JSONLoader();
        Scenario scenario;
        try {
            scenario = loader.loadScenarioFromFile(filename);
        } catch (ScenarioLoadFailedException e) {
            System.out.println("ERROR: Could not load scenario (" + e.getMessage() + ")");
            return;
        }

        simulation = new Simulation(scenario, planner);
    }
}
