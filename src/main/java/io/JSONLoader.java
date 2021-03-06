package io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import model.Scenario;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Loads JSON scenarios.
 * <p/>
 * Created by Daniel Riissanen on 17.3.2018.
 */
public class JSONLoader {

    /**
     * Loads a scenario from a JSON file.
     * @param filename The path to the file, relative to the project root.
     * @return The loaded scenario
     * @throws ScenarioLoadFailedException If something goes wrong
     */
    public Scenario loadScenarioFromFile(String filename) throws ScenarioLoadFailedException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
            return loadScenario(builder.toString());
        } catch (IOException e) {
            throw new ScenarioLoadFailedException(e.getMessage());
        }
    }

    /**
     * Loads a scenario from a JSON string.
     * @param jsonString The JSON string to parse
     * @return The loaded scenario
     * @throws ScenarioLoadFailedException If something goes wrong
     */
    public Scenario loadScenario(String jsonString) throws ScenarioLoadFailedException {
        try {
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(int.class, new IntegerTypeAdapter());
            Gson gson = builder.create();
            JSONScenario jsonScenario = gson.fromJson(jsonString, JSONScenario.class);
            if (jsonScenario == null)
                throw new ScenarioLoadFailedException("Argument is not a JSON string");
            if (jsonScenario.isEmpty())
                throw new ScenarioLoadFailedException("Invalid JSON string");

            return new JSONConverter().convertScenario(jsonScenario);
        } catch (JsonSyntaxException e) {
            throw new ScenarioLoadFailedException(e.getMessage());
        }
    }
}
