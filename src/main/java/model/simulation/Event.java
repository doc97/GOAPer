package model.simulation;

import model.Postcondition;
import model.State;

import java.util.HashMap;

/**
 * Describes an external change, an {@link model.Action} without a cost or precondition.
 * <p/>
 * Created by Daniel Riissanen on 29.3.2018.
 * @see Postcondition
 */
public class Event implements Postcondition {

    /** The key-values to apply to the state */
    private HashMap<String, Integer> keys;

    /**
     * Class constructor
     */
    public Event() {
        keys = new HashMap<>();
    }

    /**
     * Adds a key-value mapping. Will overwrite an existing mapping.
     * @param key The key
     * @param value The value
     */
    public void addKey(String key, int value) {
        keys.put(key, value);
    }

    /**
     * Remove a key-value mapping. Does nothing if the key does not exist.
     * @param key The key
     */
    public void removeKey(String key) {
        keys.remove(key);
    }

    /**
     * Applies the changes to a state.
     * @param state The state to change
     */
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
