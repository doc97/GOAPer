package io;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import model.Action;
import model.Scenario;
import model.State;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Daniel Riissanen on 17.3.2018.
 */
public class JSONLoader {
    public Scenario loadScenario(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
            String jsonString = builder.toString();
            try {
                Gson gson = new Gson();
                JSONScenario jsonScenario = gson.fromJson(jsonString, JSONScenario.class);
                JSONConverter converter = new JSONConverter();
                return converter.convertScenario(jsonScenario);
            } catch (JsonSyntaxException | IllegalStateException e) {
                System.err.println("ERROR: " + e.getMessage() + " (" + filename + ")");
                return null;
            }
        } catch (IOException e) {
            System.err.println("ERROR: " + e.getMessage() + " (" + filename + ")");
            return null;
        }
    }


}
