package model;

import model.operations.Operation;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by Daniel Riissanen on 16.3.2018.
 */
public class State {

    private HashMap<String, Integer> keys;

    public State(State state) {
        if (state != null) {
            this.keys = new HashMap<>(state.keys);
        } else {
            this.keys = new HashMap<>();
        }
    }

    public State(HashMap<String, Integer> keys) {
        this.keys = keys;
    }

    public State() {
        keys = new HashMap<>();
    }

    public void addKey(String key, int value) {
        if (key != null)
            keys.putIfAbsent(key, value);
    }

    public int query(String key) {
        return keys.getOrDefault(key, 0);
    }

    public boolean queryBoolean(String key) {
        return keys.containsKey(key) && keys.get(key) == 1;
    }

    public void apply(String key, int value, Operation operation) {
        if (key != null)
            keys.put(key, operation.apply(keys.getOrDefault(key, 0), value));
    }

    public void update(String key, int value) {
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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof State))
            return false;
        State other = (State) o;

        if (this.keys.keySet().size() != other.keys.keySet().size())
            return false;

        for (String key : this.keys.keySet()) {
            if (!this.keys.get(key).equals(other.keys.get(key)))
                return false;
        }
        return true;
    }
}
