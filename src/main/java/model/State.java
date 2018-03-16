package model;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by Daniel Riissanen on 16.3.2018.
 */
public class State {
    private HashMap<String, Boolean> keys;

    public State(State state) {
        this.keys = new HashMap<>(state.keys);
    }

    public State(HashMap<String, Boolean> keys) {
        this.keys = keys;
    }

    public State() {
        keys = new HashMap<>();
    }

    public void addKey(String key, boolean value) {
        if (keys.containsKey(key))
            throw new IllegalStateException("Trying to add a key that already exists!");
        keys.put(key, value);
    }

    public boolean query(String key) {
        return keys.containsKey(key) && keys.get(key);
    }

    public void apply(String key, boolean value) {
        if (keys.containsKey(key)) {
            keys.put(key, value);
        }
    }

    public Set<String> getKeys() {
        return keys.keySet();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String key : keys.keySet()) {
            stringBuilder.append(key).append(": ").append(keys.get(key)).append("\n");
        }
        return stringBuilder.toString();
    }
}
