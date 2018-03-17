package io;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import model.Scenario;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Daniel Riissanen on 17.3.2018.
 */
public class JSONLoader {

    public Scenario loadScenarioFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
            return loadScenario(builder.toString());
        } catch (IOException e) {
            System.err.println("ERROR: " + e.getMessage() + " (" + filename + ")");
            return null;
        }
    }

    public Scenario loadScenario(String jsonString) {
        try {
            Gson gson = new Gson();
            JSONScenario jsonScenario = gson.fromJson(jsonString, JSONScenario.class);
            if (jsonScenario == null)
                return null;

            JSONConverter converter = new JSONConverter();
            return converter.convertScenario(jsonScenario);
        } catch (JsonSyntaxException | IllegalStateException e) {
            System.err.println("ERROR: " + e.getMessage());
            return null;
        }
    }
}
