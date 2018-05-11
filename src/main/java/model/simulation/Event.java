package model.simulation;

import datastructures.HashTable;
import model.Postcondition;
import model.State;

/**
 * Describes an external change, an {@link model.Action} without a cost or precondition.
 * <p/>
 * Created by Daniel Riissanen on 29.3.2018.
 * @see Postcondition
 */
public class Event implements Postcondition {

    /** The key-values to apply to the state */
    private HashTable<String, Integer> keys;

    /**
     * Class constructor
     */
    public Event() {
        keys = new HashTable<>();
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
        String[] strKeys = new String[keys.keys().count()];
        keys.keys().asArray(strKeys);
        for (String key : strKeys)
            state.apply(key, keys.get(key));
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (keys.isEmpty())
            return "<Empty>";

        String[] strKeys = new String[keys.keys().count()];
        keys.keys().asArray(strKeys);
        for (String key : strKeys)
            builder.append(key).append(": ").append(keys.get(key)).append("\n");
        builder.deleteCharAt(builder.lastIndexOf("\n"));
        return builder.toString();
    }
}
