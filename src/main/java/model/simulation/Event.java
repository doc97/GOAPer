package model.simulation;

import model.Postcondition;
import model.State;

import java.util.HashMap;

/**
 * Created by Daniel Riissanen on 29.3.2018.
 */
public class Event implements Postcondition {

    private HashMap<String, Integer> keys;

    public Event() {
        keys = new HashMap<>();
    }

    public void addKey(String key, int value) {
        keys.put(key, value);
    }

    public void removeKey(String key) {
        keys.remove(key);
    }

    @Override
    public void activate(State state) {
        for (String key : keys.keySet())
            state.apply(key, keys.get(key));
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (keys.isEmpty())
            return "<Empty>";

        for (String key : keys.keySet())
            builder.append(key).append(": ").append(keys.get(key)).append("\n");
        builder.deleteCharAt(builder.lastIndexOf("\n"));
        return builder.toString();
    }
}
